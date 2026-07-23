class CacheEntry {
    byte[] data;
    long timestamp;

    public CacheEntry(byte[] data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}