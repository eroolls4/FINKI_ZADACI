package kumanovskiDialekt;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        // Napishete ja vie HASH FUNKCIJATA
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
public class kumanovskiDialekt {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        //ja krerirame hash
        CBHT<String,String> mapDialekttoStd= new CBHT<String, String>(N);

        //nega okolku
        String pairs[]=new String[N];
        for(int i=0;i<N;i++){
            pairs[i]=br.readLine();
            String [] parts=pairs[i].split("\\s+");
            mapDialekttoStd.insert(parts[0],parts[1]);
            //tuka ja popolnime mapa

            //od kumanovski to literatura
        }

        String tekst=br.readLine();


        String [] words=tekst.split(" ");  // ja podelime tesktot

        String toSearch,value;
        char character='$';
        StringBuilder res=new StringBuilder();
        for( String word : words){
            if((word.charAt(word.length()-1) == '.') ||
                    (word.charAt(word.length()-1) == '?') ||
                    (word.charAt(word.length()-1) == '!') ||
                    (word.charAt(word.length()-1) == ',')
                                                            ) {

                character=word.charAt(word.length()-1);
                word=word.substring(0,word.length()-1);
            }

            toSearch=word.toLowerCase();
            SLLNode<MapEntry<String,String>> node=mapDialekttoStd.search(toSearch);
            if(node != null){
                value=node.element.value;

                if(Character.isUpperCase(word.charAt(0))){  //ako u kumanovski e uppercase
                      res.append(Character.toUpperCase(value.charAt(0))).append(value.substring(1));  //put also in uppercase
                }else{
                    res.append(value);
                }

            }else{
                res.append(word);
            }

            //at the end just check character
            if(character != '$'){
                res.append(character);
            }else{
                res.append("");
            }
        }

        System.out.println(res);

    }
}
