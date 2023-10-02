package ONLINESHOP;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String id) {
        super(String.format("Product with  %s does not exist in the online shop!",id));
    }
}


class Product {

   String id;
   String name;

   LocalDateTime createdAt;
   double price;
   int quantitySold;

    public Product(String id, String name, LocalDateTime createdAt, double price) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.quantitySold=0;
    }

    public double buyProduct(int quantity){
        this.quantitySold +=quantity;
        return price * quantity;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantitySold() {
        return quantitySold;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + quantitySold +
                '}';
    }
}


class OnlineShop {


    List<Product> products;

    Map<String,Product> productById;

    Map<String,List<Product>> productsByCategory;

    OnlineShop() {
          products=new ArrayList<>();
          productById=new HashMap<>();
          productsByCategory=new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
               Product product=new Product(id,name,createdAt,price);

               productById.put(id,product);

               productsByCategory.putIfAbsent(category,new ArrayList<>());
               productsByCategory.get(category).add(product);

               products.add(product);
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException{
        if(!productById.containsKey(id)){
            throw new ProductNotFoundException(id);
        }
       return  productById.get(id).buyProduct(quantity);
    }

    private Comparator<Product> createComp(COMPARATOR_TYPE comparatorType){
        Comparator<Product> newestFirst = Comparator.comparing(Product::getCreatedAt).reversed();
        Comparator<Product> lowestPrice = Comparator.comparing(Product::getPrice);
        Comparator<Product> mostSold = Comparator.comparing(Product::getQuantitySold).reversed();

        switch (comparatorType) {
            case NEWEST_FIRST:
                return newestFirst;
            case OLDEST_FIRST:
                return newestFirst.reversed();
            case LOWEST_PRICE_FIRST:
                return lowestPrice;
            case HIGHEST_PRICE_FIRST:
                return lowestPrice.reversed();
            case MOST_SOLD_FIRST:
                return mostSold.thenComparing(Product::getId);
            default:
                return mostSold.reversed().thenComparing(Product::getId);
        }
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<List<Product>> result = new ArrayList<>();
        List<Product> listaOfProducts = new ArrayList<>();

        if (category != null) {
            listaOfProducts = productsByCategory.get(category).stream().sorted(createComp(comparatorType)).collect(Collectors.toList());
        } else {
            listaOfProducts = products.stream().sorted(createComp(comparatorType)).collect(Collectors.toList());
        }

        //22 prod 5 pageSize
        //4x5 + 1*2  = 5 pagesTotal needed
        //

        int totalProducts = listaOfProducts.size();
        //how many pages
        int pagesCount = (int) Math.ceil((double) totalProducts / pageSize);

        //calculatingStartingPoints
        // 0 1 2 3 4  ->   0 5 10 15 20
        List<Integer> startingPoints = IntStream.range(0, pagesCount).map(i -> i * pageSize).boxed().collect(Collectors.toList());

        for (int start : startingPoints) {
            int end;
            if((start+pageSize) > totalProducts){
                end=totalProducts;
            }else{
                end=start+pageSize;
            }
            result.add(listaOfProducts.subList(start, end));
        }
        return result;
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}

