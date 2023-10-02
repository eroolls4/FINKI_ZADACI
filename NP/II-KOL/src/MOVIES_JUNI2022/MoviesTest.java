package MOVIES_JUNI2022;


import java.util.*;
import java.util.stream.*;

/**
 * Да се напише класа за филм Movie во која се чува:
 *
 * наслов
 * режисер
 * жанр
 * рејтинг
 * Да се имплементира конструктор со следните аргументи Movie(String title, String director, String genre, float rating).
 *
 * Потоа да се напише класа MovieCollection во која се чува колекција од филмови. Во оваа класа треба да се имплементираат следните методи:
 *
 * public void addMovie(Movie movie) - додавање филм во колекцијата
 * public void printByGenre(String genre) - ги печати сите филмови од проследениот жанр (се споредува стрингот без разлика на мали и големи букви), сортирани според насловот на филмот (ако насловот е ист, се сортираат според рејтингот).
 * public List<Movie> getTopRatedN(int n) - враќа листа на најдобро рангираните N филмови (ако има помалку од N филмови во колекцијата, ги враќа сите).
 * public Map<String, Integer> getCountByDirector(int n) - враќа мапа од имињата на режисерите со бројот на филмови кои тие го режирале сортирани според имињата на режисерите.
 */



class Movie{
    String name;
    String director;
    String genre;
    float rating;

    public Movie(String name, String director, String genre, float rating) {
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    //Adult Life Skills (Rachel Tunnard, Comedy) 6.20


    @Override
    public String toString() {
        return String.format("%s (%s, %s) %.2f",name,director,genre,rating);
    }
}


class MoviesCollection{

    List<Movie> movies;
    Map<String,List<Movie>> moviesByGenre;


    final static Comparator<Movie> cmpByGenre=Comparator.comparing(Movie::getName).thenComparingDouble(Movie::getRating);
    final static Comparator<Movie> cmpRating=Comparator.comparingDouble(Movie::getRating).reversed();



    MoviesCollection(){
        movies=new ArrayList<>();
        moviesByGenre=new HashMap<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);

        moviesByGenre.putIfAbsent(movie.getGenre().toLowerCase(), new ArrayList<>());
        moviesByGenre.get(movie.getGenre().toLowerCase()).add(movie);
    }

    public void printByGenre(String genre) {
        if(moviesByGenre.containsKey(genre.toLowerCase())){
            moviesByGenre.get(genre.toLowerCase()).stream().sorted(cmpByGenre).forEach(System.out::println);
        }

   //     movies.stream().filter(movie -> movie.getGenre().equalsIgnoreCase(genre)).sorted(cmpByGenre).forEach(System.out::println);
    }

    public List<Movie> getTopRatedN(int printN) {
      if(movies.size()< printN){
          return movies.stream().sorted(cmpRating).collect(Collectors.toList());
      }else{
          return movies.stream().limit(printN).sorted(cmpRating).collect(Collectors.toList());
      }
    }

    public Map<String, Integer> getCountByDirector(int n) {

//        return movies.stream().limit(n).sorted(Comparator.comparing(Movie::getDirector))
//                                       .collect(Collectors.groupingBy(Movie::getDirector,TreeMap::new,Collectors.summingInt(i->1)));

    return   movies.stream()
                   .limit(n).collect(Collectors.toMap(Movie::getDirector,movie -> 1,Integer::sum,TreeMap::new));

    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int printN = scanner.nextInt();
        scanner.nextLine();
        MoviesCollection moviesCollection = new MoviesCollection();
        Set<String> genres = fillCollection(scanner, moviesCollection);
        System.out.println("=== PRINT BY GENRE ===");
        for (String genre : genres) {
            System.out.println("GENRE: " + genre);
            moviesCollection.printByGenre(genre);
        }
        System.out.println("=== TOP N BY RATING ===");
        printList(moviesCollection.getTopRatedN(printN));

        System.out.println("=== COUNT BY DIRECTOR ===");
        printMap(moviesCollection.getCountByDirector(n));


    }

    static void printMap(Map<String,Integer> countByDirector) {
        countByDirector.entrySet().stream()
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    static void printList(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          MoviesCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Movie movie = new Movie(parts[0], parts[1], parts[2], Float.parseFloat(parts[3]));
            collection.addMovie(movie);
            categories.add(parts[2]);
        }
        return categories;
    }
}
