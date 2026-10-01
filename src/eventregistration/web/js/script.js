// Events API
server.createContext("/api/events", exchange -> {

    try {
        EventDAO eventDAO = new EventDAO();

        java.util.ArrayList < Event > events =
        eventDAO.getAllEvents();

        StringBuilder json = new StringBuilder();
        json.append("[");

        for(int i = 0; i<events.size(); i++) {

            Event event = events.get(i);

    json.append("{")
        .append("\"name\":\"")
        .append(event.getEventName())
        .append("\",")
        .append("\"date\":\"")
        .append(event.getDate())
        .append("\",")
        .append("\"location\":\"")
        .append(event.getLocation())
        .append("\",")
        .append("\"capacity\":")
        .append(event.getMaximumCapacity())
        .append("}");

    if (i < events.size() - 1) {
        json.append(",");
    }
}

json.append("]");

byte[] response =
    json.toString().getBytes();

exchange.getResponseHeaders()
    .set("Content-Type", "application/json");

exchange.sendResponseHeaders(
    200,
    response.length
);

exchange.getResponseBody()
    .write(response);

exchange.close();

    } catch (Exception e) {

    e.printStackTrace();

        String error =
        "{\"error\":\"Unable to load events\"}";

    byte[] response = error.getBytes();

    exchange.getResponseHeaders()
        .set("Content-Type", "application/json");

    exchange.sendResponseHeaders(
        500,
        response.length
    );

    exchange.getResponseBody()
        .write(response);

    exchange.close();
}
});