package MoviesTest;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Movie {
    String name;
    List<Integer> ratings;


    public Movie(String name, List<Integer> rating) {
        this.name = name;
        this.ratings = rating;
    }


    public static List<Integer> converToList(int [] ratings){
        return Arrays.stream(ratings)
                .boxed()
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<Integer> getRating() {
        return ratings;
    }

    public double getAvgRating(){
        return ratings.stream()
                     .mapToDouble(i->i)
                     .average()
                     .orElse(0.0);
    }


    public double getRatingCoeff(){
        return getAvgRating() * getTotalRatings();
    }

    public int getTotalRatings(){
        return ratings.size();
    }


    //Great Expectations (1947) (6.44) of 9 ratings
    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",name,getAvgRating(),ratings.size());
    }
}

class MoviesList{

    Map<String,List<Movie>> moviesByTitle;

    final static Comparator<Movie> byAvgRating=Comparator.comparingDouble(Movie::getAvgRating)
                                                         .reversed()
                                                         .thenComparing(Movie::getName);

    public MoviesList() {
        moviesByTitle=new TreeMap<>();
    }

    public void addMovie(String title, int[] ratings) {
        List<Integer> input = Movie.converToList(ratings);
        Movie movie = new Movie(title, input);
        moviesByTitle.putIfAbsent(title, new ArrayList<>());
        moviesByTitle.get(title).add(movie);
    }

    public List<Movie> top10ByAvgRating(){

        return moviesByTitle.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(byAvgRating)
                .limit(10)
                .collect(Collectors.toList());
    }


    private int maxOfAllRatings(){
        return moviesByTitle
                .values()
                .stream()
                .flatMap(Collection::stream)
                .flatMap(i-> i.ratings.stream())
                .max(Comparator.naturalOrder())
                .get();
    }



    public List<Movie> top10ByRatingCoef() {
        int maxRatings = maxOfAllRatings();

        return moviesByTitle
                .values()
                .stream()
                .flatMap(Collection::stream)
                .sorted((m1, m2) -> Double.compare(m2.getRatingCoeff() / maxRatings, m1.getRatingCoeff() / maxRatings))
                .limit(10)
                .collect(Collectors.toList());
    }

}


public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

