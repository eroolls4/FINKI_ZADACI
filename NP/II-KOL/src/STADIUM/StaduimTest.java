package STADIUM;

import java.util.*;
import java.util.stream.*;


class SeatTakenException extends Exception{

}

class SeatNotAllowedException extends Exception{

}


class Sector implements Comparable<Sector>{
    String code;
    int capacity;

    Map<Integer,Integer> typesBySeats;
    Set<Integer> types;


    public Sector(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;

        typesBySeats=new HashMap<>();
        types=new HashSet<>();
    }

    public boolean isTaken(int seat) {
        return typesBySeats.containsKey(seat);
    }


    public int getRemainingSpace(){
        return capacity - typesBySeats.size();
    }

    public void takeSeat(int seat, int type) throws SeatNotAllowedException {
        if(type == 1){
            if(types.contains(2)){
                throw new SeatNotAllowedException();
            }
        }else  if(type == 2) {
            if (types.contains(1)) {
                throw new SeatNotAllowedException();
            }
        }

        typesBySeats.put(seat,type);
        types.add(type);
    }


    @Override
    public String toString() {
        double percentage=  (capacity - getRemainingSpace()) * 100.0 / capacity;
        return String.format("%s\t%d/%d\t%.1f%%",code,getRemainingSpace(),capacity,percentage);
    }

    public String getCode() {
        return code;
    }

    @Override
    public int compareTo(Sector other) {
        return Comparator.comparing(Sector::getRemainingSpace).reversed()
                .thenComparing(Sector::getCode)
                .compare(this,other);
    }
}

class Stadium{

    String name;
    List<Sector> sectors;

    Map<String,Sector> sectorsByName;


    public Stadium(String name) {
        this.name = name;
        sectors=new ArrayList<>();
        sectorsByName=new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            String name = sectorNames[i];
            int size = sectorSizes[i];
            Sector sector = new Sector(name, size);
            sectorsByName.put(name,sector);
            sectors.add(sector);
        }
     //   System.out.println(sectors);
    }

    void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Sector sector = sectorsByName.get(sectorName);
        if (sector.isTaken(seat)){
            throw new SeatTakenException();
        }
        else{
            sector.takeSeat(seat, type);
        }
    }

    void showSectors(){

        sectorsByName.values()
                     .stream()
                     .sorted()
                     .forEach(System.out::println);
    }
}



public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
