package FRONTPAGE_NEWS;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String name) {
        super(String.format("Category %s was not found",name));
    }
}

abstract class NewsItem{
      String name;
      Date createdAt;
      Category category;

    public NewsItem() {

    }

    public NewsItem(String name, Date createdAt, Category category) {
        this.name = name;
        this.createdAt = createdAt;
        this.category = category;
    }



    public String  getTeaser(){
        Date today=Calendar.getInstance().getTime();
        long minuts=Math.abs(today.getTime() - createdAt.getTime());
        minuts= TimeUnit.MILLISECONDS.toMinutes(minuts);
        return String.format("%s\n%d\n",this.name,minuts);
    }

}

class TextNewsItem extends NewsItem{
    String text;

    public TextNewsItem(String name, Date createdAt, Category category, String text) {
        super(name, createdAt, category);
        this.text = text;
    }

    @Override
    public String getTeaser() {
        return super.getTeaser() + String.format("%s\n",text.length() > 80 ? text.substring(0,80) : text );
    }

}

class MediaNewsItem extends NewsItem{
    String url;
    int views;

    public MediaNewsItem(String name, Date createdAt, Category category, String url, int views) {
        super(name, createdAt, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser() {
        return super.getTeaser() + String.format("%s\n%d\n",url,views);
    }

}

class Category{
    String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}


class FrontPage{

    Map<String,List<NewsItem>> newsByCategoryString;
    Map<Category,List<NewsItem>> newsByCategory;

    List<Category> categoriesList;
    List<NewsItem> newsItemList;


    public FrontPage() {
        newsByCategory=new HashMap<>();
        newsByCategoryString=new HashMap<>();

        categoriesList=new ArrayList<>();
        newsItemList=new ArrayList<>();
    }

    FrontPage(Category[] categories){
        List<Category> tmp= Arrays.stream(categories)
                                  .collect(Collectors.toList());

        categoriesList=tmp;
        newsItemList=new ArrayList<>();
    }

    void addNewsItem(NewsItem newsItem){
        this.newsItemList.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category){
//          List<NewsItem> res=new ArrayList<>();
//          for(NewsItem newsItem : newsItemList){
//              if(newsItem.category.equals(category)){
//                  res.add(newsItem);
//              }
//          }
//          return res;
        return newsItemList.stream()
                           .filter(newsItem -> newsItem.category.equals(category))
                           .collect(Collectors.toList());
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
//        List<NewsItem> res=new ArrayList<>();
//        for(NewsItem newsItem : newsItemList){
//            if(newsItem.category.categoryName.equals(category)){
//                res.add(newsItem);
//            }else{
//                throw new CategoryNotFoundException(newsItem.category.categoryName);
//            }
//        }
//        return res;

        if(!categoriesList.contains(new Category(category))){
            throw new CategoryNotFoundException(category);
        }

      return   newsItemList.stream().filter(newsItem -> newsItem.category.categoryName.equals(category)).collect(Collectors.toList());
    }


    @Override
    public String toString() {
       StringBuilder sb=new StringBuilder();
       newsItemList.forEach(newsItem -> sb.append(newsItem.getTeaser()));
       return sb.toString();
    }
}



public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde