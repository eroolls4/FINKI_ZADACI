package AIRPORT;

import java.util.*;
//
//class Flight implements Comparable<Flight>{
//    String from;
//    String to;
//    int time;
//    int duration;
//
//    public Flight(String from, String to, int time, int duration) {
//        this.from = from;
//        this.to = to;
//        this.time = time;
//        this.duration = duration;
//    }
//
//    public String getTo() {
//        return to;
//    }
//
//    public int getTime() {
//        return time;
//    }
//
//    @Override
//    public int compareTo(Flight o) {
//        return Comparator
//                .comparing(Flight::getTo)
//                .thenComparing(Flight::getTime)
//                .thenComparing(Flight::durationConverter)
//                .compare(this, o);
//    }
//
//    public String toString2(int i) {
//        return String.format("%d. %s-%s %s-%s %s\n", i, from, to, timeConvert(time), timeConvert(duration + time), durationConverter());
//    }
//
//    public String durationConverter() {
//        StringBuilder sb = new StringBuilder();
//        int hours = duration / 60;
//        int minutes = duration - (duration / 60 ) * 60;
//
//        String h = String.valueOf(hours);
//        String m = String.valueOf(minutes);
//        int timeHours = (time + duration) / 60;
//
//        if (timeHours > 23){
//            sb.append("+1d ");
//        }
//        return sb.append(String.format("%sh%sm", h, minutes < 10 ? "0" + m : minutes)).toString();
//
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s-%s %s-%s %s\n", from, to, timeConvert(time), timeConvert(duration + time), durationConverter());
//
//    }
//    public String timeConvert(int time){
//        int hours = time / 60;
//        int minutes = time - (time / 60 ) * 60;
//        if (hours > 23){
//            hours -=24;
//        }
//
//        String h = String.valueOf(hours);
//        String m = String.valueOf(minutes);
//
//        return String.format("%s:%s", hours < 10 ? "0" + h : h, minutes < 10 ? "0" + m : minutes);
//    }
//}
//class Airport{
//    String name;
//    String country;
//    String code;
//    int passengers;
//    Set<Flight> flights;
//
//    public Airport(String name, String country, String code, int passengers) {
//        this.name = name;
//        this.country = country;
//        this.code = code;
//        this.passengers = passengers;
//        this.flights = new TreeSet<>();
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s (%s)\n%s\n%d\n", name, code, country, passengers);
//    }
//}
//class Airports{
//    Map<String, Airport> airports;
//
//    public Airports() {
//        this.airports = new HashMap<>();
//    }
//    public void addAirport(String name, String country, String code, int passengers){
//        airports.put(code, new Airport(name, country, code, passengers));
//    }
//    public void addFlights(String from, String to, int time, int duration){
//        Flight flight = new Flight(from, to, time, duration);
//        airports.get(from).flights.add(flight);
//        airports.get(to).flights.add(flight);
//    }
//    public void showFlightsFromAirport(String code){
//        Airport tmp = airports.get(code);
//        System.out.print(tmp);
//        int i = 1;
//        for(Flight flight : tmp.flights){
//            if (Objects.equals(flight.from, code)){
//                System.out.print(flight.toString2(i));
//                i++;
//            }
//        }
//    }
//    public void showDirectFlightsFromTo(String from, String to){
//        Airport tmp = airports.get(from);
//        int i = 0;
//        for(Flight flight : tmp.flights){
//            if (Objects.equals(flight.to, to)){
//                System.out.print(flight);
//                i++;
//            }
//        }
//        if (i == 0){
//            System.out.printf("No flights from %s to %s\n", from, to);
//        }
//    }
//    public void showDirectFlightsTo(String to){
//        Airport tmp = airports.get(to);
//        for(Flight flight : tmp.flights){
//            if (Objects.equals(flight.to, to)){
//                System.out.print(flight);
//            }
//        }
//    }
//}
//public class AirportsTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Airports airports = new Airports();
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        String[] codes = new String[n];
//        for (int i = 0; i < n; ++i) {
//            String al = scanner.nextLine();
//            String[] parts = al.split(";");
//            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
//            codes[i] = parts[2];
//        }
//        int nn = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < nn; ++i) {
//            String fl = scanner.nextLine();
//            String[] parts = fl.split(";");
//            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
//        }
//        int f = scanner.nextInt();
//        int t = scanner.nextInt();
//        String from = codes[f];
//        String to = codes[t];
//        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
//        airports.showFlightsFromAirport(from);
//        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
//        airports.showDirectFlightsFromTo(from, to);
//        t += 5;
//        t = t % n;
//        to = codes[t];
//        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
//        airports.showDirectFlightsTo(to);
//    }
//}



import java.util.*;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

class Airports {

    Map<String, Airport> airports;

    public Airports() {
        airports = new TreeMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        airports.put(code, new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration) {
        Airport airport = airports.get(from);
        airport.addFlight(from, to, time, duration);
    }

    public void showFlightsFromAirport(String code) {
        Airport airport = airports.get(code);
        System.out.println(airport);
        int i = 1;
        for (String toCode : airport.flights.keySet()) {
            Set<Flight> flights = airport.flights.get(toCode);
            for (Flight flight : flights) {
                System.out.printf("%d. %s\n", i++, flight);
            }
        }
    }

    public void showDirectFlightsFromTo(String from, String to) {
        Airport fromAirport = airports.get(from);
        Set<Flight> flights = fromAirport.flights.get(to);
        if (flights != null) {
            for (Flight f : flights) {
                System.out.println(f);
            }
        } else {
            System.out.printf("No flights from %s to %s\n", from, to);
        }
    }

    public void showDirectFlightsTo(String to) {
        Set<Flight> flights = new TreeSet<>();
        for (Airport airport : airports.values()) {
            Set<Flight> flightsTo = airport.flights.get(to);
            if (flightsTo != null) {
                flights.addAll(flightsTo);
            }
        }
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }
}

class Airport {
    String name;
    String country;
    String code;
    int passengers;
    Map<String, Set<Flight>> flights;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        flights = new TreeMap<>();
    }

    public void addFlight(String from, String to, int time, int duration) {
        Set<Flight> flightsSet = flights.get(to);
        if (flightsSet == null) {
            flightsSet = new TreeSet<>();
            flights.put(to, flightsSet);
        }
        flightsSet.add(new Flight(from, to, time, duration));
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d", name, code, country, passengers);
    }
}

class Flight implements Comparable<Flight> {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    @Override
    public int compareTo(Flight o) {
        int x = Integer.compare(this.time, o.time);
        if (x == 0) {
            return this.from.compareTo(o.from);
        }
        return x;
    }

    @Override
    public String toString() {
        int end = time + duration;
        int plus = end / (24 * 60);
        end %= (24 * 60);
        return String.format("%s-%s %02d:%02d-%02d:%02d%s %dh%02dm", from, to, time / 60, time % 60,
                end / 60, end % 60, plus > 0 ? " +1d" : "", duration / 60, duration % 60);
    }
}