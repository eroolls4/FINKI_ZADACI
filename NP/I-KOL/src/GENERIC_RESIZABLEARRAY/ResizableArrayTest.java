package GENERIC_RESIZABLEARRAY;

import java.security.*;
import java.util.*;


class ResizableArray<T> {
    T[] niza;
    int capacity;

    public ResizableArray() {
        niza = (T[]) new Object[1];
        capacity = 0;
    }

    public static <T> void copyAll(ResizableArray<? super T> destination, ResizableArray<? extends T> source) {
        int count = source.count();
        for (int k = 0; k < count; ++k)
            destination.addElement(source.elementAt(k));
    }

    void addElement(T element) {
        if (capacity == niza.length) {
            niza = Arrays.copyOf(niza, capacity << 1);
        }
        niza[capacity++] = element;
    }

    public boolean removeElement(T element) {
        int keyIdx = find(element);
        if (keyIdx == -1) return false;

        niza[keyIdx] = niza[--capacity];
        if (capacity<< 2 <= niza.length) niza = Arrays.copyOf(niza, capacity<< 1 > 0 ? capacity<< 1 : 1);
        return true;
    }

    private int find(T element) {
        for (int i = 0; i < capacity; i++) {
            if (niza[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T element) {
        return find(element) != -1;
    }

    public Object[] toArray() {
        return Arrays.copyOf(niza, capacity);
    }

    private T elementAt(int k) {
        return niza[k];
    }

    public boolean isEmpty() {
        return capacity == 0;
    }


    public int count() {
        return capacity;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(niza, capacity)) + " " + niza.length + " " + capacity;
    }
}

class IntegerArray extends ResizableArray<Integer> {

    public IntegerArray() {
        super();
    }

    public double sum() {
        double sum = 0;
        Object[] a = toArray();
        for (int i = 0; i < a.length; i++) {
            sum += (Integer) a[i];
        }
        return sum;
    }

    public double mean() {
        return sum() / capacity;
    }

    public int countNonZero() {
        int count = 0;
        Object[] a = toArray();
        for (int i = 0; i < a.length; i++) {
            if ((Integer) a[i] > 0) {
                count++;
            }
        }
        return count;
    }

    public IntegerArray distinct() {
        Object[] a = toArray();
        Arrays.sort(a);

        IntegerArray res = new IntegerArray();

        for (int i = 0; i < a.length; i++) {
            while (i < a.length && a[i] == a[i + 1]) {
                res.addElement((Integer) a[i++]);
            }
        }
        return res;
    }

    public IntegerArray increment(int x) {
        IntegerArray res = new IntegerArray();

        Object[] a = toArray();

        for (int i = 0; i < a.length; i++) {
            res.addElement((Integer) a[i] + x);
        }
        return res;
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
