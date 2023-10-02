package DailyTemperatures;


import javax.swing.text.*;
import java.io.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;


abstract class Temperature{
    int temperatureValue;

    public Temperature(int temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    abstract double getCelcius();
    abstract double getFarenheit();


    public int getTemperatureValue() {
        return temperatureValue;
    }
}


class Celcius extends Temperature{


    public Celcius(int value) {
        super(value);
    }

    @Override
    double getCelcius() {
        return this.temperatureValue;
    }

    @Override
    double getFarenheit() {
        return temperatureValue * 9.0 /5.0 +32;
    }

    @Override
    public String toString() {
        return String.format("%dC",this.temperatureValue);
    }
}

class Farenheit extends Temperature{


    public Farenheit(int value) {
        super(value);
    }

    @Override
    double getCelcius() {
       return (temperatureValue - 32) *5.0 /9.0;
    }

    @Override
    double getFarenheit() {
        return temperatureValue;
    }

    @Override
    public String toString() {
        return String.format("%dF",this.temperatureValue);
    }
}


class Temperatures implements Comparable<Temperatures>{
    int day;
    List<Temperature> temperatures;


    public Temperatures(int day, List<Temperature> temperatures) {
        this.day = day;
        this.temperatures = temperatures;
    }

    public int getDay() {
        return day;
    }

    @Override
    public int compareTo(Temperatures o) {
        return Integer.compare(this.getDay(),o.getDay());
    }

    public String toString(char scale) {
        DoubleSummaryStatistics doubleSummaryStatistics=temperatures.stream()
                                                                    .mapToDouble(temperature -> scale == 'C' ? temperature.getCelcius() : temperature.getFarenheit())
                                                                    .summaryStatistics();


        //[ден]: Count: [вк. мерења - 3 места] Min: [мин. температура] Max: [макс. температура] Avg: [просек ]
               return String.format("%d: Count: %3d Min: %6.2f%s Max: %6.2f%s Avg: %6.2f%s",day,
                                                                                      doubleSummaryStatistics.getCount(),
                                                                                      doubleSummaryStatistics.getMin(),
                                                                                      scale,
                                                                                      doubleSummaryStatistics.getMax(),
                                                                                      scale,
                                                                                      doubleSummaryStatistics.getAverage(),
                                                                                      scale
                                                                                                                            );
    }
}


class TemperaturesFactory{

    //299 18C 17C 17C 18C 16C 19C 16C 16C 17C 18C 21C 21C
    public static Temperatures createTemps(String line){
        String [] parts=line.split("\\s+");
        int day=Integer.parseInt(parts[0]);

        List<Temperature> lista=new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(part ->{
                    char type=part.charAt(part.length()-1);
                    int temperatureValue=Integer.parseInt(part.substring(0,part.length()-1));
                    if(type=='C'){
                        lista.add(new Celcius(temperatureValue));
                    }else{
                        lista.add(new Farenheit(temperatureValue));
                    }
                });
        return new Temperatures(day,lista);
    }
}

class DailyTemperatures{

    List<Temperatures> temperatures;

    public DailyTemperatures() {
        temperatures=new ArrayList<>();
    }

    public void readTemperatures(InputStream in) {
        temperatures = new BufferedReader(new InputStreamReader(in))
                                         .lines()
                                         .map(line -> TemperaturesFactory.createTemps(line))
                                         .collect(Collectors.toList());
    }

    public void writeDailyStats(OutputStream out, char c) {
        PrintWriter pw=new PrintWriter(out,true);

        temperatures.stream()
                    .sorted(Comparator.naturalOrder())
                    .forEach(temp -> pw.println(temp.toString(c)));
    }
}




public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}
