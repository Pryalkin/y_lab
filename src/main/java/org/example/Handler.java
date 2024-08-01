package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Handler extends Thread {

    private static final Map<String, String> CONTENT_TYPES = new HashMap<>() {{
        put("jpg", "image/jpeg");
        put("html", "text/html");
        put("json", "application/json");
        put("txt", "text/plain");
        put("", "text/plain");
    }};

    private static final String NOT_FOUND_MESSAGE = "NOT FOUND";

    private Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var output = this.socket.getOutputStream()) {
            String requestLine = input.readLine();
            System.out.println("Полученная строка запроса: " + requestLine);

            // Анализируем первую строку запроса
            String[] parts = requestLine.split(" ");
            String method = parts[0];

            if ("GET".equals(method)) {
                System.out.println("Это GET-запрос");
                // Обработка GET-запроса
            } else if ("POST".equals(method)) {
                System.out.println("Это POST-запрос");
                // Получаем длину тела запроса
                int contentLength = 0;
                while ((requestLine = input.readLine()) != null && !requestLine.isEmpty()) {
                    if (requestLine.startsWith("Content-Length: ")) {
                        // Извлекаем значение Content-Length
                        contentLength = Integer.parseInt(requestLine.split(": ")[1]);
                        System.out.println("Content-Length: " + contentLength);
                    }
                }

                // Читаем тело запроса
                StringBuilder body = new StringBuilder();
                for (int i = 0; i < contentLength; i++) {
                    body.append((char) input.read());
                }
                String requestBody = body.toString();
                System.out.println("Тело запроса: " + requestBody);

                // Парсим JSON-данные
//                Gson gson = new GsonBuilder().create();
//                MyData data = gson.fromJson(requestBody, MyData.class);

                // Обрабатываем данные
//                System.out.println("Получены данные: " + data.name + ", " + data.age);

                // Формируем ответ (JSON)
//                String responseBody = gson.toJson(new ResponseData("Успех!", "Данные получены"));
//                System.out.println("Ответ: " + responseBody);
//
//                // Отправляем ответ клиенту
//                output.write(("HTTP/1.1 200 OK\r\n" +
//                        "Content-Type: application/json\r\n" +
//                        "Content-Length: " + responseBody.length() + "\r\n\r\n" +
//                        responseBody).getBytes());
            } else {
                System.out.println("Неизвестный метод запроса: " + method);
                // Обработка неизвестного метода
            }

            // Отправка ответа клиенту
            // ...







//            var url = this.getRequestUrl(input);
//            var filePath = Path.of(this.directory, url);
//            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
//                var extension = this.getFileExtension(filePath);
//                var type = CONTENT_TYPES.get(extension);
//                var fileBytes = Files.readAllBytes(filePath);
//                this.sendHeader(output, 200, "OK", type, fileBytes.length);
//                output.write(fileBytes);
//            } else {
//                var type = CONTENT_TYPES.get("text");
//                this.sendHeader(output, 404, "Not Found", type, NOT_FOUND_MESSAGE.length());
//                output.write(NOT_FOUND_MESSAGE.getBytes());
//            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String getRequestUrl(InputStream input) {
        var reader = new Scanner(input).useDelimiter("\r\n");
        var line = reader.next();
        return line.split(" ")[1];
    }

    private String getFileExtension(Path path) {
        var name = path.getFileName().toString();
        var extensionStart = name.lastIndexOf(".");
        return extensionStart == -1 ? "" : name.substring(extensionStart + 1);
    }

    private void sendHeader(OutputStream output, int statusCode, String statusText, String type, long lenght) {
        var ps = new PrintStream(output);
        ps.printf("HTTP/1.1 %s %s%n", statusCode, statusText);
        ps.printf("Content-Type: %s%n", type);
        ps.printf("Content-Length: %s%n%n", lenght);
    }
}
