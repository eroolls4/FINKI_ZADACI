package podeliSporedProsekot;

/**Подели според просек
 Дадена е еднострано поврзана листа чии што јазли содржат по еден природен број. Листата треба да се подели на две резултантни листи,
 т.ш. во првата листа треба да се сместат сите јазли кои содржат броеви помали или еднакви на просекот
 на листата (просек на листа претставува математички просек од сите природни броеви кои се јавуваат во листата),
 а во втората сите јазли кои содржат броеви поголеми од просекот на листата. Јазлите во резултантните листи се додаваат според редоследот по кој се појавуваат
 во дадената листа.

 Во првиот ред од влезот е даден бројот на јазли во листата, а во вториот ред се дадени броевите од кои се составени јазлите по редослед во листата.
 Во првиот ред од излезот треба да се испечатат јазлите по редослед од првата резултантна листа (броеви помали или еднакви на просекот на листата),
 во вториот ред од втората (броеви поголеми од просекот на листата) .

 Име на класа (за Java): PodeliSporedProsek

 Забелешка: При реализација на задачите МОРА да се користат дадените структури, а не да користат помошни структури како низи или сл.
 Sample input
 5
 4 2 1 5 3

 2 1 3
 4 5

 Sample input
 6
 6 7 9 8 10 10
 Sample output
 6 7 8
 9 10 10
 */


//CODE



import java.util.Scanner;

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
            if (first == before) {
                this.insertFirst(o);
                return;
            }
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

public class PodeliSporedProsek {
    private static void PodeliSporedProsekG1(SLL<Integer> list, int n) {

        SLLNode<Integer> current = list.getFirst();
        SLL<Integer> res1 = new SLL<>();
        SLL<Integer> res2 = new SLL<>();

        int sum = 0;

        while (current != null) {
            sum += current.element;
            current = current.succ;
        }

        int avg = sum / n;
        current = list.getFirst();

        while (current != null) {
            if (current.element <= avg) {
                res1.insertLast(current.element);
            } else {
                res2.insertLast(current.element);
            }
            current = current.succ;
        }
        System.out.println(res1);
        System.out.println(res2);



    }

    public static void main(String[] args) {
        SLL<Integer> list = new SLL<Integer>();

        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        for (int i = 0; i < n; i++)
            list.insertLast(input.nextInt());

        PodeliSporedProsekG1(list, n);
        input.close();
    }
}