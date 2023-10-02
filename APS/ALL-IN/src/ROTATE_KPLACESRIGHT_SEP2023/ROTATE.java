package ROTATE_KPLACESRIGHT_SEP2023;

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
    public DLLNode<E> first;
    public DLLNode<E> last;

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

public class ROTATE {

    public static void rotateLeftK(DLL<Integer> lista,int k){

        //1 2 3 4 5 k=2    1 2 3 4 5 6 7 8 9 10 k=4
        //3 4 5 1 2        5 6 7 8 9 10 1 2 3 4
        while (k-- != 0){
            DLLNode<Integer> curr=lista.getFirst();
            lista.insertLast(curr.element);
            lista.delete(curr);
           }
    }

    private static void  rotateB(DLL<Integer> lista,int k){


        //1 2 3 4 5 k=2    1 2 3 4 5 6 7 8 9 10 k=4
        //4 5 1 2 3        7 8 9 10 1 2 3 4 5 6
//        while (k-- != 0) {
//            DLLNode<Integer> lastNode = lista.getLast();
//            lista.insertFirst(lastNode.element);
//            lista.deleteLast();
//        }



        //1 2 3 4 5   k=2           1 2 3 4 5 6 7 8 9 10   k=4
        //5 4 1 2 3                 10 9 8 7 1 2 3 4 5 6


        DLLNode<Integer> first=lista.getFirst();
        while (k-- > 0){
            DLLNode<Integer> last=lista.getLast();
            lista.insertBefore(last.element,first);
            lista.deleteLast();
        }
//        int length = lista.length();
//        if (length == 0 || k % length == 0) {
//            // No rotation needed or a multiple of the list length, no change
//            System.out.println(lista);
//            return;
//        }
//
//        // Calculate the effective rotation value
//        int effectiveRotation = k % length;
//
//        // Find the new head and tail of the rotated list
//        DLLNode<Integer> currentHead = lista.getFirst();
//        for (int i = 0; i < effectiveRotation; i++) {
//            currentHead = currentHead.succ;
//        }
//        DLLNode<Integer> newHead = currentHead;
//        DLLNode<Integer> newTail = currentHead.pred;
//
//        // Update the links to create the rotated list
//        lista.getFirst().pred = lista.getLast();
//        lista.getLast().succ = lista.getFirst();
//        newTail.succ = null;
//        newHead.pred = null;
//        lista.first = newHead;
//        lista.last = newTail;


        System.out.println(lista);
    }
    public static void main(String[] args) {
        DLL<Integer> lista=new DLL<>();

        Scanner scanner=new Scanner(System.in);
        int n= scanner.nextInt();
        for (int i = 0; i < n; i++)
            lista.insertLast(scanner.nextInt());


        int k= scanner.nextInt();

        rotateB(lista,k);
    }
}
