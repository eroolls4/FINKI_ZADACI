package mergeListAndDeleteDuplli;

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

    public SLLNode<E> getLast(){
        SLLNode<E> tmp=first;
        while (tmp.succ != null){
            tmp=tmp.succ;
        }
        return tmp;
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

public class listProblem_10_lab2 {
    public static SLL<Integer> mergeLists(SLL<Integer> firstList, SLL<Integer> secondList) {

        SLLNode<Integer> firstCurrent = firstList.getFirst();
        SLLNode<Integer> secondCurrent = secondList.getFirst();

        SLL<Integer> res = new SLL<>();

        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.element < secondCurrent.element) {
                res.insertLast(firstCurrent.element);
                firstCurrent = firstCurrent.succ;
            } else if (firstCurrent.element > secondCurrent.element) {
                res.insertLast(secondCurrent.element);
                secondCurrent = secondCurrent.succ;
            }
        }

        while (firstCurrent != null) {
            res.insertLast(firstCurrent.element);
            firstCurrent = firstCurrent.succ;
        }
        while (secondCurrent != null) {
            res.insertLast(secondCurrent.element);
            secondCurrent = secondCurrent.succ;
        }

        return res;
    }

    public static SLL<Integer> removeDuplicates (SLL<Integer> list) {

        SLLNode<Integer> current=list.getFirst();
        SLLNode<Integer> tmp=null;

        //1 2 3 1 4 2 1 1 5 6

        while (current != null) {
            tmp = current.succ;
            while (tmp != null) {
                if (Objects.equals(current.element, tmp.element)) {
                    list.delete(tmp);
                }
                tmp = tmp.succ;
            }
            current = current.succ;
        }
        return list;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // input for first list
        int numberOfElementsFirstList = Integer.parseInt(br.readLine());
        SLL<Integer> firstList = new SLL<>();
        String[] elementsFirstList = br.readLine().split(" ");
        for (int i = 0; i < numberOfElementsFirstList; i++)
            firstList.insertLast(Integer.parseInt(elementsFirstList[i]));

        // input for second list

        int numberOfElementsSecondList = Integer.parseInt(br.readLine());
        SLL<Integer> secondList = new SLL<>();
        String[] elementsSecondList = br.readLine().split(" ");
        for (int i = 0; i < numberOfElementsSecondList; i++)
            secondList.insertLast(Integer.parseInt(elementsSecondList[i]));

        SLL<Integer> integerSLL = removeDuplicates(mergeLists(firstList, secondList));

        System.out.println(integerSLL);

    }

}