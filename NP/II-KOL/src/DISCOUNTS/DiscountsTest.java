package DISCOUNTS;

import java.io.*;
import java.util.*;
import java.util.stream.*;


class Product{
    int prevPrice;
    int discountedPrice;

    public Product(int prevPrice, int discountedPrice) {
        this.prevPrice = prevPrice;
        this.discountedPrice = discountedPrice;
    }

    public int discount() {
        return (prevPrice - discountedPrice) * 100 / prevPrice;
    }

    public int absoluteDiscount() {
        return prevPrice - discountedPrice;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", discount(), discountedPrice, prevPrice);
    }

}

class Store {
    String name;
    List<Product> products;


    public Store(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public double averageDiscount(){
        return products.stream()
                       .mapToDouble(product->product.discount())
                       .average()
                       .orElse(0.0);
    }

    public int totalDiscount(){
        return products.stream()
                       .mapToInt(product -> product.absoluteDiscount())
                       .sum();
    }

    @Override
    public String toString() {
        String productsStr = products.stream()
                                     .sorted(Comparator.comparing(Product::discount).thenComparing(Product::absoluteDiscount).reversed())
                                     .map(Product::toString)
                                     .collect(Collectors.joining("\n"));

        double rounded = Math.round(averageDiscount() * 10) / 10.;

        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s", name,
                rounded,
                totalDiscount(),
                productsStr);
    }




}


class StoreFactory{
    public static Store createStore(String line){
        //Desigual 5967:9115  5519:9378  3978:5563  7319:13092  8558:10541
        String[] parts = line.split(" ");
        String name = parts[0];
        List<Product> products=new ArrayList<>();
        for (int i = 1; i < parts.length; i+=2) {
            String part = parts[i];
            String[] chunksOfPart = part.split(":");
            Product product=new Product(Integer.parseInt(chunksOfPart[1]),Integer.parseInt(chunksOfPart[0]));
            products.add(product);
        }
        return new Store(name, products);
    }
}

class Discounts{
    List<Store> stores;

    public int readStores(InputStream in) {
        BufferedReader bf=new BufferedReader(new InputStreamReader(in));

        stores=bf.lines()
                 .map(line->StoreFactory.createStore(line))
                 .collect(Collectors.toList());

        return stores.size();
    }

    public List<Store> byAverageDiscount(){
        return stores.stream()
                     .sorted(Comparator.comparing(Store::averageDiscount).thenComparing(Store::getName))
                     .limit(3)
                     .collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount(){
        return stores.stream()
                     .sorted(Comparator.comparing(Store::totalDiscount).thenComparing(Store::getName))
                     .limit(3)
                     .collect(Collectors.toList());

    }
}

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}