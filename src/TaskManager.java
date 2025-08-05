import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class TaskManager {
    private final String filename;

    public TaskManager(String filename) {
        this.filename = filename;
    }

    private List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            Files.write(path, "[]".getBytes());
        }
        String content = new String(Files.readAllBytes(path)).trim();
        if (content.length() <= 2) return tasks; // array vacío
        // Separar objetos JSON correctamente
        String[] items = content.substring(1, content.length() - 1).split("(?<=}),\\s*(?=\\{)");
        for (String item : items) {
            Map<String, String> map = parseJsonObject(item);
            Task t = new Task(
                    Integer.parseInt(map.get("id")),
                    map.get("description"),
                    map.get("status"),
                    map.get("createdAt"),
                    map.get("updatedAt")
            );
            tasks.add(t);
        }
        return tasks;
    }

    private void saveTasks(List<Task> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            sb.append("{");
            sb.append("\"id\":").append(t.id).append(",");
            sb.append("\"description\":\"").append(escape(t.description)).append("\",");
            sb.append("\"status\":\"").append(t.status).append("\",");
            sb.append("\"createdAt\":\"").append(t.createdAt).append("\",");
            sb.append("\"updatedAt\":\"").append(t.updatedAt).append("\"");
            sb.append("}");
            if (i < tasks.size() - 1) sb.append(",");
        }
        sb.append("]");
        Files.write(Paths.get(filename), sb.toString().getBytes());
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // Mejorado: separa por comas fuera de comillas
    private Map<String, String> parseJsonObject(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
        String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            String key = kv[0].replaceAll("[\"{}]", "").trim();
            String value = kv[1].trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            map.put(key, value);
        }
        return map;
    }

    public void addTask(String description) throws IOException {
        List<Task> tasks = loadTasks();
        int id = tasks.stream().mapToInt(t -> t.id).max().orElse(0) + 1;
        String now = LocalDateTime.now().toString();
        Task t = new Task(id, description, "todo", now, now);
        tasks.add(t);
        saveTasks(tasks);
        System.out.println("Tarea agregada exitosamente (ID: " + id + ")");
    }

    public void updateTask(int id, String description) throws IOException {
        List<Task> tasks = loadTasks();
        boolean found = false;
        for (Task t : tasks) {
            if (t.id == id) {
                t.description = description;
                t.updatedAt = LocalDateTime.now().toString();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Tarea no encontrada.");
            return;
        }
        saveTasks(tasks);
        System.out.println("Tarea actualizada.");
    }

    public void deleteTask(int id) throws IOException {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(t -> t.id == id);
        if (!removed) {
            System.out.println("Tarea no encontrada.");
            return;
        }
        saveTasks(tasks);
        System.out.println("Tarea eliminada.");
    }

    public void markTask(int id, String status) throws IOException {
        List<Task> tasks = loadTasks();
        boolean found = false;
        for (Task t : tasks) {
            if (t.id == id) {
                t.status = status;
                t.updatedAt = LocalDateTime.now().toString();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Tarea no encontrada.");
            return;
        }
        saveTasks(tasks);
        System.out.println("Tarea marcada como " + status + ".");
    }

    public void listTasks(String status) throws IOException {
        List<Task> tasks = loadTasks();
        for (Task t : tasks) {
            if (status == null || t.status.equals(status)) {
                System.out.println("ID: " + t.id + " | " + t.description + " | " + t.status + " | Creado: " + t.createdAt + " | Actualizado: " + t.updatedAt);
            }
        }
    }
}