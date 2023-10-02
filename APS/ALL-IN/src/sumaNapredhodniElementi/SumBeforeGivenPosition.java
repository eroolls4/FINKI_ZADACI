package sumaNapredhodniElementi;

import java.util.Scanner;

/**Дадена е еднострано поврзана листа и да се модифицира листата на тој начин што ќе се почне од првиот јазел,
 * вредноста на тој јазел се зема како вредност за локација после колку јазли треба да се вметне јазел со
 * сумата на јазлите до таа локација. Откако ќе се вметне јазел се прдодолжува од наредниот и се повторува се додека не се стигне на крај од листата.

 Влез:
 14
 2 3 4 5 2 4 5 7 5 9 2 3 4 13
 Излез:
 2 3 5 4 5 2 4 15 5 7 5 9 2 28 3 4 13


 Влез:
 15
 3 3 4 5 2 4 5 7 5 9 2 3 4 13 2
 Излез:
 3 3 4 10 5 2 4 5 7 23 5 9 2 3 4 23 13 2
 */

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
            if (first == before) {
                this.insertFirst(o);
                return;
            }
            // ako first!=before
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
            if (first == node) {
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
}

public class SumBeforeGivenPosition {
//    14
//            2 3 4 5 2 4 5 7 5 9 2 3 4 13
//    Излез:
//            2 3 5 4 5 2 4 15 5 7 5 9 2 28 3 4 13


    private static void pecatiLista(SLL<Integer> list){
        SLLNode<Integer> iterator=list.getFirst();
        StringBuilder sb=new StringBuilder();
        while (iterator != null){
            sb.append(iterator.element);
            if(iterator.succ != null){
                sb.append("->");
            }
            iterator=iterator.succ;
        }
        System.out.println(sb.toString());
    }

    private static boolean satisfyCondition(SLL<Integer> list,int goal,SLLNode<Integer> startFrom){
        SLLNode<Integer> iterator = startFrom;
        int brojac = 0;
        while (iterator != null) {
            ++brojac;
            if(brojac==goal){
                break;
            }
            iterator = iterator.succ;
        }
        return brojac == goal;

    }
    private static void sumBefore(SLL<Integer> list) {

        SLLNode<Integer> iterator = list.getFirst();
        int sum = 0;
        int brojac = 0;
        SLLNode<Integer> dvizi = null;
        boolean isDone=true;
        while (iterator != null) {
            dvizi = iterator;
            sum = 0;
            int goal = iterator.element;
            if(satisfyCondition(list,goal,dvizi)) {
                while (true) {
                    sum += dvizi.element;
                    dvizi = dvizi.succ;
                    ++brojac;
                    if (brojac == goal) {
                        brojac = 0;
                        break;
                    }
                }
                list.insertBefore(sum, dvizi);
                isDone=!isDone;
            }
            if(!isDone) {
                iterator = dvizi;
                isDone=!isDone;
            }else {
                iterator =iterator.succ;
            }
        }

        pecatiLista(list);

    }

    public static void main(String[] args) {
        SLL<Integer> list = new SLL<Integer>();

        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        for (int i = 0; i < n; i++)
            list.insertLast(input.nextInt());

        sumBefore(list);
        input.close();
    }
}