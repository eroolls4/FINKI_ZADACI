package JUSTTEST;


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


public class test {
    public static void main(String[] args) {

        Celcius c0 = new Celcius(5);
        Celcius c1 = new Celcius(10);
        Celcius c2 = new Celcius(15);
        Celcius c3 = new Celcius(20);
        Celcius c4 = new Celcius(25);

        Farenheit f0 = new  Farenheit(4);
        Farenheit f1 = new  Farenheit(14);
        Farenheit f2 = new  Farenheit(24);
        Farenheit f3 = new  Farenheit(34);
        Farenheit f4 = new  Farenheit(44);


    }
}
