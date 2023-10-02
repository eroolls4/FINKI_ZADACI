package KOMPONENTI;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class InvalidPositionException extends Exception{
    public InvalidPositionException(int pos) {
        super(String.format(" Invalid position %d, alredy taken!",pos));
    }
}


class Component{

    String color;
    int weight;
    Set<Component> inner;

    static final Comparator<Component> cmpInner=Comparator.comparing(Component::getWeight)
                                                          .thenComparing(Component::getColor);

    public Component(String color, int weight) {
        this.color=color;
        this.weight=weight;
        inner=new TreeSet<>(cmpInner);
    }

//    public static void printTheRest(StringBuilder sb, Component component, int level) {
//        for (int i = 0; i < level; ++i)
//            sb.append("---");
//
//        sb.append(String.format("%d:%s\n", component.weight, component.color));
//
//        for (Component c : component.inner) {
//            printTheRest(sb, c, level + 1);
//        }
//    }


    public void addComponent(Component component) {
        inner.add(component);
    }

    public void changeColor(int weight, String color) {
        if(this.weight < weight){
            this.color=color;
        }
//        for(Component component : inner){
//            component.changeColorForAll(component,weight,color);
//        }
        inner.forEach(component -> component.changeColor(weight,color));
    }

    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < indent; i++)
            sb.append("-");

        sb.append(this.weight).append(":").append(this.color).append("\n");

        inner.forEach(component -> sb.append(component.toString(indent+3)));

        return sb.toString();
    }

    public int getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }
}

class Window{


    String name;
    Map<Integer,Component> componentByPos;


    public Window(String name) {
        this.name = name;
        componentByPos=new TreeMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {
                   if(componentByPos.containsKey(position)){
                       throw new InvalidPositionException(position);
                   }
                   componentByPos.put(position,component);
    }

    public void changeColor(int weight, String color) {
        for(Component c : componentByPos.values()){
            c.changeColor(weight,color);
        }
    }

    public void swichComponents(int pos1, int pos2) {
        Component componentPos1 = componentByPos.get(pos1);
        Component componentPos2 = componentByPos.get(pos2);

        componentByPos.put(pos1, componentPos2);
        componentByPos.put(pos2, componentPos1);
    }

    @Override
    public String toString() {
//       StringBuilder sb=new StringBuilder();
//       sb.append(String.format("WINDOW %s\n",this.name));
//
//       for(Map.Entry<Integer,Component> entry : componentByPos.entrySet()){
//              sb.append(String.format("%d:",entry.getKey()));
//              Component.printTheRest(sb,entry.getValue(),0);
//       }
//
//       return sb.toString();

        return String.format("WINDOW %s\n%s",
                name,
                componentByPos.entrySet().stream()
                        .map(entry -> String.format("%d:%s", entry.getKey(), entry.getValue().toString(0)))
                        .collect(Collectors.joining(""))

        );
    }
}


public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}
