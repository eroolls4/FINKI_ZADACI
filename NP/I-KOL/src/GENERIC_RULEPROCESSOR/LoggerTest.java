package GENERIC_RULEPROCESSOR;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface ILog {
    //TODO write methods definitions here;

    public String getType();

    public String getMessage();

    public void setMessage(String newMessage);

    public long getTimestamp();

}


interface LogProcessor<IN extends ILog & Comparable<IN>> {
        ArrayList<IN> processLogs(ArrayList<IN> logs);
}

class LogSystem<IN extends ILog & Comparable<IN>> {

    ArrayList<IN> logsList;

    public LogSystem(ArrayList<IN> logovi) {
        this.logsList = logovi;
    }

    void printResults() {

        //TODO define concrete log processors with lambda expressions

        LogProcessor<IN> firstLogProcessor =(logs) -> logs.stream()
                                                           .filter(l->l.getType().equals("INFO"))
                                                           .collect(Collectors.toCollection(ArrayList::new));


        LogProcessor<IN> firstLogProcessorA = new LogProcessor<IN>() {
            @Override
            public ArrayList<IN> processLogs(ArrayList<IN> logs) {
                ArrayList<IN> ins = new ArrayList<>();
                for (IN l : logs) {
                    if (l.getType().equals("INFO")) {
                        ins.add(l);
                    }
                }
                return ins;
            }
        };



        LogProcessor<IN> secondLogProcessor = (logs) -> logs.stream()
                                                            .filter(l->l.getMessage().length()<100)
                                                            .collect(Collectors.toCollection(ArrayList::new));


        LogProcessor<IN> secondLogProcessorA = new LogProcessor<IN>() {
            @Override
            public ArrayList<IN> processLogs(ArrayList<IN> logs) {
                ArrayList<IN> ins = new ArrayList<>();
                for (IN l : logs) {
                    if (l.getMessage().length() < 100) {
                        ins.add(l);
                    }
                }
                return ins;
            }
        };




        LogProcessor<IN> thirdLogProcessor = (logs) -> logs.stream()
                                                           .sorted()
                                                           .collect(Collectors.toCollection(ArrayList::new));


              LogProcessor<IN> thirdLogProcessorA = new LogProcessor<IN>() {
                  @Override
                  public ArrayList<IN> processLogs(ArrayList<IN> logs) {
                      ArrayList<IN> ins = new ArrayList<>();
                      for (IN log : logs) {
                          ins.add(log);
                      }
                      ins.sort(null);
                      return ins;
                  }
              };




        System.out.println("RESULTS FROM THE FIRST LOG PROCESSOR");
        firstLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

        System.out.println("RESULTS FROM THE SECOND LOG PROCESSOR");
        secondLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

        System.out.println("RESULTS FROM THE THIRD LOG PROCESSOR");
        thirdLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));
        }
        }

class RealLog implements ILog, Comparable<RealLog> {
    String type;
    String message;
    long timestamp;
    static Random rdm = new Random();
    static int index = 100;

    public RealLog(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(RealLog o) {
        return Long.compare(this.timestamp, o.timestamp);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String newMessage) {
        this.message = newMessage;
    }


    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static RealLog createLog(String line) {
        String[] parts = line.split("\\s+");
        String date = parts[0] + "T" + parts[1];
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZoneId zoneId = ZoneId.systemDefault();
        long timestamp = ldt.atZone(zoneId).toEpochSecond() * 1000 + index;
        ++index;
        String type = parts[3];
        String message = Arrays.stream(parts).skip(4).collect(Collectors.joining(" "));
        return new RealLog(type, message, timestamp);
    }

    @Override
    public String toString() {
        return String.format("%d [%s] %s", timestamp, type, message);
    }
}

class DummyLog implements ILog, Comparable<DummyLog> {
    String type;
    String message;
    long timestamp;

    public DummyLog(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(DummyLog o) {
        return Long.compare(this.getTimestamp(), o.getTimestamp());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String newMessage) {
        this.message = message;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static DummyLog createLog(String line) {
        String[] parts = line.split("\\s+");
        return new DummyLog(parts[0], parts[1], Long.parseLong(parts[2]));
    }

    @Override
    public String toString() {
        return "DummyLog{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

public class LoggerTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int firstCount = Integer.parseInt(sc.nextLine());

        ArrayList<RealLog> realLogs = IntStream.range(0, firstCount)
                .mapToObj(i -> RealLog.createLog(sc.nextLine()))
                .collect(Collectors.toCollection(ArrayList::new));

        int secondCount = Integer.parseInt(sc.nextLine());

        ArrayList<DummyLog> dummyLogs = IntStream.range(0, secondCount)
                .mapToObj(i -> DummyLog.createLog(sc.nextLine()))
                .collect(Collectors.toCollection(ArrayList::new));

        LogSystem<RealLog> realLogSystem = new LogSystem<>(realLogs);
        LogSystem<DummyLog> dummyLogSystem = new LogSystem<>(dummyLogs);

        System.out.println("===REAL LOGS SYSTEM RESULTS===");
        realLogSystem.printResults();
        System.out.println("===DUMMY LOGS SYSTEM RESULTS===");
        dummyLogSystem.printResults();

    }
}
