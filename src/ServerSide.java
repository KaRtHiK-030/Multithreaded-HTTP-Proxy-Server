import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerSide {

    private static final int PORT = 5000;
    private static final int THREAD_POOL_SIZE = 20;
    private static final long TTL = 10000;

    private static final RateLimiter rateLimiter =
            new RateLimiter(1000, 10000);

    // 🔥 Metrics
    private static final AtomicInteger totalRequests = new AtomicInteger();
    private static final AtomicInteger servedRequests = new AtomicInteger();
    private static final AtomicInteger cacheHits = new AtomicInteger();
    private static final AtomicInteger cacheMisses = new AtomicInteger();
    private static final AtomicInteger rateLimited = new AtomicInteger();

    private static final Map<String, CacheEntry> cache =
            Collections.synchronizedMap(new LRUCache(50));

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("🚀 Proxy Server running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(() -> handleClient(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔥 Metrics Endpoint
    private static void sendMetrics(OutputStream out) throws IOException {
        int total = totalRequests.get();
        int served = servedRequests.get();
        int hits = cacheHits.get();
        int misses = cacheMisses.get();
        int limited = rateLimited.get();

        double systemHitRate = total == 0 ? 0 : (hits * 100.0) / total;
        double cacheEfficiency = (hits + misses) == 0 ? 0 :
                (hits * 100.0) / (hits + misses);

        String html =
                "<html><body>" +
                        "<h1>📊 Proxy Metrics</h1>" +
                        "<p>Total Requests: " + total + "</p>" +
                        "<p>Served Requests: " + served + "</p>" +
                        "<p>Rate Limited: " + limited + "</p>" +
                        "<hr>" +
                        "<p>Cache Hits: " + hits + "</p>" +
                        "<p>Cache Misses: " + misses + "</p>" +
                        "<p>System Hit Rate: " + String.format("%.2f", systemHitRate) + "%</p>" +
                        "<p>Cache Efficiency: " + String.format("%.2f", cacheEfficiency) + "%</p>" +
                        "</body></html>";

        String response =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + html.length() + "\r\n\r\n" +
                        html;

        out.write(response.getBytes());
        out.flush();
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()
        ) {

            String line;
            String host = "";
            String requestLine = "";
            StringBuilder request = new StringBuilder();

            boolean first = true;

            // 🔥 Read request
            while ((line = in.readLine()) != null && !line.isEmpty()) {

                if (first) {
                    requestLine = line;
                    first = false;
                }

                request.append(line).append("\r\n");

                // case-insensitive Host parsing
                if (line.toLowerCase().startsWith("host:")) {
                    host = line.substring(5).trim();
                }
            }
            request.append("\r\n");

            // 🔥 Metrics endpoint
            if (requestLine.contains("GET /metrics")) {
                sendMetrics(out);
                return;
            }

            totalRequests.incrementAndGet();

            if (host.isEmpty()) {
                String resp =
                        "HTTP/1.1 400 Bad Request\r\nContent-Length: 12\r\n\r\nMissing Host";
                out.write(resp.getBytes());
                out.flush();
                return;
            }

            String ip = clientSocket.getInetAddress().getHostAddress();

            // 🔥 Rate limiting
            if (!rateLimiter.allow(ip)) {
                rateLimited.incrementAndGet();

                String resp =
                        "HTTP/1.1 429 Too Many Requests\r\nContent-Length: 19\r\n\r\nRate limit exceeded";
                out.write(resp.getBytes());
                out.flush();
                return;
            }

            servedRequests.incrementAndGet();

            boolean isGet = requestLine.startsWith("GET");

            // 🔥 Better cache key
            String key = requestLine + "|" + host;

            // 🔥 Cache check
            if (isGet) {
                CacheEntry entry = cache.get(key);

                if (entry != null &&
                        System.currentTimeMillis() - entry.timestamp < TTL) {

                    cacheHits.incrementAndGet();
                    out.write(entry.data);
                    out.flush();
                    return;
                } else if (entry != null) {
                    cache.remove(key);
                }

                cacheMisses.incrementAndGet();
            }

            // 🔥 Forward request
            try (Socket server = new Socket()) {
                server.connect(new InetSocketAddress(host, 80), 3000);
                server.setSoTimeout(5000);

                OutputStream serverOut = server.getOutputStream();
                InputStream serverIn = server.getInputStream();

                serverOut.write(request.toString().getBytes());
                serverOut.flush();

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                byte[] buf = new byte[4096];
                int n;

                while ((n = serverIn.read(buf)) != -1) {
                    buffer.write(buf, 0, n);
                    out.write(buf, 0, n);
                }

                out.flush();

                if (isGet && buffer.size() < 200_000) {
                    cache.put(key, new CacheEntry(buffer.toByteArray()));
                }

            } catch (SocketTimeoutException e) {
                String resp =
                        "HTTP/1.1 504 Gateway Timeout\r\nContent-Length: 7\r\n\r\nTimeout";
                out.write(resp.getBytes());

            } catch (Exception e) {
                String resp =
                        "HTTP/1.1 502 Bad Gateway\r\nContent-Length: 24\r\n\r\nError contacting server";
                out.write(resp.getBytes());
            }

        } catch (Exception ignored) {
        } finally {
            try { clientSocket.close(); } catch (Exception ignored) {}
        }
    }
}