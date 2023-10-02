package DeleteSubListFromList;

import java.util.Scanner;

class DLLNode<E> {
    protected E element;
    protected DLLNode<E> pred, succ;

    public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
        this.element = elem;
        this.pred = pred;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return "<-" + element.toString() + "->";
    }
}

class DLL<E> {
    private DLLNode<E> first, last;

    public DLL() {
        // Construct an empty SLL
        this.first = null;
        this.last = null;
    }

    public void deleteList() {
        first = null;
        last = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            DLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    public void insertFirst(E o) {
        DLLNode<E> ins = new DLLNode<E>(o, null, first);
        if (first == null)
            last = ins;
        else
            first.pred = ins;
        first = ins;
    }
    public E delete(DLLNode<E> node) {
        if (node == first) {
            return deleteFirst();
        }
        if (node == last) {
            return deleteLast();
        }
        node.pred.succ = node.succ;
        node.succ.pred = node.pred;
        return node.element;
    }

    public void insertLast(E o) {
        if (first == null)
            insertFirst(o);
        else {
            DLLNode<E> ins = new DLLNode<E>(o, last, null);
            last.succ = ins;
            last = ins;
        }
    }

    public void insertAfter(E o, DLLNode<E> after) {
        if (after == last) {
            insertLast(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }

    public void insertBefore(E o, DLLNode<E> before) {
        if (before == first) {
            insertFirst(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
    }

    public E deleteFirst() {
        if (first != null) {
            DLLNode<E> tmp = first;
            first = first.succ;
            if (first != null) first.pred = null;
            if (first == null)
                last = null;
            return tmp.element;
        } else
            return null;
    }

    public E deleteLast() {
        if (first != null) {
            if (first.succ == null)
                return deleteFirst();
            else {
                DLLNode<E> tmp = last;
                last = last.pred;
                last.succ = null;
                return tmp.element;
            }
        }
        // else throw Exception
        return null;
    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            DLLNode<E> tmp = first;
            ret += tmp + "<->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "<->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public DLLNode<E> getFirst() {
        return first;
    }

    public DLLNode<E> getLast() {

        return last;
    }

}

public class zz {

    //6 7 6 7 9 1 5 6 7 6 7 6 7 9 3 4 6 7 6 7 9     6 7 6 7 9
    private static void deleteSubList(DLL<Integer> lista,DLL<Integer> sublista){

        DLLNode<Integer> iterator = lista.getFirst();
        DLLNode<Integer> tmpForIterator = lista.getFirst();
        DLLNode<Integer> sub = sublista.getFirst();

        while (iterator != null) {
            sub = sublista.getFirst();
            while (tmpForIterator != null && sub != null && tmpForIterator.element == sub.element) {  //try to find sublist if we found sub will point to null last cycle
                tmpForIterator = tmpForIterator.succ;
                sub = sub.succ;
            }
            if (sub == null) { //the sublist has found time to delete it now
                while (iterator != tmpForIterator) {  //tmp will point to the succ of the last element on sublist which we need to delete
                    lista.delete(iterator);
                    iterator = iterator.succ;
                }
            } else {
                if (iterator != null) {   //if we dont find the sublist update iterator IMP to move tmp to iterator
                    iterator = iterator.succ;
                    tmpForIterator = iterator;
                }
            }
        }
    }
    public static void main(String[] args) {
        DLL<Integer> lista=new DLL<>();
        DLL<Integer> sublista=new DLL<>();
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        for(int i=0;i<n;i++){
            lista.insertLast(scanner.nextInt());
        }
        int m= scanner.nextInt();

        for(int j=0;j<m;j++){
            sublista.insertLast(scanner.nextInt());
        }

        deleteSubList(lista,sublista);

    }
}
