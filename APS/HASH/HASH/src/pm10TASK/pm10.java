package pm10TASK;


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


public class pm10 {

    static class Merenje{
        double sumaPm;
        int brojac;

        public Merenje(double sumaPm, int brojac) {
            this.sumaPm = sumaPm;
            this.brojac = brojac;
        }

        @Override
        public String toString() {
            return String.format("< %.2f - %d >",sumaPm,brojac);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        CBHT<String, Merenje> table = new CBHT<String, Merenje>(2 * n);
        for (int i = 0; i < n; i++) {
            String line[] = bf.readLine().split(" ");
            String opstina = line[0];
            double zadaduvanje = Double.parseDouble(line[1]);

            SLLNode<MapEntry<String, Merenje>> curr = table.search(opstina);


         //   System.out.println(curr);
            if (curr != null) {
                zadaduvanje = zadaduvanje + curr.element.value.sumaPm;
                int brojac = curr.element.value.brojac + 1;
                table.insert(opstina, new Merenje(zadaduvanje, brojac));
            } else {
                table.insert(opstina, new Merenje(zadaduvanje, 1));
            }
        }
        String baranaOpstina = bf.readLine();
        SLLNode<MapEntry<String, Merenje>> curr = table.search(baranaOpstina);
        if (curr != null) {
            System.out.format("%.2f", curr.element.value.sumaPm / curr.element.value.brojac);
        } else {
            System.out.println("NP");
        }
    }
}







