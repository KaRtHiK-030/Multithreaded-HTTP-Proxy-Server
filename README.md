<div align="center">

# 🌐 Multithreaded HTTP Proxy Server

### High-Performance Java HTTP Proxy Server with LRU Caching, Sliding Window Rate Limiting & Concurrent Request Processing

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Socket Programming](https://img.shields.io/badge/Networking-Socket%20Programming-blue?style=for-the-badge)
![ExecutorService](https://img.shields.io/badge/Concurrency-ExecutorService-success?style=for-the-badge)
![HTTP](https://img.shields.io/badge/Protocol-HTTP-red?style=for-the-badge)
![Cache](https://img.shields.io/badge/Cache-LRU%20%2B%20TTL-yellow?style=for-the-badge)
![Rate Limiting](https://img.shields.io/badge/Rate%20Limiter-Sliding%20Window-purple?style=for-the-badge)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 📈 Project Highlights

- 🚀 ~1150 Requests/sec Throughput
- ⚡ ~14 ms Average Latency
- 📈 96.6% Cache Efficiency
- 🧵 ExecutorService Thread Pool
- 🛡️ Sliding Window Rate Limiting

---

## 📚 Table of Contents

- [Project Highlights](#-project-highlights)
- [Overview](#-overview)
- [Problem Statement](#-problem-statement)
- [Features](#-features)
- [System Architecture](#️-system-architecture)
- [Request Flow](#-request-flow)
- [Performance Results](#-performance-results)
- [Load Testing](#-load-testing)
- [Project Structure](#-project-structure)
- [Tech Stack](#-tech-stack)
- [Requirements](#-requirements)
- [Skills Demonstrated](#-skills-demonstrated)
- [How to Run](#-how-to-run)
- [Limitations](#️-limitations)
- [Future Improvements](#-future-improvements)
- [Key Learnings](#-key-learnings)
- [Conclusion](#-conclusion)
- [Author](#-author)

---

## 📖 Overview
A multithreaded HTTP Proxy Server built in Java using Socket Programming. The server forwards client requests to destination servers while improving performance through LRU caching, sliding-window rate limiting, and concurrent request processing using an ExecutorService thread pool.

The project was developed to explore backend networking concepts such as concurrent request processing, HTTP communication, caching, and traffic control using core Java while demonstrating real-world proxy server design principles.

---

## ❗ Problem Statement
Repeated network requests to external servers lead to:
- High latency
- Increased load on backend services
- Unnecessary network overhead

This proxy server solves these problems by:
- Caching frequently requested responses
- Controlling traffic using rate limiting
- Handling multiple clients efficiently

---

## ✨ Features

### Multithreaded Request Handling
- Uses a thread pool (ExecutorService)
- Handles multiple client connections concurrently

### LRU Cache with TTL
- In-memory cache using LinkedHashMap (LRU policy)
- Stores frequently accessed responses
- TTL automatically expires stale cache entries to maintain data freshness

### Rate Limiting
- Implements sliding window algorithm
- Limits excessive requests per client IP
- Protects system from overload
- Returns HTTP 429 (Too Many Requests) when the request limit is exceeded

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

## 🏗️ System Architecture
The proxy server sits between clients and destination servers, applying request validation, caching, and rate limiting before forwarding traffic.

```
                 +----------------------+
                 |      Client          |
                 +----------+-----------+
                            |
                            v
                +------------------------+
                |   HTTP Proxy Server    |
                |------------------------|
                | ExecutorService Pool   |
                | LRU Cache + TTL        |
                | Sliding Window RL      |
                | Metrics Collector      |
                +-----------+------------+
                            |
                            v
                  +------------------+
                  |  Target Server   |
                  +------------------+
```

---

## 🔄 Request Flow
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

## 📊 Performance Results
- Throughput: ~1150 requests/sec
- Average Latency: ~14 ms
- Successfully processed 2000 requests during load testing

> **Note:** Performance measurements were obtained using the included custom multithreaded `LoadTester` on a local development environment. Results may vary depending on system hardware and network conditions.

---

## 🖥️ Real Execution Output
```
🚀 Proxy Server running on port 5000

Connected to proxy

HTTP/1.1 200 OK

Proxy Metrics
--------------------------------
Total Requests: 2000
Cache Hits: 1932
Cache Misses: 68
Cache Efficiency: 96.6%
```

---

## ⚡ Load Testing
A custom multithreaded LoadTester was developed to simulate concurrent client requests and evaluate throughput and latency under load.

---

## 📁 Project Structure
```
src/
├── ServerSide.java
├── ClientSide.java
├── LoadTester.java
├── LRUCache.java
├── CacheEntry.java
└── RateLimiter.java
```

---

## 🛠 Tech Stack
- Java 25
- Java Socket API
- ExecutorService (Java Concurrency)
- HTTP/1.1
- LinkedHashMap (LRU Cache)
- IntelliJ IDEA

---

## 📋 Requirements
- Java 21+ (Tested on Java 25)
- IntelliJ IDEA / VS Code (optional)

---

## 💡 Skills Demonstrated
- Java Socket Programming
- Concurrent Programming
- Thread Pool Management (ExecutorService)
- LRU Cache Design
- Sliding Window Rate Limiting
- HTTP Request Processing
- Performance Testing
- Backend Networking Fundamentals
- Concurrent Server Design

---

## 🚀 How to Run

### 1. Compile
```bash
javac *.java
```

### 2. Start the Proxy Server
```bash
java ServerSide
```

### 3. Run the Client
```bash
java ClientSide
```

### 4. Run the Load Tester
```bash
java LoadTester
```

### 5. Access Metrics
Enable metrics by setting `showMetrics = true` in `ClientSide.java` before running the client.

```
GET /metrics
```

---

## ⚠️ Limitations
- Uses blocking I/O (Socket-based)
- Single-node cache
- No HTTPS support
- No connection pooling

---

## 🔮 Future Improvements
- Non-blocking I/O (Java NIO)
- Distributed caching (Redis)
- Connection pooling
- HTTPS support
- API Gateway extensions

---

## 📚 Key Learnings
- Handling concurrent network requests
- Designing cache systems (LRU + TTL)
- Implementing rate limiting
- Measuring system performance
- Building scalable backend components

---

## 🎯 Conclusion
This project demonstrates the core concepts behind proxy servers and illustrates techniques commonly used in systems such as reverse proxies, CDNs, and API gateways to improve performance and manage traffic.

---

## 👨‍💻 Author

**Karthik Naik**

Backend Developer | Java | Spring Boot | Backend Systems

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

If you found this project useful, consider giving it a ⭐ on GitHub.
