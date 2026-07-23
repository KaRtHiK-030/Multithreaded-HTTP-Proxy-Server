import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class RateLimiter {

    private final int maxRequests;
    private final long window;

    private final Map<String, Deque<Long>> map = new ConcurrentHashMap<>();

    public RateLimiter(int maxRequests, long window) {
        this.maxRequests = maxRequests;
        this.window = window;
    }

    public boolean allow(String ip) {

        long now = System.currentTimeMillis();

        Deque<Long> q = map.computeIfAbsent(ip, k -> new ArrayDeque<>());

        synchronized (q) {

            while (!q.isEmpty() && now - q.peekFirst() > window) {
                q.pollFirst();
            }

            if (q.size() < maxRequests) {
                q.addLast(now);
                return true;
            }

            return false;
        }
    }
}