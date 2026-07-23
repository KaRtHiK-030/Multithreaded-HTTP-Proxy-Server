import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class LoadTester {

    private static final int THREADS = 20;
    private static final int REQUESTS_PER_THREAD = 100;

    private static final String[] HOSTS = {
            "www.google.com",
            "example.com",
            "iana.org"
    };

    private static final AtomicLong totalLatency = new AtomicLong(0);
    private static final AtomicLong minLatency = new AtomicLong(Long.MAX_VALUE);
    private static final AtomicLong maxLatency = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < THREADS; i++) {
            pool.execute(() -> runRequests());
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        int totalRequests = THREADS * REQUESTS_PER_THREAD;
        double totalTimeSec = (endTime - startTime) / 1000.0;

        double avgLatency = totalLatency.get() / (double) totalRequests;

        System.out.println("\n====== LOAD TEST RESULT ======");
        System.out.println("Total Requests: " + totalRequests);
        System.out.println("Total Time: " + totalTimeSec + " sec");
        System.out.println("Throughput: " + (totalRequests / totalTimeSec) + " req/sec");

        System.out.println("\n====== LATENCY ======");
        System.out.println("Avg Latency: " + avgLatency + " ms");
        System.out.println("Min Latency: " + minLatency.get() + " ms");
        System.out.println("Max Latency: " + maxLatency.get() + " ms");
    }

    private static void runRequests() {
        Random random = new Random();

        for (int i = 0; i < REQUESTS_PER_THREAD; i++) {

            String host = HOSTS[random.nextInt(HOSTS.length)];

            long start = System.nanoTime();

            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 5000), 3000);
                socket.setSoTimeout(5000);

                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                String request =
                        "GET / HTTP/1.1\r\n" +
                                "Host: " + host + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n";

                out.write(request.getBytes());
                out.flush();

                byte[] buffer = new byte[4096];
                while (in.read(buffer) != -1) {}

                socket.close();

            } catch (Exception e) {
                // ignore errors for load testing
            }

            long end = System.nanoTime();
            long latency = (end - start) / 1_000_000;

            totalLatency.addAndGet(latency);

            minLatency.updateAndGet(prev -> Math.min(prev, latency));
            maxLatency.updateAndGet(prev -> Math.max(prev, latency));
        }
    }
}