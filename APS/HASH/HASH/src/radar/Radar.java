package radar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.sort;

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }
}



class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}

public class Radar {
//    5
//    SK1234AA ANITA ANGELOVVSKA
//    OH1212BE ALEKSANDAR ANTOV
//    ST098900 OGNEN SPIROVSKI
//    ST0000AB SARA SPASOVKSKA
//    SK8888KD DINO ACKOV
//    50
//    SK8888KD 48 14:00:00 ST0000AB 55 12:00:02 ST098900 60 08:10:00 SK1234AA 65 20:00:10 OH1212BE 50 22:00:21
//
//            Ognen Spirovski
//            Sara Spasovksa
//             Anita Angelovska
    public static void main(String[] args) throws IOException {
        //write your code here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        CBHT<String, Driver> tabla = new CBHT<String, Driver>(getSizeHash(n));
        for(int i= 0; i<n; i++)
        {
            String[] parts = br.readLine().split("\\s+");
            tabla.insert(parts[0], new Driver(parts[1], parts[2]));
        }
        int speedLimit = Integer.parseInt(br.readLine());
        String izvestaj = br.readLine();
        String[] izvestajParts = izvestaj.split("\\s+");
        ArrayList<Driver> kaznatiDrivers = new ArrayList<Driver>();
        for(int i = 0; i<izvestajParts.length; i+=3)
        {
            String tablica = izvestajParts[i];
            int speed = Integer.parseInt(izvestajParts[i+1]);
            int seconds = getSeconds(izvestajParts[i+2]);


            SLLNode<MapEntry<String, Driver>> currentDriver = tabla.search(tablica);
            Driver tempDriver = new Driver( currentDriver.element.value.getName(),  currentDriver.element.value.getSurname());
            tempDriver.setSeconds(seconds);
            if(speed > speedLimit)
                kaznatiDrivers.add(tempDriver);
        }
        Collections.sort(kaznatiDrivers);

        for(int i = 0; i<kaznatiDrivers.size(); i++)
        {
            System.out.println(kaznatiDrivers.get(i).toString());
        }
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++)
        {
            if(n%i == 0)
                return false;
        }
        return true;
    }

    public static int getSizeHash(int n)
    {
        int m = (int) (n/0.6);
        while(!isPrime(m))
        {
            m++;
        }
        return m;
    }
    public static int getSeconds(String time)
    {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0])*3600 + Integer.parseInt(parts[1])*60 + Integer.parseInt(parts[2]);

    }
}

class Driver implements  Comparable<Driver>{
    private String name;
    private String surname;
    //    private Date date;
    private int seconds;
    public Driver(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }
    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public int compareTo(Driver o) {
//        return this.date.compareTo(o.date);
        if(this.seconds < o.seconds)
            return -1;
        else if(this.seconds == o.seconds)
            return 0;
        return 1;
    }
}
//class Driver {
//
//    String rego;
//    int speed;
//    String time;
//
//    public Driver(String rego, int speed, String time) {
//        this.rego = rego;
//        this.speed = speed;
//        this.time = time;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public String getRego() {
//        return rego;
//    }
//
//    public void setRego(String rego) {
//        this.rego = rego;
//    }
//
//    public int getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    @Override
//    public String toString() {
//        return rego +  " " + speed + " " + time;
//    }
//}
//
//public class zadaca_kol {
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int golemina = Integer.parseInt(br.readLine());
//        CBHT<String, String> vozaci = new CBHT<>(golemina*2);
//        for(int i = 0; i < golemina; i++) {
//            String line = br.readLine();
//            String [] pomniza = line.split("\\s+");
//            String rego = pomniza[0];
//            String name = pomniza[1] + " " + pomniza[2];
//            vozaci.insert(rego, name);
//        }
//        int speedLimit = Integer.parseInt(br.readLine());
//
//        String line = br.readLine();
//        List<Driver> driverList = new ArrayList<>();
//        String [] pomNiza = line.split("\\s+");
//        for(int i = 0; i < pomNiza.length; i+=3) {
//            driverList.add(new Driver(pomNiza[i], Integer.parseInt(pomNiza[i+1]), pomNiza[i+2]));
//        }
//        driverList.stream()
//                   .filter(i -> i.speed > speedLimit)
//                   .sorted(Comparator.comparing(Driver::getTime))
//                   .forEach(i -> {
//            SLLNode<MapEntry<String, String>> pokazuvac = vozaci.search(i.rego);
//            System.out.println(pokazuvac.element.value);
//        });
//    }
//}