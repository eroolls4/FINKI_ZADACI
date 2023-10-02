package jan2022termin2;

import java.util.Scanner;

class Discussion {
    private int id;
    private int popularity;
    private int users;

    public Discussion(int id, int popularity, int users) {
        this.id = id;
        this.popularity = popularity;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

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
            ret += tmp;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += " " + tmp;
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

public class Forum {

    // TODO: implement function

    public static void forum(SLL<Discussion> health, SLL<Discussion> finance){
        SLLNode<Discussion> financeDvizi=finance.getFirst();
        SLLNode<Discussion> najmala=null;
        int min=9999;
        while (financeDvizi != null){
            int importance=financeDvizi.element.getPopularity()*financeDvizi.element.getUsers();
            if(importance<min){
                min=importance;
                najmala=financeDvizi;
            }
            financeDvizi=financeDvizi.succ;
        }
        finance.delete(najmala);


        SLLNode<Discussion> healthDvizi=health.getFirst();
        SLLNode<Discussion> najvaz=null;
        int max=0;
        while (healthDvizi != null){
            int importance=healthDvizi.element.getPopularity()*healthDvizi.element.getUsers();
            if(importance>max){
                max=importance;
                najvaz=healthDvizi;
            }
            healthDvizi=healthDvizi.succ;
        }
        finance.insertLast(najvaz.element);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numHealth = Integer.parseInt(scanner.nextLine());
        int numFinance = Integer.parseInt(scanner.nextLine());

        SLL<Discussion> health = new SLL<Discussion>();
        SLL<Discussion> finance = new SLL<Discussion>();

        for (int i = 0; i < numHealth; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            health.insertLast(new Discussion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }

        for (int i = 0; i < numFinance; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            finance.insertLast(new Discussion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }

        forum(health, finance);
        System.out.println(health.toString());
        System.out.println(finance.toString());
    }
}
