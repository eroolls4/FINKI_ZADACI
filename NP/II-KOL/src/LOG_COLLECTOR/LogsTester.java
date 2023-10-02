//package LOG_COLLECTOR;
//
//import java.sql.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//
//abstract class Log{
//    //service_name microservice_name type message timestamp
//    String serviceName;
//    String microserviceName;
//    long timestamp;
//
//
//
//
//    abstract int getSeverity(String message);
//
//}
//
//
//class LogFactory{
//    //addLog service2 microservice3 ERROR Log message 2. 252
//    public static Log  createLog(String fullLog){
//        String[] parts = fullLog.split("\\s+");
//        String service = parts[0];
//        String microservice = parts[1];
//        String type = parts[2];
//        long timestamp = Long.parseLong(parts[parts.length - 1]);
//        String message = Arrays.stream(parts).skip(3).limit(parts.length - 1).collect(Collectors.joining(" "));
//        if (type.equalsIgnoreCase("info")) {
//            return new Info(service, microservice, type, message, timestamp);
//        } else if (type.equalsIgnoreCase("warn")) {
//            return new Warn(service, microservice, type, message, timestamp);
//        } else {
//            return new Error(service, microservice, type, message, timestamp);
//        }
//    }
//}
//
//class Info extends Log{
//
//    @Override
//    int getSeverity(String message) {
//        return 0;
//    }
//}
//
//class Warn extends Log{
//
//    @Override
//    int getSeverity(String message) {
//       return  message.toLowerCase().contains("might cause error") ? 2 : 1;
//    }
//}
//
//class Error extends Log{
//
//    @Override
//    int getSeverity(String message) {
//        int x=3;
//        if(message.contains("fatal")){
//            x +=2;
//        }else if(message.contains("exception")){
//            x +=3;
//        }
//        return x;
//    }
//}
//
//
//
//
//class Microservice{
//
//}
//
//class Service{
//
//
//
//}
//
//
//class LogCollector{
//
//    List<Log> logs;
//
//
//    public LogCollector() {
//        logs=new ArrayList<>();
//    }
//
//    public void addLog(String addLog) {
//        //service_name microservice_name type message timestamp
//
//    }
//
//    public void printServicesBySeverity() {
//
//    }
//
//   public Map<Integer, Integer> getSeverityDistribution (String service, String microservice) {
//    }
//
//
//    public void displayLogs(String service, String microservice, String order) {
//    }
//}
//
//public class LogsTester {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        LogCollector collector = new LogCollector();
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            if (line.startsWith("addLog")) {
//                collector.addLog(line.replace("addLog ", ""));
//            } else if (line.startsWith("printServicesBySeverity")) {
//                collector.printServicesBySeverity();
//            } else if (line.startsWith("getSeverityDistribution")) {
//                String[] parts = line.split("\\s+");
//                String service = parts[1];
//                String microservice = null;
//                if (parts.length == 3) {
//                    microservice = parts[2];
//                }
//                collector.getSeverityDistribution(service, microservice).forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
//            } else if (line.startsWith("displayLogs")) {
//                String[] parts = line.split("\\s+");
//                String service = parts[1];
//                String microservice = null;
//                String order = null;
//                if (parts.length == 4) {
//                    microservice = parts[2];
//                    order = parts[3];
//                } else {
//                    order = parts[2];
//                }
//                System.out.println(line);
//                collector.displayLogs(service, microservice, order);
//            }
//        }
//    }
//}
