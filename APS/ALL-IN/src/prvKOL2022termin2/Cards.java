package prvKOL2022termin2;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cards
{
    public static void tarotCards(SLL<Card> firstPart,SLL<Card> secondPart) {
        SLLNode<Card> first=firstPart.getFirst();
        SLLNode<Card> second=secondPart.getFirst();

        secondPart.insertLast(first.element);
        firstPart.delete(first);
        firstPart.insertLast(second.element);
        secondPart.delete(second);

        int lengthFirst= firstPart.length();
        int lengthSecond= secondPart.length();

        first=firstPart.getFirst();
        second=secondPart.getFirst();



        if(lengthSecond % 2 == 0){ //even
            lengthSecond /=2;
        }else{ //odd
            lengthSecond /=2 +1;
        }

        SLLNode<Card> preposledna=null; //take the second to last
        while (first.succ !=null){
            preposledna=first;
            first=first.succ;
        }


        //dvizime kon sredina
        for(int i=0;i<lengthSecond;i++){
             second=second.succ;
        }//na kraj pokazuva kon sredina

        secondPart.insertBefore(preposledna.element,second);
        firstPart.delete(preposledna);

    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        SLL<Card> prvDel = new SLL<>();
        SLL<Card> vtorDel = new SLL<>();

        for(int i=0;i<6;i++)
        {
            int id = input.nextInt();
            int rank = input.nextInt();
            Card c1 = new Card(id,rank);
            prvDel.insertLast(c1);
        }
        for(int j=0;j<6;j++)
        {
            int id = input.nextInt();
            int rank = input.nextInt();
            Card c2 = new Card(id,rank);
            vtorDel.insertLast(c2);
        }
        tarotCards(prvDel,vtorDel);
        System.out.println(prvDel.toString());
        System.out.println(vtorDel.toString());
    }
}

class Card
{
    private int id;
    private int rank;

    public Card(int id, int rank)
    {
        this.id=id;
        this.rank=rank;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

class SLLNode<E>
{
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