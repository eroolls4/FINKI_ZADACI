package Kompanija;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.DateFormat;
import java.io.*;
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
//        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
//        int b = hash(key);
//        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
//            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
//                // Make newEntry replace the existing entry ...
//                curr.element = newEntry;
//                return;
//            }
//        }
//        // Insert newEntry at the front of the 1WLL in bucket b ...
//        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);

        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (val.equals(((MapEntry<K, E>) curr.element).value)) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        if (buckets[b] == null)
            buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
        else {
            SLLNode<MapEntry<K, E>> dvizi = buckets[b];
            while (dvizi.succ != null) {
                dvizi = dvizi.succ;
            }
            dvizi.succ = new SLLNode<MapEntry<K, E>>(newEntry, null);
        }

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


public class hyhy {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        CBHT<Integer,Integer> mapa=new CBHT<>(9);

        mapa.insert(10,1);
        mapa.insert(35,2);
        mapa.insert(18,3);
        mapa.insert(2,4);
        mapa.insert(19,5);
        mapa.insert(26,6);
        mapa.insert(9,7);
        mapa.insert(28,8);
        System.out.println(mapa);

//        CBHT<String, Integer> mapa = new CBHT<>(2 * n);
//        for (int i = 0; i < n; i++) {
//            String line = bf.readLine();
//            String[] parts = line.split(" ");
//            String employe = parts[0];
//            String manager = parts[1];
//
//            SLLNode<MapEntry<String, Integer>> curr = mapa.search(manager);
//            if (curr == null) {
//                mapa.insert(manager, 1);
//            } else {
//                int prev = curr.element.value;
//                mapa.insert(manager, prev + 1);
//            }
//        }


    }
}
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//class CompanyPair {
//    String manager;
//    String employee;
//
//    CompanyPair(String input) {
//        String [] parts = input.split("\\s+");
//        this.manager = parts[0];
//        this.employee = parts[1];
//    }
//
//    String getManager() {
//        return manager;
//    }
//
//    String getEmployee() {
//        return employee;
//    }
//}
//
//public class Company {
//
//
//
//    public static void main(String[] args) throws IOException {
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        Map<String, List<String>> employeeMap = new HashMap<>();
//
////        6
////        JohnSmith AliceJohnson
////        EmilyDavis AliceJohnson
////        MichaelJohnson AliceJohnson
////        SarahBrown MichaelJohnson
////        DanielLee MichaelJohnson
////        EmmaWilson EmilyDavis
//
//        int N=Integer.parseInt(reader.readLine());
//
//
//        reader.lines()
//                .map(CompanyPair::new)
//                .limit(N)
//                .forEach(pair -> {
//                    employeeMap.computeIfAbsent(pair.getEmployee(), (k) -> new ArrayList<>())
//                            .add(pair.getManager());
//
//                    employeeMap.computeIfAbsent(pair.getManager(), (k) -> new ArrayList<>());
//                });
//
//
//
//
//        employeeMap.entrySet()
//                .stream()
//                .filter(entry -> entry.getValue().size() > 0)
//                .map(entry -> entry.getKey() + ": " + entry.getValue().size())
//                .forEach(System.out::println);
//    }
//
//}
