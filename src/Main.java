public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: task-cli <comando> [argumentos]");
            return;
        }
        TaskManager manager = new TaskManager("tasks.json");
        String command = args[0];
        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.out.println("Falta la descripción de la tarea.");
                        return;
                    }
                    manager.addTask(args[1]);
                    break;
                case "update":
                    if (args.length < 3) {
                        System.out.println("Faltan argumentos para actualizar.");
                        return;
                    }
                    manager.updateTask(Integer.parseInt(args[1]), args[2]);
                    break;
                case "delete":
                    if (args.length < 2) {
                        System.out.println("Falta el ID de la tarea.");
                        return;
                    }
                    manager.deleteTask(Integer.parseInt(args[1]));
                    break;
                case "mark-in-progress":
                    if (args.length < 2) {
                        System.out.println("Falta el ID de la tarea.");
                        return;
                    }
                    manager.markTask(Integer.parseInt(args[1]), "in-progress");
                    break;
                case "mark-done":
                    if (args.length < 2) {
                        System.out.println("Falta el ID de la tarea.");
                        return;
                    }
                    manager.markTask(Integer.parseInt(args[1]), "done");
                    break;
                case "list":
                    if (args.length == 1) {
                        manager.listTasks(null);
                    } else {
                        manager.listTasks(args[1]);
                    }
                    break;
                default:
                    System.out.println("Comando no reconocido.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}