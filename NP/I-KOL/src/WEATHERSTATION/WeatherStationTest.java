package WEATHERSTATION;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Measurement implements Comparable<Measurement> {
    float temperature;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurement(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    @Override
    public int compareTo(Measurement o) {
        return date.compareTo(o.date);
    }

    public Date getDate() {
        return date;
    }

    public float getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature, wind, humidity, visibility, date);
    }
}

class WeatherStation {
    Set<Measurement> measurements;
    int days;

    public WeatherStation(int days) {
        this.days = days;
        measurements = new TreeSet<>();
    }

    public void addMeasurement(float temperature, float wind, float humidity, float visibility, Date date) {
        // Ignore measurements with a time difference less than 2.5 minutes
        if (measurements.isEmpty() || Math.abs(date.getTime() - measurements.iterator().next().date.getTime()) >= 2.5 * 60 * 1000) {
            measurements.add(new Measurement(temperature, wind, humidity, visibility, date));
            removeOldMeasurements();
        }
    }

    private void removeOldMeasurements() {
        if (measurements.size() > days) {
            int excess = measurements.size() - days;
            for (int i = 0; i < excess; i++) {
                measurements.remove(measurements.iterator().next());
            }
        }
    }

    public int total() {
        return measurements.size();
    }

    public void status(Date from, Date to) {
        boolean foundMeasurements = false;
        for (Measurement measurement : measurements) {
            if (measurement.date.compareTo(from) >= 0 && measurement.date.compareTo(to) <= 0) {
                System.out.println(measurement);
                foundMeasurements = true;
            }
        }
        if (!foundMeasurements) {
            throw new RuntimeException("No measurements found in the specified period.");
        }
        System.out.println("Average temperature: " + calculateAverageTemperature(from, to));
    }

    private float calculateAverageTemperature(Date from, Date to) {
        float sumTemperature = 0;
        int count = 0;
        for (Measurement measurement : measurements) {
            if (measurement.date.compareTo(from) >= 0 && measurement.date.compareTo(to) <= 0) {
                sumTemperature += measurement.temperature;
                count++;
            }
        }
        return sumTemperature / count;
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurement(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}