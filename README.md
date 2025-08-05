# Task-Tracker

Task-Tracker es una aplicación Java para gestionar tareas desde la consola. Permite agregar, listar, actualizar, marcar como en progreso, marcar como hechas y eliminar tareas de manera sencilla.

## Características

- Agregar nuevas tareas
- Listar todas las tareas
- Actualizar el contenido de una tarea
- Marcar tareas como "en progreso" o "hechas"
- Eliminar tareas

## Requisitos

- Java 17 o superior

## Compilación

Puedes compilar el proyecto desde la terminal con: javac -d out src*.java

O usando IntelliJ IDEA desde el menú `Build > Build Project`.

## Ejecución

Ejecuta los siguientes comandos desde la terminal para interactuar con la aplicación:

- Agregar una tarea: java -cp out/production/Task-Tracker Main add "Mi primera tarea"
- Listar tareas: java -cp out/production/Task-Tracker Main list
- Actualizar una tarea: java -cp out/production/Task-Tracker Main update 1 "Tarea actualizada"
- Marcar como en progreso: java -cp out/production/Task-Tracker Main mark-in-progress 1
- Marcar como hecha: java -cp out/production/Task-Tracker Main mark-done 1
- Eliminar una tarea: java -cp out/production/Task-Tracker Main delete 1

## Enlace al proyecto

[https://github.com/JulianISGON/Task-Tracker](https://github.com/JulianISGON/Task-Tracker) tomado de https://roadmap.sh/projects/task-tracker