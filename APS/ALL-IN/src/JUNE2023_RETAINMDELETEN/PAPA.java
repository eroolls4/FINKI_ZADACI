package JUNE2023_RETAINMDELETEN;

import deleteOCCofEVEN.*;


import java.util.*;

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }
}

class SLL<E> {
    private SLLNode<E> first;

    public SLL() {
        // Construct an empty SLL
        this.first = null;
    }

    public void deleteList() {
        first = null;
    }

    public int size() {
        int listSize = 0;
        SLLNode<E> tmp = first;
        while(tmp != null) {
            listSize++;
            tmp = tmp.succ;
        }
        return listSize;
    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            SLLNode<E> tmp = first;
            ret += tmp.element;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += "->" + tmp.element;
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public void insertFirst(E o) {
        SLLNode<E> ins = new SLLNode<E>(o, null);
        ins.succ = first;
        //SLLNode<E> ins = new SLLNode<E>(o, first);
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
    public SLLNode<E> getPredecessor(E o, SLLNode<E> node) {
        if (first != null) {
            SLLNode<E> tmp = first;
            while (tmp.succ != node)
                tmp = tmp.succ;
            return tmp;
        } else {
            System.out.println("Listata e prazna");
            return null;
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
            while (tmp.succ != before && tmp.succ!=null)
                tmp = tmp.succ;
            if (tmp.succ == before) {
                tmp.succ = new SLLNode<E>(o, before);;
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
            tmp.succ = new SLLNode<E>(o, null);
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
            if(first == node) {
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
            if (tmp.element.equals(o)) {
                return tmp;
            } else {
                // System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return null;
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

    public void mirror() {
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
}

public class PAPA {

    //
    //6
    //2 4 6 8 10 12     ==>    2->4->6->8->12
    //4 1

    private static void tuka(int m,int n,SLL<Integer> lista){
//        SLLNode<Integer> dvizi = lista.getFirst();
//        SLLNode<Integer> tmp = null;
//
//        while (dvizi != null) {
//            for (int retain = 1; retain < m && dvizi != null; retain++) {
//                dvizi = dvizi.succ;
//            }
//            tmp = dvizi.succ;
//            for (int delete = 1; delete <= n && dvizi != null; delete++) {
//               tmp=tmp.succ;
//            }
//            if (dvizi == null || tmp==null) {
//                return;
//            }
//
//            dvizi.succ = tmp;
//            dvizi=tmp;
//        }
        SLLNode<Integer> current = lista.getFirst();
        SLLNode<Integer> prev = null;

        while (current != null) {

            for (int i = 0; i < m && current != null; i++) {
                prev = current;
                current = current.succ;
            }

            // Delete N nodes
            for (int i = 0; i < n && current != null; i++) {
                current = current.succ;
            }

            // Update links to skip deleted nodes
            if (prev != null) {
                prev.succ = current;
            }
        }

    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int k=Integer.parseInt(scanner.nextLine());
        SLL<Integer> list1=new SLL<Integer>();
        String lista []=scanner.nextLine().split(" ");

        for(int i=0;i<k;i++){
            list1.insertLast(Integer.parseInt(lista[i]));
        }
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        tuka(m,n,list1);
        System.out.println(list1.toString());


    }
}
