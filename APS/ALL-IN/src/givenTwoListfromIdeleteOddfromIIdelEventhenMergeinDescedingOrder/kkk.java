package givenTwoListfromIdeleteOddfromIIdelEventhenMergeinDescedingOrder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

    public void setFirst(SLLNode<E> first) {
        this.first = first;
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

    public SLLNode<E> getFirst()
    {
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


}
public class kkk {
    private static void insertSorted(SLL<Integer> lista, int e) {
        if (lista.getFirst() == null) {
            lista.insertLast(e);
            return;
        }
        /**
         *  THIS METHOD WILL SORT ELEMENTS IN DESCENDING ORDER
         */
        // 2 3 0 1
        SLLNode<Integer> current = lista.getFirst();
        while (current != null) {
            if (current.element < e) {
                lista.insertBefore(e, current);
                return;
            }
            current = current.succ;
        }
        lista.insertLast(e);

        /**
         * THIS METHOD WILL SORT ELEMENTS IN ASCEDING ORDER
         */
//        SLLNode<Integer> current = lista.getFirst();
//        while (current != null) {
//            if (current.element > e) {
//                lista.insertBefore(e, current);
//                return;
//            }
//            current = current.succ;
//        }
//        lista.insertLast(e);
    }

        private static SLL<Integer> spojLista (SLL < Integer > firstList, SLL < Integer > secondList){
            SLL<Integer> res = null;
            SLLNode<Integer> d1 = null;
            SLLNode<Integer> d2 = null;
            SLLNode<Integer> sleden = null;

            /**
             * THE IDEA HERE IS TO FIRST TAKE THE LIST WHITH THE SMALLER FIRST ELEMENT AND THEN PUT ELEMENTS IN THAT LIST
             */
            if (firstList.getFirst().element < secondList.getFirst().element) {
                res = firstList;
                d1 = firstList.getFirst();
                d2 = secondList.getFirst();
            } else if (firstList.getFirst().element > secondList.getFirst().element) {
                res = secondList;
                d1 = secondList.getFirst();
                d2 = firstList.getFirst();
            } else {
                res = firstList;
                d1 = firstList.getFirst();
                d2 = secondList.getFirst().succ;
            }

            /**
             * ITERATIE TILL D2 != NULL , IFF D1.SUCC !=NULL (we have to iterate all elements of smaller list) THEN check if d1.succ.element > d2.element which means we need to take d2 and put right after d1,if cond is sat.
             * since we cant loose the list we need to keep track of second list so we have sleden to point to succ of d2 ,THEN make d2.succ to points to d1.succ and d1.succ to points to d2  update d1 to have d2 since d2 is in same list as d1 (like making d1=d1.succ)
             * update d2=sleden
             * IFF COND IS D1.SUCC.ELEMENT < D2 we just increment d1 since everything is OK
             * when we come to sitation like d1.succ ==null we need to make just d1.succ points to d2 and break the exe of task
             */

            while (d2 != null) {
                if (d1.succ != null) {
                    if (d1.succ.element > d2.element) {  //>
                        sleden = d2.succ;
                        d2.succ = d1.succ;
                        d1.succ = d2;

                        d1 = d2;
                        d2 = sleden;
                    } else if (d1.succ.element < d2.element) {  //<
                        d1 = d1.succ;
                    } else {   //==
                        d1 = d1.succ;
                        d2 = d2.succ;
                    }
                } else {
                    d1.succ = d2;
                    break;
                }
            }

            return res;

        }

    private static void deleteNodes(SLL<Integer> lista, int reminder) {
        SLLNode<Integer> current = lista.getFirst();
        while (current != null){
            if(current.element % 2 ==reminder){
                lista.delete(current);
            }
            current=current.succ;
        }
    }

//        private static void deleteDupli (SLL < Integer > lista) {
//            SLLNode<Integer> current = lista.getFirst();
//            while (current.succ != null) {
//                if (current.element == current.succ.element) {
//                    current.succ = current.succ.succ;
//                } else {
//                    current = current.succ;
//                }
//            }
//        }


//        private static void spojLista (SLL < Integer > lista1, SLL < Integer > lista2){
//            SLLNode<Integer> p = lista1.getFirst();
//            SLLNode<Integer> q = lista2.getFirst();
//            SLLNode<Integer> prv = null, posleden = null;
//
//            while (p != null && q != null) {
//                if (p.element < q.element) {
//                    if (prv == null) {   //prv will point to the first element of list which has the first element smaller
//                        prv = posleden = p;  //also posleden is impt to make here
//                    } else {
//                        posleden.succ = p;     //pos succ will point to the element smaller
//                        posleden = p;   //update to point to p
//                    }
//                    p = p.succ;
//                } else {
//                    if (prv == null) {
//                        prv = posleden = q;
//                    } else {
//                        posleden.succ = q;
//                        posleden = q;
//                    }
//                    q = q.succ;
//                }
//            }
//            if (p != null) {
//                posleden.succ = p;
//            }
//            if (q != null) {
//                posleden.succ = q;
//            }
//
//            lista1.setFirst(prv);
//            lista2.setFirst(null);
//
//            p = lista1.getFirst();
//
//            while (p != null && p.succ != null) {
//                if (p.element == p.succ.element) {
//                    lista1.delete(p);
//                }
//                p = p.succ;
//            }
//
//            System.out.println(lista1);
//        }



    public static void main(String[] args) {
        SLL<Integer> firstList=new SLL<>();
        SLL<Integer> secondList=new SLL<>();
        Scanner scanner=new Scanner(System.in);

        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            insertSorted(firstList, scanner.nextInt());
         //   firstList.insertLast(scanner.nextInt());
        }
        System.out.println(firstList);

//        int m = scanner.nextInt();
//        for (int i = 0; i < m; i++) {
//           insertSorted(secondList, scanner.nextInt());
//         //   secondList.insertLast(scanner.nextInt());
//        }

//        deleteNodes(firstList,1);  //neparen
//        deleteNodes(secondList,0);   //paren

//        SLL<Integer> res = spojLista(firstList, secondList);
//        System.out.println(res);


    }
}
