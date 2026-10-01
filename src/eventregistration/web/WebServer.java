package eventregistration.web;

import eventregistration.Event;
import eventregistration.EventDAO;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class WebServer {

        public static void main(String[] args) throws IOException {

                int port = 8080;

                HttpServer server = HttpServer.create(
                                new InetSocketAddress(port),
                                0);

                // =========================
                // HOME PAGE
                // =========================

                server.createContext("/", exchange -> {

                        serveFile(
                                        exchange,
                                        "src/eventregistration/web/index.html",
                                        "text/html");

                });

                // =========================
                // CSS
                // =========================

                server.createContext("/css/style.css", exchange -> {

                        serveFile(
                                        exchange,
                                        "src/eventregistration/web/css/style.css",
                                        "text/css");

                });

                // =========================
                // JAVASCRIPT
                // =========================

                server.createContext("/js/script.js", exchange -> {

                        serveFile(
                                        exchange,
                                        "src/eventregistration/web/js/script.js",
                                        "application/javascript");

                });

                // =========================
                // EVENTS API
                // =========================

                server.createContext("/api/events", exchange -> {

                        try {

                                EventDAO eventDAO = new EventDAO();

                                ArrayList<Event> events = eventDAO.getAllEvents();

                                StringBuilder json = new StringBuilder();

                                json.append("[");

                                for (int i = 0; i < events.size(); i++) {

                                        Event event = events.get(i);

                                        json.append("{");

                                        json.append("\"name\":\"")
                                                        .append(event.getEventName())
                                                        .append("\",");

                                        json.append("\"date\":\"")
                                                        .append(event.getDate())
                                                        .append("\",");

                                        json.append("\"location\":\"")
                                                        .append(event.getLocation())
                                                        .append("\",");

                                        json.append("\"capacity\":")
                                                        .append(event.getMaximumCapacity());

                                        json.append("}");

                                        if (i < events.size() - 1) {
                                                json.append(",");
                                        }
                                }

                                json.append("]");

                                byte[] response = json.toString().getBytes();

                                exchange.getResponseHeaders()
                                                .set(
                                                                "Content-Type",
                                                                "application/json");

                                exchange.sendResponseHeaders(
                                                200,
                                                response.length);

                                exchange.getResponseBody()
                                                .write(response);

                                exchange.close();

                        } catch (Exception e) {

                                e.printStackTrace();

                                String error = "{\"error\":\"Unable to load events\"}";

                                byte[] response = error.getBytes();

                                exchange.getResponseHeaders()
                                                .set(
                                                                "Content-Type",
                                                                "application/json");

                                exchange.sendResponseHeaders(
                                                500,
                                                response.length);

                                exchange.getResponseBody()
                                                .write(response);

                                exchange.close();
                        }

                });

                // =========================
                // START SERVER
                // =========================

                server.start();

                System.out.println(
                                "Web server started at http://localhost:"
                                                + port);
        }

        // =========================
        // FILE SERVER METHOD
        // =========================

        private static void serveFile(
                        HttpExchange exchange,
                        String filePath,
                        String contentType) {

                try {

                        Path path = Path.of(filePath);

                        if (!Files.exists(path)) {

                                String error = "File not found.";

                                byte[] response = error.getBytes();

                                exchange.sendResponseHeaders(
                                                404,
                                                response.length);

                                exchange.getResponseBody()
                                                .write(response);

                                exchange.close();

                                return;
                        }

                        byte[] response = Files.readAllBytes(path);

                        exchange.getResponseHeaders()
                                        .set(
                                                        "Content-Type",
                                                        contentType);

                        exchange.sendResponseHeaders(
                                        200,
                                        response.length);

                        exchange.getResponseBody()
                                        .write(response);

                        exchange.close();

                } catch (IOException e) {

                        e.printStackTrace();
                }
        }
}