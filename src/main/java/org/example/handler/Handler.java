package org.example.handler;

import org.example.annotation.Url;
import org.example.controller.Controller;
import org.example.factory.Factory;
import org.example.model.User;
import org.example.service.Service;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Handler(Socket socket) {
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
            String uri = parts[1];
            System.out.println(uri);

            Method[] methods = Factory.getController().getClass().getMethods();
            for (Method m: methods){
                if(m.isAnnotationPresent(Url.class)){
                    Url an = m.getAnnotation(Url.class);
                    if(an.name().equals(uri)){
                        if(an.method().equals(method)){
                            Parameter[] parameters = m.getParameters();
                            if(parameters.length > 0){
//                                parseObject();
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
                                Map<String, String> jsonObject = parseJson(requestBody);

                                Field[] fields = parameters[0].getType().getDeclaredFields();
                                Method[] methodsModel = parameters[0].getType().getDeclaredMethods();

                                for(Method methodModel: methodsModel){
                                    System.out.println(methodModel.getName());
                                }

                                List<String> fieldsModel = new ArrayList<>();
                                for(Field field: fields){
                                    jsonObject.keySet().forEach(key -> {
                                        if(key.equals(field.getName())) fieldsModel.add(key);
                                    });
                                }

                                fieldsModel.forEach(System.out::println);

                               Class<?> model = parameters[0].getType();
                                try {
                                   Constructor<?> constructor = model.getConstructor();
                                    Object object = constructor.newInstance();
                                    Field[] fieldsObj = object.getClass().getDeclaredFields();
                                    for(Field field: fieldsObj){
                                        fieldsModel.forEach(f ->{
                                            if (f.equals(field.getName())){
                                                field.setAccessible(true);
                                                try {
                                                    System.out.println(jsonObject.get(f));
                                                    field.set(object, jsonObject.get(f));
                                                } catch (IllegalAccessException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        });
                                    }
                                    m.invoke(Factory.getController(), object);
                                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                                         InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }

                            }
//                            try {
//                                m.invoke(Factory.getController(), new User());
//                            } catch (IllegalAccessException | InvocationTargetException e) {
//                                throw new RuntimeException(e);
//                            }
                        }
                    }
                }

            }

//            if ("GET".equals(method)) {
//                System.out.println("Это GET-запрос");
//                // Обработка GET-запроса
//            } else if ("POST".equals(method)) {
//                System.out.println("Это POST-запрос");
//                // Получаем длину тела запроса
//                int contentLength = 0;
//                while ((requestLine = input.readLine()) != null && !requestLine.isEmpty()) {
//                    if (requestLine.startsWith("Content-Length: ")) {
//                        // Извлекаем значение Content-Length
//                        contentLength = Integer.parseInt(requestLine.split(": ")[1]);
//                        System.out.println("Content-Length: " + contentLength);
//                    }
//                }
//
//                // Читаем тело запроса
//                StringBuilder body = new StringBuilder();
//                for (int i = 0; i < contentLength; i++) {
//                    body.append((char) input.read());
//                }
//                String requestBody = body.toString();
//                System.out.println("Тело запроса: " + requestBody);

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
//            } else {
//                System.out.println("Неизвестный метод запроса: " + method);
//                // Обработка неизвестного метода
//            }

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

    private static Map<String, String> parseJson(String jsonString) {
        Map<String, String> jsonObject = new HashMap<>();
        String key = null;
        String value = null;
        boolean inKey = false;
        boolean inValue = false;
        boolean inString = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < jsonString.length(); i++) {
            char ch = jsonString.charAt(i);

            switch (ch) {
                case '"':
                    if (!inKey && !inValue) {
                        inKey = true;
                        break;
                    }
                    if (inKey) {
                       inKey = false;
                       key = sb.toString();
                       sb.setLength(0);
                       break;
                    }
                    if (!inKey && inValue && !inString) {
                        inString = true;
                        break;
                    }
                    if (inString) {
                        inValue = false;
                        inString = false;
                        value = sb.toString();
                        sb.setLength(0);
                        break;
                    }
                case ',':
                    if(inString){
                        sb.append(ch);
                        break;
                    }
                    if(inValue){
                        inValue = false;
                        value = sb.toString();
                        sb.setLength(0);
                        break;
                    }
                case '{':
                case '}':
                case '\r':
                    break;
                case ':':
                    inValue = true;
                    break;
                case '\n':
                    if(!(key == null) && !(value == null)){
                        jsonObject.put(key, value);
                        key = null;
                        value = null;
                    }
                    break;
                case 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9:
                    sb.append(ch);
                    break;
                case ' ':
                    if(sb.isEmpty()) break;
                    else sb.append(ch);
                    break;
                default:
                    sb.append(ch);
            }
        }
        return jsonObject;
    }

}


