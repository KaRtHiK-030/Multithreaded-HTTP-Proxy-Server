import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache extends LinkedHashMap<String, CacheEntry> {

    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
        return size() > capacity;
    }
}