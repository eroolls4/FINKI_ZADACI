package MOBILE_OPERATOR_KOL1_2021;


import java.awt.desktop.*;
import java.io.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;



class InvalidExe extends Exception{
    public InvalidExe(String e) {
        super(e);
    }
}
class IDValidator{
    public static boolean isValid(String id,int needed){
        if(id.length() != needed) return false;
        for(int i=0;i<id.length();i++){
            if(!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }
}


abstract class Costumer{
    String costumerId;
    int minutes;
    int sms;
    int gb;

    public Costumer(String costumerId, int minutes, int sms, int gb) throws InvalidExe {
        if(!IDValidator.isValid(costumerId,7)){
            throw new InvalidExe(String.format("%s is not a valid user ID", costumerId));
        }
        this.costumerId = costumerId;
        this.minutes = minutes;
        this.sms = sms;
        this.gb = gb;
    }


    abstract double totalPrice();


    abstract double commission();
}

class MCosutmer extends Costumer{


    static double BASE_PRICE_M = 750.0;

    static double FREE_MINUTES_M = 150.0;
    static int FREE_SMS_M = 60;
    static double FREE_GB_INTERNET_M = 10.0;

    static double PRICE_PER_MINUTES = 4.0;
    static double PRICE_PER_SMS = 4.0;
    static double PRICE_PER_GB = 20.0;

    static double COMMISSION_RATE = 0.04;

    public MCosutmer(String costumerId, int minutes, int sms, int gb) throws InvalidExe {
        super(costumerId, minutes, sms, gb);
    }
    @Override
    double totalPrice() {
//        double total = BASE_PRICE_M;
//        total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINUTES_M);
//        total += PRICE_PER_SMS * Math.max(0, sms - FREE_SMS_M);
//        total += PRICE_PER_GB * Math.max(0, gb - FREE_GB_INTERNET_M);
//        return total;


        double total = BASE_PRICE_M;
        if (minutes > FREE_MINUTES_M) {
            total += (PRICE_PER_MINUTES * (minutes - FREE_MINUTES_M));
        }
        if (sms > FREE_SMS_M) {
            total += (PRICE_PER_SMS * (sms - FREE_SMS_M));
        }
        if (gb > FREE_GB_INTERNET_M) {
            total += (PRICE_PER_GB * (gb - FREE_GB_INTERNET_M));
        }
        return total;
    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE;
    }
}

class SCostumer extends Costumer{

    static double BASE_PRICE_S = 500.0;

    static double FREE_MINUTES_S = 100.0;
    static int FREE_SMS_S = 50;
    static double FREE_GB_INTERNET_S = 5.0;

    static double PRICE_PER_MINUTES = 5.0;
    static double PRICE_PER_SMS = 6.0;
    static double PRICE_PER_GB = 25.0;

    static double COMMISSION_RATE = 0.07;

    public SCostumer(String costumerId, int minutes, int sms, int gb) throws InvalidExe {
        super(costumerId, minutes, sms, gb);
    }
    @Override
    double totalPrice() {
//        double total = BASE_PRICE_S;
//        total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINUTES_S);
//        total += PRICE_PER_SMS * Math.max(0, sms - FREE_SMS_S);
//        total += PRICE_PER_GB * Math.max(0, gb - FREE_GB_INTERNET_S);
//        return total;
//
        double total = BASE_PRICE_S;
        if (minutes > FREE_MINUTES_S) {
            total += (PRICE_PER_MINUTES * (minutes - FREE_MINUTES_S));
        }
        if (sms > FREE_SMS_S) {
            total += (PRICE_PER_SMS * (sms - FREE_SMS_S));
        }
        if (gb > FREE_GB_INTERNET_S) {
            total += (PRICE_PER_GB * (gb - FREE_GB_INTERNET_S));
        }
        return total;

    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE;
    }
}

class SmetkaFactory{

    // 0       1  2  3   4    5     6    7   8  9  10    11   12  13 14 15       16    17 18 19 20
    //475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
    public static Smetka createSmetka(String line) throws InvalidExe {
        String [] parts=line.split("\\s+");
        String smetkaId=parts[0];
        List<Costumer> costumerList=new ArrayList<>();

        for(int i=1;i<parts.length;i+=5){
            String costumerId = parts[i];
            String type = parts[i + 1];
            int minutes = Integer.parseInt(parts[i + 2]);
            int sms = Integer.parseInt(parts[i + 3]);
            int gb = Integer.parseInt(parts[i + 4]);
            try {
                if (type.equals("M")) {
                    costumerList.add(new MCosutmer(costumerId, minutes, sms, gb));
                } else {
                    costumerList.add(new SCostumer(costumerId, minutes, sms, gb));
                }
            }catch (InvalidExe e){
                System.out.println(e.getMessage());
            }
        }
        return new Smetka(smetkaId,costumerList);
    }
}

class Smetka implements Comparable<Smetka>{
    String id;
    List<Costumer> costumers;


    public Smetka(String id, List<Costumer> costumers) {
        this.id = id;
        this.costumers = costumers;
    }


    public int totalCommisionRate(){
      return (int)  costumers.stream().mapToDouble(Costumer::commission).sum();
    }


    @Override
    public String toString() {
        DoubleSummaryStatistics dss=costumers.stream()
                                             .mapToDouble(Costumer::totalPrice)
                                             .summaryStatistics();

        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
                id,
                dss.getCount(),
                dss.getMin(),
                dss.getAverage(),
                dss.getMax(),
                totalCommisionRate());

    }

    @Override
    public int compareTo(Smetka o) {
        return Integer.compare(this.totalCommisionRate(),o.totalCommisionRate());
    }
}
class MobileOperator{
    List<Smetka> smetki;


    public void readSalesRepData(InputStream in) {
        smetki = new BufferedReader(new InputStreamReader(in)).lines()
                .map(line -> {
                    try {
                        return SmetkaFactory.createSmetka(line);
                    } catch (InvalidExe e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void printSalesReport(OutputStream out) {
        PrintWriter pw=new PrintWriter(out,true);

        smetki.stream().sorted().forEach(smetka -> pw.println(smetka));
    }
}


public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}
