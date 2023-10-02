package EVENT_CALENDAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.*;


class WrongDateException extends Exception{
    public WrongDateException(Date date) {
        super(String.format("Wrong date: %s",date.toString().replace("GMT","UTC")));
    }
}


class Event{
    String name;
    String location;
    Date date;

    public static DateFormat df = new SimpleDateFormat("dd MMM, yyyy HH:mm");

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }


    public int getYear(){
         return date.getYear();
    }


    public long getTime(){
        return date.getTime();
    }

    public String getName() {
        return name;
    }

    public int getMonth(){
        return date.getMonth();
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s",df.format(date),location,name);
    }
}


class EventCalendar{
    int year;
    List<Event> events;

    Map<String,List<Event>> eventsByMonth;

    Map<Integer,Integer> numberOfEventsByMonth;

    Comparator<Event> eventComparator=Comparator.comparing(Event::getTime).thenComparing(Event::getName);


    public EventCalendar(int year) {
        this.year = year - 1900;
        this.events=new ArrayList<>();
        eventsByMonth=new HashMap<>();
        numberOfEventsByMonth=new HashMap<>();

        IntStream.range(1,13).map( i -> Integer.parseInt(String.valueOf(i)))
                             .boxed()
                             .forEach(i-> numberOfEventsByMonth.put(i,0));
    }


    private String makeKey(Date date){
        return Arrays.stream(date.toString()
                     .split("\\s+"))
                     .limit(3)
                     .collect(Collectors.joining(" "));
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
           Event event=new Event(name,location,date);

           if(event.getYear() != year){
               throw new WrongDateException(date);
           }
           events.add(event);

           //Wed Apr 16 14:20:00 GMT 2014
           String key= makeKey(date);
           eventsByMonth.putIfAbsent(key,new ArrayList<>());
           eventsByMonth.get(key).add(event);

           numberOfEventsByMonth.computeIfPresent(date.getMonth(), (k,v) -> ++v);
    }

    public void listEvents(Date date){
        String toSearch=makeKey(date);

        if(eventsByMonth.containsKey(toSearch)){
              eventsByMonth.get(toSearch).stream()
                                         .sorted(eventComparator)
                                         .forEach(System.out::println);
        }else{
            System.out.println("No events on this day!");
        }
    }

    public void listByMonth(){

        Map<Integer, Integer> tmp = events.stream().collect(Collectors.groupingBy(
                Event::getMonth,
                Collectors.summingInt(i -> 1)
        ));
        System.out.println(numberOfEventsByMonth);

        IntStream.range(0, 12).forEach( i -> {
            System.out.printf("%d : %d\n", i+1, tmp.getOrDefault(i, 0));
        });

    }
}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}
