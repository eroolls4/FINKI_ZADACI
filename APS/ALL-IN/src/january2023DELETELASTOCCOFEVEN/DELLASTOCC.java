package january2023DELETELASTOCCOFEVEN;


import java.util.*;
import java.util.stream.*;



public class DELLASTOCC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        LinkedList<Integer> lista = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            lista.addLast(scanner.nextInt());
        }

        int toDelete = scanner.nextInt();

        int count = (int) lista.stream().filter(element -> element.equals(toDelete)).count();

        if (count % 2 != 0) {
            lista.removeLastOccurrence(toDelete);
        }

        String res = lista.stream()
                .map(Object::toString)
                .collect(Collectors.joining("->"));

        System.out.println(res);
    }
}




//
//import java.util.*;
//class SLLNode<E> {
//    protected E element;
//    protected SLLNode<E> succ;
//
//    public SLLNode(E elem, SLLNode<E> succ) {
//        this.element = elem;
//        this.succ = succ;
//    }
//}
//
//class SLL<E> {
//    private SLLNode<E> first;
//
//    public SLL() {
//        // Construct an empty SLL
//        this.first = null;
//    }
//
//    public void deleteList() {
//        first = null;
//    }
//
//    public int size() {
//        int listSize = 0;
//        SLLNode<E> tmp = first;
//        while(tmp != null) {
//            listSize++;
//            tmp = tmp.succ;
//        }
//        return listSize;
//    }
//
//    @Override
//    public String toString() {
//        String ret = new String();
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            ret += tmp.element;
//            while (tmp.succ != null) {
//                tmp = tmp.succ;
//                ret += "->" + tmp.element;
//            }
//        } else
//            ret = "Prazna lista!!!";
//        return ret;
//    }
//
//    public void insertFirst(E o) {
//        SLLNode<E> ins = new SLLNode<E>(o, null);
//        ins.succ = first;
//        //SLLNode<E> ins = new SLLNode<E>(o, first);
//        first = ins;
//    }
//
//    public void insertAfter(E o, SLLNode<E> node) {
//        if (node != null) {
//            SLLNode<E> ins = new SLLNode<E>(o, node.succ);
//            node.succ = ins;
//        } else {
//            System.out.println("Dadenot jazol e null");
//        }
//    }
//    public SLLNode<E> getPredecessor(E o, SLLNode<E> node) {
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            while (tmp.succ != node)
//                tmp = tmp.succ;
//            return tmp;
//        } else {
//            System.out.println("Listata e prazna");
//            return null;
//        }
//    }
//    public void insertBefore(E o, SLLNode<E> before) {
//
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            if(first==before){
//                this.insertFirst(o);
//                return;
//            }
//            //ako first!=before
//            while (tmp.succ != before && tmp.succ!=null)
//                tmp = tmp.succ;
//            if (tmp.succ == before) {
//                tmp.succ = new SLLNode<E>(o, before);;
//            } else {
//                System.out.println("Elementot ne postoi vo listata");
//            }
//        } else {
//            System.out.println("Listata e prazna");
//        }
//    }
//
//    public void insertLast(E o) {
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            while (tmp.succ != null)
//                tmp = tmp.succ;
//            tmp.succ = new SLLNode<E>(o, null);
//        } else {
//            insertFirst(o);
//        }
//    }
//
//    public E deleteFirst() {
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            first = first.succ;
//            return tmp.element;
//        } else {
//            System.out.println("Listata e prazna");
//            return null;
//        }
//    }
//
//    public E delete(SLLNode<E> node) {
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            if(first == node) {
//                return this.deleteFirst();
//            }
//            while (tmp.succ != node && tmp.succ.succ != null)
//                tmp = tmp.succ;
//            if (tmp.succ == node) {
//                tmp.succ = tmp.succ.succ;
//                return node.element;
//            } else {
//                System.out.println("Elementot ne postoi vo listata");
//                return null;
//            }
//        } else {
//            System.out.println("Listata e prazna");
//            return null;
//        }
//
//    }
//
//    public SLLNode<E> getFirst() {
//        return first;
//    }
//
//    public SLLNode<E> find(E o) {
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            while (tmp.element != o && tmp.succ != null)
//                tmp = tmp.succ;
//            if (tmp.element.equals(o)) {
//                return tmp;
//            } else {
//                // System.out.println("Elementot ne postoi vo listata");
//            }
//        } else {
//            System.out.println("Listata e prazna");
//        }
//        return null;
//    }
//
//    public void merge (SLL<E> in){
//        if (first != null) {
//            SLLNode<E> tmp = first;
//            while(tmp.succ != null)
//                tmp = tmp.succ;
//            tmp.succ = in.getFirst();
//        }
//        else{
//            first = in.getFirst();
//        }
//    }
//
//    public void mirror() {
//        if (first != null) {
//            //m=nextsucc, p=tmp,q=next
//            SLLNode<E> tmp = first;
//            SLLNode<E> newsucc = null;
//            SLLNode<E> next;
//
//            while(tmp != null){
//                next = tmp.succ;
//                tmp.succ = newsucc;
//                newsucc = tmp;
//                tmp = next;
//            }
//            first = newsucc;
//        }
//    }
//}
//
//public class DELLASTOCC {
//
//    public static void deleteLast(SLL<Integer> lista,int key){
//          int count=0;
//          SLLNode<Integer> pamti=null;
//          SLLNode<Integer> dvizi=lista.getFirst();
//          while (dvizi != null){
//              if(dvizi.element == key){
//                  count++;
//                  pamti=dvizi;
//              }
//              dvizi=dvizi.succ;
//          }
//          if(count % 2 != 0){
//               lista.delete(pamti);
//          }
//    }
//
//    public static void main(String[] args)  {
//        Scanner scanner=new Scanner(System.in);
//        int n=Integer.parseInt(scanner.nextLine());
//        SLL<Integer> list1=new SLL<Integer>();
//        String lista []=scanner.nextLine().split(" ");
//        for(int i=0;i<n;i++){
//            list1.insertLast(Integer.parseInt(lista[i]));
//        }
//        int key= scanner.nextInt();
//        deleteLast(list1,key);
//        SLLNode<Integer> tmp=list1.getFirst();
//        while(tmp!=null){
//            System.out.print(tmp.element + " ");
//            tmp=tmp.succ;
//        }
//    }
//}
