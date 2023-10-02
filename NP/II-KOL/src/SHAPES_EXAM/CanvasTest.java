package SHAPES_EXAM;

import javax.net.ssl.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;



class InvalidIDException extends Exception{
    public InvalidIDException() {
    }
}

class InvalidDimensionException extends Exception{
    public InvalidDimensionException() {
    }
}



abstract class Shape{


    abstract double calcuatePerimter();
    abstract double calculateArea();

    abstract void scale(double coeff);
}


class Cirlce extends Shape{

    double radius;

    public Cirlce(double radius) {
        this.radius = radius;
    }

    @Override
    double calcuatePerimter() {
        return 2 * radius * Math.PI;
    }

    @Override
    double calculateArea() {
        return radius * radius * Math.PI;
    }

    @Override
    void scale(double coeff) {
        radius *= coeff;
    }

    @Override
    public String toString() {
        return String.format("Circle: -> Radius: %.2f Area: %.2f Perimeter: %.2f",radius,calculateArea(),calcuatePerimter());
    }
}

class Square extends Shape{
    double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    double calcuatePerimter() {
        return 4 * side;
    }

    @Override
    double calculateArea() {
        return side * side;
    }

    @Override
    void scale(double coeff) {
        side *=coeff;
    }


    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f",side,calculateArea(),calcuatePerimter());
    }
}


class Rectangle extends Shape{
    double side1;
    double side2;

    public Rectangle(double side1, double side2) {
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    double calcuatePerimter() {
        return 2 * side1 + 2 * side2;
    }

    @Override
    double calculateArea() {
        return side2 * side1;
    }

    @Override
    void scale(double coeff) {
        side1 *= coeff;
        side2 *= coeff;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f",side1,side2,calculateArea(),calcuatePerimter());
    }
}



class ShapeFactory{
    public static Shape createShape(String line) throws InvalidIDException, InvalidDimensionException {
        String[] parts = line.split("\\s+");
        int type = Integer.parseInt(parts[0]);
        double firstDimension = Double.parseDouble(parts[2]);
        if (firstDimension == 0.0)
            throw new InvalidDimensionException();
        if (type == 1) {
            return new Cirlce(firstDimension);
        } else if (type == 2) {
            return new Square(firstDimension);
        } else {
            double secondDimension = Double.parseDouble(parts[3]);
            if (secondDimension == 0.0)
                throw new InvalidDimensionException();
            return new Rectangle(firstDimension, secondDimension);
        }
    }

        public static String getUserID(String line) throws InvalidIDException {
            String[] parts = line.split("\\s+");
            String id = parts[1];
            if (!isValidID(id))
                throw new InvalidIDException();
            return id;
        }


    public static boolean isValidID(String id){
        Pattern pattern=Pattern.compile("\\w");
        Matcher matcher=pattern.matcher(id);
        return id.length() ==6 && matcher.matches();
    }
}





class Canvas{

    List<Shape> shapes;


    Map<String,Set<Shape>> shapesBYUSER;


    final static Comparator<Shape> cmpByAREA=Comparator.comparingDouble(Shape::calculateArea);
    final static Comparator<Shape> cmpFORUSER=Comparator.comparingDouble(Shape::calcuatePerimter);

    public Canvas() {
        shapesBYUSER=new HashMap<>();
        shapes=new ArrayList<>();

    }

    public void readShapes(InputStream in) {
        BufferedReader bf=new BufferedReader(new InputStreamReader(in));

        shapes = bf.lines().map(line -> {
            try {
                String id=ShapeFactory.getUserID(line);
                shapesBYUSER.putIfAbsent(id,new TreeSet<>(cmpFORUSER));
                Shape sh= ShapeFactory.createShape(line);
                shapesBYUSER.get(id).add(sh);
                return sh;
            } catch (InvalidIDException | InvalidDimensionException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());


    }

    public void printAllShapes(PrintStream out) {
        PrintWriter pw=new PrintWriter(out,true);
        shapes.stream().sorted(cmpByAREA).forEach(pw::println);


    }

    public void scaleShapes(String number, double coeff) {
        shapesBYUSER.getOrDefault(number, new HashSet<>()).forEach(iShape -> iShape.scale(coeff));
    }

    public void printByUserId(PrintStream out) {
        PrintWriter pw=new PrintWriter(out,true);
        Comparator<Map.Entry<String,Set<Shape>>> cmpByCount=Comparator.comparing(entry -> entry.getValue().size());

        shapesBYUSER.entrySet().stream()
                .sorted(cmpByCount.reversed().thenComparing(entry -> entry.getValue().stream().mapToDouble(Shape::calculateArea).sum()))
                .forEach(entry -> {
                    pw.println("Shapes of user: " + entry.getKey());
                    entry.getValue().forEach(pw::println);
                });
    }

    public void statistics(PrintStream out) {
        PrintWriter pw=new PrintWriter(out,true);
        DoubleSummaryStatistics dss=shapes.stream().mapToDouble(Shape::calculateArea).summaryStatistics();
        pw.println(String.format("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f", dss.getCount(), dss.getSum(), dss.getMin(), dss.getAverage(), dss.getMax()));
    }
}

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        canvas.readShapes(System.in);

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}