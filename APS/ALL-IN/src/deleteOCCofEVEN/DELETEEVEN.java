package deleteOCCofEVEN;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/*
               ------NP-STYLE--------------------

public class deleteEVEN {

    public static List<Integer> deleteQtu(LinkedList<Integer> list){

       List<Integer> res= list.stream()
               .filter(element -> countOcc(list,element) % 2 == 0)
               .collect(Collectors.toList());

       return res;
    }

    private static int countOcc(LinkedList<Integer> list, Integer key) {

        int count=(int)list.stream()
                           .filter(element-> element.equals(key))
                           .count();
        return count;
    }


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();

        LinkedList<Integer> list=new LinkedList<>();

        for(int i=0;i<n;i++){
            list.addLast(scanner.nextInt());
        }
         List<Integer> x=deleteQtu(list);

        String res=x.stream().map(element->element.toString()).collect(Collectors.joining("->"));
        System.out.println(res);
    }
}


*/

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

public class DELETEEVEN {

    public static void deleteEven(SLL<Integer> lista){
          SLLNode<Integer> dvizi=lista.getFirst();
          while (dvizi != null){
              if(countOcc(lista,dvizi.element) % 2 == 0){ //if has even occ
                   int x=countOcc(lista,dvizi.element);  //how many times
                   SLLNode<Integer> brisi=lista.find(dvizi.element);  //points to element which we need to delete
                   while (x-- != 0){
                       lista.delete(brisi);
                        brisi=lista.find(dvizi.element);
                     //  x--;
                   }
              }
              dvizi=dvizi.succ;
          }
    }

    public static int countOcc(SLL<Integer> lista,int element){
        int count=0;
        SLLNode<Integer> dvizi=lista.getFirst();
        while (dvizi != null){
             if(dvizi.element.equals(element)){
                 count++;
             }
          dvizi=dvizi.succ;
        }
        return count;
    }

    public static void main(String[] args)  {
        Scanner scanner=new Scanner(System.in);
        int n=Integer.parseInt(scanner.nextLine());
        SLL<Integer> list1=new SLL<Integer>();
        String lista []=scanner.nextLine().split(" ");

        for(int i=0;i<n;i++){
            list1.insertLast(Integer.parseInt(lista[i]));
        }

        deleteEven(list1);
        SLLNode<Integer> tmp=list1.getFirst();
        while(tmp!=null){
            System.out.print(tmp.element + " ");
            tmp=tmp.succ;
        }
    }
}