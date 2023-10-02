package PDF_II_SEP2019TERMIN2;





import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.*;
import java.io.*;

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            SLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode<E> tmp = first;
            ret += tmp + "->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode<E> ins = new SLLNode<E>(o, first);
        first = ins;
    }

    public void insertAfter(E o, SLLNode<E> node) {
        if (node != null) {
            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
            node.succ = ins;
        } else {
            System.out.println("Dadenot jazol e null");
        }
    }

    public void insertBefore(E o, SLLNode<E> before) {

        if (first != null) {
            SLLNode<E> tmp = first;
            if(first==before){
                this.insertFirst(o);
                return;
            }
            //ako first!=before
            while (tmp.succ != before)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                SLLNode<E> ins = new SLLNode<E>(o, before);
                tmp.succ = ins;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
    }

    public void insertLast(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != null)
                tmp = tmp.succ;
            SLLNode<E> ins = new SLLNode<E>(o, null);
            tmp.succ = ins;
        } else {
            insertFirst(o);
        }
    }

    public E deleteFirst() {
        if (first != null) {
            SLLNode<E> tmp = first;
            first = first.succ;
            return tmp.element;
        } else {
            System.out.println("Listata e prazna");
            return null;
        }
    }

    public E delete(SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
            if(first ==node){
                return this.deleteFirst();
            }
            while (tmp.succ != node && tmp.succ.succ != null)
                tmp = tmp.succ;
            if (tmp.succ == node) {
                tmp.succ = tmp.succ.succ;
                return node.element;
            } else {
                System.out.println("Elementot ne postoi vo listata");
                return null;
            }
        } else {
            System.out.println("Listata e prazna");
            return null;
        }

    }

    public SLLNode<E> getFirst() {
        return first;
    }

    public SLLNode<E> find(E o) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.element != o && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element == o) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return first;
    }

    public Iterator<E> iterator () {
        // Return an iterator that visits all elements of this list, in left-to-right order.
        return new LRIterator<E>();
    }

    // //////////Inner class ////////////

    private class LRIterator<E> implements Iterator<E> {

        private SLLNode<E> place, curr;

        private LRIterator() {
            place = (SLLNode<E>) first;
            curr = null;
        }

        public boolean hasNext() {
            return (place != null);
        }

        public E next() {
            if (place == null)
                throw new NoSuchElementException();
            E nextElem = place.element;
            curr = place;
            place = place.succ;
            return nextElem;
        }

        public void remove() {
            //Not implemented
        }
    }

    public void mirror(){
        if (first != null) {
            //m=nextsucc, p=tmp,q=next
            SLLNode<E> tmp = first;
            SLLNode<E> newsucc = null;
            SLLNode<E> next;

            while(tmp != null){
                next = tmp.succ;
                tmp.succ = newsucc;
                newsucc = tmp;
                tmp = next;
            }
            first = newsucc;
        }

    }

    public void merge (SLL<E> in){
        if (first != null) {
            SLLNode<E> tmp = first;
            while(tmp.succ != null)
                tmp = tmp.succ;
            tmp.succ = in.getFirst();
        }
        else{
            first = in.getFirst();
        }
    }

    public SLLNode<E> getLast(){
        SLLNode<E> tmp=first;
        while (tmp.succ !=null){
            tmp=tmp.succ;
        }
        return tmp;
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

public class x {

   //1 2 3 4 5 6 7 8     -> -2 6 -2 14

    /**
     * dadena e ednostrana povrzana lista od celi broevi. treba da se najdat parovi od parni i
     * neparni broevi. parot od parni broevi treba da se sobere i sumata treba da dojde na
     * mestoto na prviot element od parot, a vtoriot treba da se izbrise. Neparnite broevi treba
     * da se odzemat i razlikata treba da se smesti kako kaj parnite broevi, odnosno prviot
     * element od parot se zamenuva so razlikata , a vtoriot element od parot se brise
     * Vlez : 1 2 3 4 5 6 7 8
     * Izlez -2 6 -2 14
     *
     */


    private static void modify(SLL<Integer> lista,int x) {
//        SLLNode<Integer> iterator = lista.getFirst();
//        SLLNode<Integer> paren = null;
//        SLLNode<Integer> neparen = null;
//        while (iterator != null) {
//            if (iterator.element % 2 == 0) {  //even
//                if (paren == null) {   //to keep track of first number of even pair then the second will entry the else part
//                    paren = iterator;
//                } else {
//                    int sum = paren.element + iterator.element;
//                    lista.insertBefore(sum, paren);
//                    lista.delete(paren);
//                    paren = null;
//                    lista.delete(iterator);
//                }
//            } else {   //odd
//                if (neparen == null) {
//                    neparen = iterator;
//                } else {
//                    int diff = neparen.element - iterator.element;
//                    lista.insertBefore(diff, paren);
//                    lista.delete(neparen);
//                    neparen = null;
//                    lista.delete(iterator);
//                }
//            }
//            iterator = iterator.succ;
//        }



        //2 3 5 8 4 7 10 9 1 6

        SLLNode<Integer> currentNode = lista.getFirst();
        SLLNode<Integer> smallerHead = null;
        SLLNode<Integer> smallerTail = null;
        SLLNode<Integer> greaterOrEqualHead = null;
        SLLNode<Integer> greaterOrEqualTail = null;

        while (currentNode != null) {
            SLLNode<Integer> nextNode = currentNode.succ;
            if (currentNode.element < x) {
                if (smallerHead == null) {
                    smallerHead = currentNode;
                    smallerTail = currentNode;
                } else {
                    smallerTail.succ = currentNode;
                    smallerTail = currentNode;
                }
            } else {
                if (greaterOrEqualHead == null) {
                    greaterOrEqualHead = currentNode;
                    greaterOrEqualTail = currentNode;
                } else {
                    greaterOrEqualTail.succ = currentNode;
                    greaterOrEqualTail = currentNode;
                }
            }
            currentNode.succ = null;
            currentNode = nextNode;
        }

        if (smallerHead != null) {
            smallerTail.succ = greaterOrEqualHead;
            lista.getFirst().element = smallerHead.element;
        } else {
            lista.getFirst().element = greaterOrEqualHead.element;
        }
    }



    public static void main(String[] args) {
        SLL<Integer> list = new SLL<Integer>();
        Scanner input = new Scanner(System.in);
        int e = input.nextInt();
        for (int i = 0; i < e; i++)
            list.insertLast(input.nextInt());

        int x= input.nextInt();

        modify(list,x);
        System.out.println(list);

    }
}
