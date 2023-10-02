package swapFirstAndLastSLL;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class vtoraZadacha {

    public static void swapHere(SLL<Integer> lista){
        // 1->2->3->4->5->6->7->8->null

        SLLNode<Integer> secondLast =lista.getFirst();
        while (secondLast.succ.succ != null) {
            secondLast = secondLast.succ;
        }
        SLLNode<Integer> last=secondLast.succ;
        SLLNode<Integer> first=lista.getFirst();

        last.succ=first.succ;
        secondLast.succ=first;
        first.succ=null;
        lista.setFirst(last);


        System.out.println(lista);
    }
    public static void main(String[] args) {
        SLL<Integer> integerSLL = new SLL<Integer>();

        integerSLL.insertLast(10);
        integerSLL.insertLast(9);
        integerSLL.insertLast(8);
        integerSLL.insertLast(7);
        integerSLL.insertLast(6);
        integerSLL.insertLast(5);
        integerSLL.insertLast(4);
        integerSLL.insertLast(3);
        integerSLL.insertLast(2);
        integerSLL.insertLast(1);

        int N = 5;

        SLLNode<Integer> first = integerSLL.getFirst();

//        while(first.element >= N)
//            first=first.succ;
//        integerSLL.insertFirst(first.element);
//        integerSLL.delete(first);
//
//        first = integerSLL.getFirst();
//        SLLNode<Integer> pomNode = first.succ;
//
//        while(pomNode!=null) {
//            if(pomNode.element < N) {
//                integerSLL.insertAfter(pomNode.element,first);
//                SLLNode<Integer> temp = pomNode.succ;
//                integerSLL.delete(pomNode);
//                pomNode = temp;
//                first=first.succ;
//                continue;
//            }
//            pomNode = pomNode.succ;
//        }

     //  integerSLL.swapFirstAndLast();
         swapHere(integerSLL);
        System.out.println(integerSLL);

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

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void setFirst(SLLNode<E> first) {
        this.first = first;
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
            ret += tmp + " ";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + " ";
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

    public void swapFirstAndLast () {
        // 1->2->3->4->5->6->7->8->null

        SLLNode<E> secondLast = first;
        while (secondLast.succ.succ != null) {
            secondLast = secondLast.succ;
        }
        SLLNode<E> last=secondLast.succ;

        last.succ=first.succ;
        secondLast.succ=first;
        first.succ=null;
        first=last;
    }
}
