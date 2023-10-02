package ShapesApp;


import java.io.*;
import java.util.*;
import java.util.stream.*;




class InvalidCanvasException extends Exception{
    public InvalidCanvasException(String id,double maxArea) {

        super(String.format("Canvas %s has a shape with area larger than %.2f",id, maxArea));
    }
}

abstract class Shape{
    int area;


    public Shape(int area) {
        this.area = area;
    }

    abstract double calculateArea();


    abstract String getType();

}

class Square extends Shape{
    String type;

    public Square(int area) {
        super(area);
        type="S";
    }

    @Override
    double calculateArea() {
        return area * area;
    }

    @Override
    String getType() {
        return type;
    }

}

class Circle extends Shape{
    String type;

    public Circle(int area) {
        super(area);
        type="C";
    }



    @Override
  public   double calculateArea() {
        return area * area * Math.PI;
    }

    @Override
    String getType() {
        return type;
    }
}


class Shapes implements Comparable<Shapes>{
    String id;
    List<Shape> shapes;

    public Shapes(String id, List<Shape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }

   public double  sumOfAreas(){
      return  shapes.stream()
                    .mapToDouble(Shape::calculateArea)
                    .sum();
    }

   public int getSquareCount(){
        return (int) shapes.stream()
                           .filter(shape -> shape.getType().equals("S"))
                           .count();
   }

   public int getCircleCount(){
       return (int) shapes.stream()
                          .filter(shape -> shape.getType().equals("C"))
                          .count();
   }




    //ID total_shapes total_circles total_squares min_area max_area average_area
    @Override
    public String toString() {

        DoubleSummaryStatistics dss=shapes.stream().mapToDouble(Shape::calculateArea).summaryStatistics();

        return String.format("%s %d %d %d %.2f %.2f %.2f",id,shapes.size(),getCircleCount(),getSquareCount(),dss.getMin(),dss.getMax(),dss.getAverage());
    }




    @Override
    public int compareTo(Shapes o) {
        return Double.compare(this.sumOfAreas(),o.sumOfAreas());
    }
}



class ShapeFactory{
    public static Shape shapeInstance(int area,String type){
        switch (type){
            case "C" :
                return new Circle(area);
            case "S" :
                return new Square(area);
            default:
              return   null;
        }
    }
}


class ShapesFactory{
    public static Shapes createInstance(String line,double maxArea) throws InvalidCanvasException {
        String [] parts=line.split("\\s+");
//5e28f402 C 24 C 28 C 14 C 25 S 11 S 22 S 10 S 19 S 20 S 11 C 29
        String id=parts[0];
        List<Shape> res=new ArrayList<>(parts.length-1);

        for(int i=1;i< parts.length;i+=2){
            String type=parts[i];
            int area=Integer.parseInt(parts[i+1]);
            Shape condition=ShapeFactory.shapeInstance(area,type);
            if(condition.calculateArea() > maxArea){
                throw new InvalidCanvasException(id,maxArea);
            }else {
                if (type.equals("C")) {
                    res.add(new Circle(area));
                } else {
                    res.add(new Square(area));
                }
            }
        }
        return new Shapes(id,res);
    }
}

class ShapesApplication{
    double maxArea;
    List<Shapes> shapesList;

    ShapesApplication(double maxArea){
        this.maxArea=maxArea;
        shapesList=new ArrayList<>();
    }


    public void readCanvases(InputStream in) {

        shapesList=new BufferedReader(new InputStreamReader(in)).lines()
                                                               .map(line->{
                                                                  try {
                                                                     return ShapesFactory.createInstance(line,this.maxArea);
                                                                  }catch (InvalidCanvasException e){
                                                                      System.out.println(e.getMessage());
                                                                      return null;
                                                                  }
                                                               }).filter(Objects::nonNull)
                                                                .collect(Collectors.toList());
    }

    public void printCanvases(OutputStream out) {
        PrintWriter pw=new PrintWriter(out,true);


        shapesList.stream()
                  .sorted(Comparator.reverseOrder())
                  .forEach(shape -> pw.println(shape));
    }
}

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}