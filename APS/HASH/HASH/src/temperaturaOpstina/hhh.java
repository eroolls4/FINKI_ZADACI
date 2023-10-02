package temperaturaOpstina;


import java.io.*;

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
        int h = (29*(29*(29*0 + ((String) key).charAt(0)) + ((String) key).charAt(1)) + ((String) key).charAt(2)) % 102780;
        return  h % buckets.length;
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
        // Insert newEntry at the front of the SLL in bucket b ...
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

/*
   4
   Skopje 13:00 15:30 25.6
   Prilep 12:00 18:00 23.0
   Prilep 18:00 20:30 18.2
   Bitola 16:00 18:30 22.3

                            ----> Prilep 12:00-18:00 23.0
 */

class Temperatura{
    String timeFrom;
    String timeTo;
    float temp;

    public Temperatura(String timeFrom, String timeTo, float temp) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.temp = temp;
    }

    @Override
    public String toString() {
       return String.format("%s-%s %.2f",timeFrom,timeTo,temp);
    }
}


public class hhh {
    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        CBHT<String, Temperatura> table = new CBHT<>(2 * n);
        Temperatura old;

        for (int i = 0; i < n; i++) {
            String line[] = bf.readLine().split(" ");
            String city = line[0];
            String timeFrom = line[1];
            String timeTo = line[2];
            float temp = Float.parseFloat(line[3]);

            SLLNode<MapEntry<String, Temperatura>> curr = table.search(city);
            Temperatura newOne = new Temperatura(timeFrom, timeTo, temp);

            if (curr == null) {    //if its for first time
                table.insert(city, newOne);
            } else {  //since the task is to print greater element we put only greater
                old = curr.element.value;
                if (temp > old.temp) {
                    table.insert(city, newOne);
                }
            }
        }
        String searchCity = bf.readLine();
        SLLNode<MapEntry<String, Temperatura>> curr = table.search(searchCity);
        if (curr == null) {
            System.out.println("Nema takov grad");
        } else {
            old = curr.element.value;
            System.out.println(searchCity + " " + old);
        }
    }
}
