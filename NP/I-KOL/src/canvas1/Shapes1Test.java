package canvas1;

import java.io.*;
import java.util.*;
import java.util.stream.*;

class Square {
    int square;


    public Square(int square) {
        this.square = square;
    }

    public int getSquare() {
        return square;
    }


    public int getPerimeter(){
        return square*4;
    }
}


class Canvas implements Comparable<Canvas>{

    String id;
    List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }


    public int getTotalSquares(){
        return squares.size();
    }


    public int getTotalPerimetar(){
     return    squares.stream()
                .mapToInt(squares->squares.getPerimeter())
                .sum();
    }


    //364fbe94 14 1556
    @Override
    public String toString() {
        return String.format("%s %d %d",id,squares.size(),getTotalPerimetar());
    }

    @Override
    public int compareTo(Canvas o) {
        return Integer.compare(this.getTotalPerimetar(),o.getTotalPerimetar());
    }
}


class CanvasFactory{

    //364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
    public static Canvas createCanvas(String line){

        List<Square> squares = new ArrayList<>();
        String[] parts = line.split("\\s+");

        String id = parts[0];


//        squares = Arrays.stream(parts)
//                .skip(1)
//                .mapToInt(square -> Integer.parseInt(square))
//                .mapToObj(square -> new Square(square))
//                .collect(Collectors.toList());

        squares = Arrays.stream(parts)
                .skip(1)
                .map(square -> new Square(Integer.parseInt(square)))
                .collect(Collectors.toList());



        return new Canvas(id, squares);
    }
}

class ShapesApplication{

    List<Canvas> canvasList;


    public int readCanvases(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));

        canvasList = bf.lines()
                .map(line -> CanvasFactory.createCanvas(line))
                .collect(Collectors.toList());



     return   canvasList.stream().mapToInt(canvas->canvas.getTotalSquares()).sum();
    }

    public void printLargestCanvasTo(OutputStream out) {

        PrintWriter pw = new PrintWriter(out, true);

        Canvas largest = canvasList.stream()
                .max(Comparator.naturalOrder())
                .get();

        pw.println(largest);
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}