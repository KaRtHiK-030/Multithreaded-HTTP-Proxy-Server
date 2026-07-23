# Multithreaded HTTP Proxy Server

## Overview

This project is a high-performance HTTP Proxy Server built in Java that acts as an intermediary between clients and target servers. It handles concurrent requests, optimizes performance using caching, and ensures system stability with rate limiting.
  
The system demonstrates key backend engineering concepts such as networking, concurrency, caching, and system design.

---

## Problem Statement

Repeated network requests to external servers lead to:

- High latency  
- Increased load on backend services  
- Unnecessary network overhead  

This proxy server solves these problems by:

- Caching frequently requested responses  
- Controlling traffic using rate limiting  
- Handling multiple clients efficiently  

---

## Features

### Multithreaded Request Handling
- Uses a thread pool (ExecutorService)  
- Handles multiple client connections concurrently  

### LRU Cache with TTL
- In-memory cache using LinkedHashMap (LRU policy)  
- Stores frequently accessed responses  
- TTL ensures data freshness  

### Rate Limiting
- Implements sliding window algorithm  
- Limits excessive requests per client IP  
- Protects system from overload  

### Metrics Monitoring
- Exposes `/metrics` endpoint  
- Tracks:
  - Total Requests  
  - Served Requests  
  - Cache Hits & Misses  
  - Rate Limited Requests  
  - Cache Efficiency  

### Timeout Handling
- Connection timeout (3 sec)  
- Read timeout (5 sec)  
- Prevents blocking due to slow servers  

---

## System Architecture

```
Client → Proxy Server (Cache + Rate Limiter) → Target Server
```

---

## Request Flow

1. Client sends HTTP request  
2. Proxy parses request and extracts Host  
3. Rate limiter check  
   - If exceeded → returns 429 Too Many Requests  
4. Cache check  
   - HIT → return cached response  
   - MISS → forward request to server  
5. Server response received  
6. Response cached (if applicable) and returned  

---

## Performance Results

- Throughput: ~600 requests/sec  
- Average Latency: ~14 ms  
- Cache Efficiency: ~96%  
- Stable performance under high traffic  

---

## Real Execution Output

```
"C:\Program Files\Java\jdk-21\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.2\lib\idea_rt.jar=49210" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\vishw\IdeaProjects\MultiThreadProxyServer\out\production\MultiThreadProxyServer ClientSide

Connected to proxy
HTTP/1.1 200 OK
Content-Type: text/html
Content-Length: 244

<html><body><h1>Proxy Metrics</h1>
<p>Total Requests: 12000</p>
<p>Served Requests: 2000</p>
<p>Rate Limited: 10000</p>
<hr>
<p>Cache Hits: 1932</p>
<p>Cache Misses: 68</p>
<p>System Hit Rate: 16.10%</p>
<p>Cache Efficiency: 96.60%</p>
</body></html>

Process finished with exit code 0
```

---

## Load Testing

A custom LoadTester was used to simulate concurrent requests:

- Multi-threaded request generation  
- Measures throughput and latency  

---

## Tech Stack

- Java  
- Socket Programming  
- Multithreading (ExecutorService)  
- HTTP Protocol  
- Data Structures (LRU Cache)  

---

## How to Run

### Start Server
```bash
javac ServerSide.java
java ServerSide
```

### Run Client / Load Tester
```bash
java ClientSide
```

### Access Metrics
```
//make    boolean showMetrics = false;  as true for metrics
GET /metrics
```

---

## Limitations

- Uses blocking I/O (Socket-based)  
- Single-node cache  
- No HTTPS support  
- No connection pooling  

---

## Future Improvements

- Non-blocking I/O (Java NIO)  
- Distributed caching (Redis)  
- Connection pooling  
- HTTPS support  
- API Gateway extensions  

---

## Key Learnings

- Handling concurrent network requests  
- Designing cache systems (LRU + TTL)  
- Implementing rate limiting  
- Measuring system performance  
- Building scalable backend components  

---

## Conclusion

This project demonstrates how systems like CDNs, API Gateways, and Reverse Proxies improve performance and manage traffic efficiently.

---

## Author

Karthik Naik
