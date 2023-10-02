package deleteMidleNtimes;

// You are give a singly linked list from which you have to delete the middle node N times.
// If the list has an even length pick you have to delete the smallest of the two middle nodes, if they have the same value delete the first one (left).
// Input:   first line  - number of elements,
//          second line - elements
//          third line  - N (number of times to delete the middle element)
//
// Example 1:
//  input:
//      6
//      1 2 3 4 5 6
//      3
//  output:
//      1 5 6
//
// Example 2:
//  input:
//      7
//      1 3 5 7 9 11 13
//      5
//  output:
//      1 13

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
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


}
public class firstProblem_2018_exam_list {
    public static void removeMiddleElement(SLL<Integer> list) {
           int middlePoss= list.length()/2;

           SLLNode<Integer> current=list.getFirst();

           int brojac=1;

           while (brojac != middlePoss){
               brojac++;
               current=current.succ;
           }

           //If the list has an even length pick you have to delete the smallest of the two middle nodes, if they have the same value delete the first one (left).
           //even->current before the first middle element
           //odd->current before the middle element


        //7
        //1 3 5 7 9 11 13
        //5

        if (list.length() % 2 == 0) { //even
            SLLNode<Integer> secondMiddle=current.succ;
            if(current.element.equals(secondMiddle.element) || current.element < secondMiddle.element){
                list.delete(current);
            }else{
                list.delete(secondMiddle);
            }
        }else{ //odd
            list.delete(current.succ);
        }


    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfElements = Integer.parseInt(br.readLine());

        SLL<Integer> integerSLL = new SLL<>();

        String[] elements = br.readLine().split(" ");

        for (int i = 0; i < numberOfElements; i++)
            integerSLL.insertLast(Integer.parseInt(elements[i]));

        int N = Integer.parseInt(br.readLine());

        while (N-- >= 1)
            removeMiddleElement(integerSLL);

        System.out.println(integerSLL);

    }
}