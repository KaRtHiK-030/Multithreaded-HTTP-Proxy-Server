import java.io.*;
import java.net.*;

public class ClientSide {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected to proxy");

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // 🔥 Toggle this
            boolean showMetrics = true;

            String request;

            if (showMetrics) {
                request =
                        "GET /metrics HTTP/1.1\r\n" +
                                "Host: localhost\r\n" +
                                "Connection: close\r\n" +
                                "\r\n";
            } else {
                String host = "www.google.com";
                request =
                        "GET / HTTP/1.1\r\n" +
                                "Host: " + host + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n";
            }

            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                System.out.write(buffer, 0, bytesRead);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}