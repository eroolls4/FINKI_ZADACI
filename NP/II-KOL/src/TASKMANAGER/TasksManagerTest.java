//package TASKMANAGER;
//
//
//import java.io.*;
//import java.time.*;
//import java.util.*;
//import java.util.stream.*;
//
//class DeadlineNotValidException extends Exception{
//
//}
//
//class Task{
//
//    String category;
//    String name;
//    String description;
//    LocalDateTime deadLine;
//    int priority;
//
//    public Task(String category, String name, String description, LocalDateTime validity, int priority) {
//        this.category = category;
//        this.name = name;
//        this.description = description;
//        this.deadLine = validity;
//        this.priority = priority;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public LocalDateTime getValidity() {
//        return deadLine;
//    }
//
//    public int getPriority() {
//        return priority;
//    }
//
//
//    @Override
//    public String toString() {
//        return "Task{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", deadLine=" + deadLine +
//                ", priority=" + priority +
//                '}';
//    }
//}
//
//
//class TaskFactory{
//
//
//    //[категорија][име_на_задача],[oпис],[рок_за_задачата],[приоритет].
//    public  static Task  createTask(String line){
//         String [] parts=line.split(",");
//
//
//    }
//}
//
//class TaskManager{
//    List<Task> tasks;
//
//
//    public TaskManager() {
//        tasks=new ArrayList<>();
//    }
//
//
//    void readTasks (InputStream inputStream){
//
//        tasks=new BufferedReader(new InputStreamReader(inputStream)).lines()
//                      .map(line->TaskFactory.createTask(line))
//                      .collect(Collectors.toList());
//
//    }
//
//    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory){
//        PrintWriter pw=new PrintWriter(os,true);
//    }
//}
//
//public class TasksManagerTest {
//
//    public static void main(String[] args) {
//
//        TaskManager manager = new TaskManager();
//
//        System.out.println("Tasks reading");
//        manager.readTasks(System.in);
//        System.out.println("By categories with priority");
//        manager.printTasks(System.out, true, true);
//        System.out.println("-------------------------");
//        System.out.println("By categories without priority");
//        manager.printTasks(System.out, false, true);
//        System.out.println("-------------------------");
//        System.out.println("All tasks without priority");
//        manager.printTasks(System.out, false, false);
//        System.out.println("-------------------------");
//        System.out.println("All tasks with priority");
//        manager.printTasks(System.out, true, false);
//        System.out.println("-------------------------");
//
//    }
//}
//
