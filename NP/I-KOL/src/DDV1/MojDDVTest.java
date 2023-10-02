package DDV1;


import javax.swing.text.*;
import java.io.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;




class AmountNotAllowedException extends Exception {

    int sumOfItems;

    public AmountNotAllowedException(int sumOfItems) {
        this.sumOfItems = sumOfItems;
    }

    @Override
    public String getMessage() {
        return String.format("Receipt with amount %d is not allowed to be scanned", sumOfItems);
    }
}

 abstract class Item{
    float price;

     public Item(float price) {
         this.price = price;
     }

     abstract double calculatePrice();


     public double getTaxReturn() {
         return calculatePrice() * 0.15;
     }


     public  float getPrice() {
         return price;
     }

     public void setPrice(float price) {
         this.price = price;
     }
 }

class itemA extends Item{

    public itemA(float price) {
        super(price);
    }

    private static double PER_VALUE=0.18;

    @Override
    double calculatePrice() {
        return price*PER_VALUE;
    }


}
class itemB extends Item{

    public itemB(float price) {
        super(price);
    }

    private static double PER_VALUE=0.05;

    @Override
    double calculatePrice() {
        return price*PER_VALUE;
    }
}
class itemV extends Item{

    public itemV(float price) {
        super(price);
    }

    @Override
    double calculatePrice() {
        return 0;
    }
}



class ItemFactory{
    public static Item createItem (char type, int price) {
        switch (type) {
            case 'A': return new itemA(price);
            case 'B': return new itemB(price);
            default: return new itemV(price);
        }
    }
}

class SmetkaFactory{
    public static Smetka createSmetka(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Item> items = new ArrayList<>();
        for (int i = 1; i < parts.length; i += 2) {
            Integer price = Integer.parseInt(parts[i]);
            char type = parts[i + 1].charAt(0);
            items.add(ItemFactory.createItem(type,price));
        }
        Smetka receipt = new Smetka(id, items);
        if (receipt.itemsPriceSum() > 30000)
            throw new AmountNotAllowedException(receipt.itemsPriceSum());
        return receipt;
    }
}


class Smetka {

     String id;
     List<Item> items;

    public Smetka(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public double taxReturnsSum() {
        return items.stream()
                .mapToDouble(i -> i.getTaxReturn())
                .sum();
    }
    public int itemsPriceSum() {
        return items.stream()
                .mapToInt(i -> (int) i.price)
                .sum();
    }

    @Override
    public String toString() {
        //70876 20538 53.42
        return String.format("%10s\t%10d\t%10.5f", id, itemsPriceSum(), taxReturnsSum());
    }
//    @Override
//    public String toString() {
//        return String.format("%s %d %.2f", id, itemsPriceSum(), taxReturnsSum());
//    }

}



class MojDDV{

    List<Smetka> lista;

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));


        lista = br.lines().map(line -> {
                    try {
                        return SmetkaFactory.createSmetka(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printTaxReturns(OutputStream out) {
        PrintWriter pw=new PrintWriter(out,true);

        lista.stream().forEach(smetka -> pw.println(smetka));
    }

    public void printStatistics(OutputStream out) {
        DoubleSummaryStatistics dss=lista.stream()
                                         .mapToDouble(smetka-> smetka.taxReturnsSum())
                                         .summaryStatistics();

        PrintWriter pw=new PrintWriter(out,true);
        pw.printf("min:\t%.3f\nmax:\t%3.f\n sum:\t%.3f\n count:\t%.3f\n avg:\t%.3f",dss.getMin(),
                                                                                    dss.getMax(),
                                                                                    dss.getSum(),
                                                                                    dss.getCount(),
                                                                                    dss.getAverage());
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);


         System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
         mojDDV.printStatistics(System.out);


    }
}