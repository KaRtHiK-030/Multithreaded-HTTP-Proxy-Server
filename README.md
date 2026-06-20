<div align="center">

# 🌐 Multithreaded HTTP Proxy Server

### 🚀 High-Performance Java Proxy Server for Concurrent HTTP Request Handling

*An efficient and lightweight HTTP Proxy Server built using Java Socket Programming and Multithreading. The server acts as an intermediary between clients and web servers, handling multiple requests simultaneously while demonstrating core networking and system programming concepts.*

<br/>

![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Sockets](https://img.shields.io/badge/Networking-Socket%20Programming-blue?style=for-the-badge)
![Threads](https://img.shields.io/badge/Concurrency-Multithreading-green?style=for-the-badge)
![HTTP](https://img.shields.io/badge/Protocol-HTTP-red?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

</div>

---

# 📋 Table of Contents

* [Overview](#-overview)
* [Features](#-features)
* [Tech Stack](#-tech-stack)
* [Architecture](#-architecture)
* [Project Structure](#-project-structure)
* [How It Works](#-how-it-works)
* [Prerequisites](#-prerequisites)
* [Installation & Setup](#️-installation--setup)
* [Compilation](#-compilation)
* [Running the Server](#-running-the-server)
* [Example Workflow](#-example-workflow)
* [Learning Outcomes](#-learning-outcomes)
* [Future Enhancements](#-future-enhancements)
* [Author](#-author)

---

# 📖 Overview

**Multithreaded HTTP Proxy Server** is a networking project developed in Java that acts as an intermediary between clients and web servers.

The proxy server receives HTTP requests from clients, forwards them to the destination server, receives the response, and sends it back to the client.

To efficiently handle multiple users at the same time, the server creates a dedicated thread for each client connection, ensuring smooth and concurrent request processing.

This project demonstrates practical implementation of:

* Computer Networks
* Socket Programming
* Multithreading
* Client-Server Communication
* HTTP Protocol Handling
* Proxy Server Architecture

---

# ✨ Features

### 🌍 HTTP Request Forwarding

* Receives client HTTP requests
* Forwards requests to target web servers
* Returns server responses back to clients

### ⚡ Concurrent Client Handling

* Dedicated thread for every client connection
* Supports multiple simultaneous users
* Non-blocking client processing

### 📡 Socket-Based Communication

* Built entirely using Java sockets
* Direct TCP communication between systems

### 📝 Request Logging

* Tracks incoming client requests
* Useful for debugging and monitoring

### 🚀 Lightweight Design

* Minimal dependencies
* Fast startup and execution

---

# 🛠️ Tech Stack

| Layer            | Technology              |
| ---------------- | ----------------------- |
| Language         | Java                    |
| Networking       | Java Socket API         |
| Concurrency      | Java Multithreading     |
| Protocol         | HTTP                    |
| Development Tool | IntelliJ IDEA / VS Code |
| Version Control  | Git & GitHub            |

---

# 🏗️ Architecture

The server follows a simple Proxy Architecture where every incoming client request is handled by an independent thread.

```text
+------------+
|   Client   |
+------------+
       |
       v
+-------------------+
|  Proxy Server     |
|  (Main Thread)    |
+-------------------+
       |
       v
+-------------------+
| Client Handler    |
| (Separate Thread) |
+-------------------+
       |
       v
+-------------------+
|   Web Server      |
+-------------------+
       |
       v
+------------+
|  Response  |
+------------+
```

### Request Flow

```text
Client
   │
   ▼
Proxy Server
   │
   ▼
Client Handler Thread
   │
   ▼
Target Web Server
   │
   ▼
Response Returned
   │
   ▼
Client
```

---

# 📂 Project Structure

```text
multithreaded-http-proxy-server/
│
├── src/
│   ├── ProxyServer.java
│   ├── ClientHandler.java
│   ├── RequestParser.java
│   └── ...
│
├── README.md
├── .gitignore
└── LICENSE
```

---

# ⚙️ How It Works

### Step 1 — Server Startup

The proxy server starts and listens on a predefined port.

### Step 2 — Client Connection

A client sends an HTTP request to the proxy server.

### Step 3 — Thread Creation

The server accepts the connection and spawns a new thread.

### Step 4 — Request Processing

The ClientHandler thread processes the request and forwards it to the target server.

### Step 5 — Response Handling

The web server sends back a response.

### Step 6 — Response Delivery

The proxy server forwards the response to the client.

---

# ✅ Prerequisites

Before running the project, ensure the following are installed:

| Tool                    | Version  |
| ----------------------- | -------- |
| Java JDK                | 8+       |
| Git                     | Latest   |
| IntelliJ IDEA / VS Code | Optional |

Verify Java installation:

```bash
java -version
javac -version
```

---

# ⚙️ Installation & Setup

### Clone the Repository

```bash
git clone https://github.com/KaRtHiK-030/multithreaded-http-proxy-server.git
```

### Navigate to Project Directory

```bash
cd multithreaded-http-proxy-server
```

---

# 🔨 Compilation

Move to the source directory:

```bash
cd src
```

Compile all Java files:

```bash
javac *.java
```

---

# ▶️ Running the Server

Start the proxy server:

```bash
java ProxyServer
```

Expected output:

```text
Proxy Server started successfully...
Listening on port 8080...
```

---

# 🔄 Example Workflow

### Client Request

```text
GET http://example.com HTTP/1.1
```

### Processing

```text
Client
   ↓
Proxy Server
   ↓
Target Server
   ↓
Proxy Server
   ↓
Client
```

### Response

```text
HTTP/1.1 200 OK
Content-Type: text/html
...
```

---

# 🎯 Learning Outcomes

Through this project, I gained hands-on experience with:

* Java Socket Programming
* TCP/IP Communication
* HTTP Request Processing
* Multithreaded Programming
* Client-Server Architecture
* Concurrent Request Handling
* Proxy Server Design Patterns
* Network Programming Fundamentals

---

# 📈 Future Enhancements

| Feature                     | Description                                            |
| --------------------------- | ------------------------------------------------------ |
| 💾 Caching                  | Store frequently accessed pages to improve performance |
| 🔒 HTTPS Support            | Support secure HTTPS connections                       |
| 🚫 URL Filtering            | Block access to restricted websites                    |
| 📊 Monitoring Dashboard     | Real-time traffic analytics                            |
| 🔐 Authentication           | User authentication and access control                 |
| 📝 Advanced Logging         | Detailed request and response logs                     |
| ⚖️ Load Balancing           | Distribute traffic efficiently                         |
| 🚀 Performance Optimization | Connection pooling and thread management               |

---

# 👨‍💻 Author

<div align="center">

## Karthik Naik

**BE in Computer Science Engineering**

Backend Developer • Java Enthusiast • System Design Learner

[![GitHub](https://img.shields.io/badge/GitHub-KaRtHiK--030-181717?style=for-the-badge\&logo=github)](https://github.com/KaRtHiK-030)

</div>

---

<div align="center">

⭐ If you found this project useful, consider giving it a star on GitHub!

</div>
