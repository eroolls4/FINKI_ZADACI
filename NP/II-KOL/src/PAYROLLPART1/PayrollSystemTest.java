package PAYROLLPART1;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

abstract class Employee implements Comparable<Employee>{
    String id;
    String level;

    public Employee() {
    }

    public Employee(String id, String level) {
        this.id = id;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    abstract double salary();



    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f",id,level,salary());
    }

    @Override
    public int compareTo(Employee o) {
        return Comparator.comparingDouble(Employee::salary).reversed().thenComparing(Employee::getLevel).compare(this,o);
    }
}

class HourlyEmployee extends Employee{
    Double hoursWorked;
    Double moneyPerLevel;

    final static double coefficient=1.5;
    final static double regularHours=40.00;

    public HourlyEmployee(String id, String level, Double hours,Double moneyPerLevel) {
        super(id, level);
        this.hoursWorked = hours;
        this.moneyPerLevel=moneyPerLevel;
    }

    double overtimeHours(){
        return hoursWorked - regularHours;
    }

    @Override
    double salary() {
        if (hoursWorked > 40.00) {
            return (regularHours * moneyPerLevel) + (overtimeHours() * moneyPerLevel * coefficient);
        }
        return hoursWorked * moneyPerLevel;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Regular hours: %.2f Overtime hours: %.2f", hoursWorked<=40 ? hoursWorked : 40.00,hoursWorked<=40 ? 0.00 : overtimeHours());
    }
}

class FreelanceEmployee extends Employee{
    List<Integer>  ticketPoints;

    Double moneyPerLevel;

    public FreelanceEmployee(String id, String level, List<Integer> ticketPoints,Double moneyPerLevel) {
        super(id, level);
        this.ticketPoints = ticketPoints;
        this.moneyPerLevel=moneyPerLevel;
    }

    @Override
    double salary() {
        return sumOfTickets() * moneyPerLevel;
    }

    int sumOfTickets(){
        return ticketPoints.stream().mapToInt(i->i).sum();
    }


    int getTicketsCounts(){
        return ticketPoints.size();
    }

    @Override
    public String toString() {
        return super.toString() +  String.format(" Tickets count: %d Tickets points: %d",getTicketsCounts(),sumOfTickets());
    }
}

class EmployeeFactory{

    //F;72926a;level7;5;6;8;1
    //H;157f3d;level10;63.14
    public static Employee createEmployee(String line, Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        String[] parts = line.split(";");
        String type = parts[0];
        String id = parts[1];
        String level = parts[2];

        if (type.equals("F")) {
            List<Integer> ticketPoints = Arrays.stream(parts, 3, parts.length)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
            Double getMoneyPerLevel = ticketRateByLevel.get(level);
            return new FreelanceEmployee(id, level, ticketPoints, getMoneyPerLevel);
        } else {
            Double hours = Double.parseDouble(parts[3]);
            Double getMoneyPerLevel = hourlyRateByLevel.get(level);
            return new HourlyEmployee(id, level, hours, getMoneyPerLevel);
        }
    }

}

class PayrollSystem {
    List<Employee> employees;
    Map<String, Double> hourlyRateByLevel;
    Map<String, Double> ticketRateByLevel;


    public PayrollSystem() {
        hourlyRateByLevel=new HashMap<>();
        ticketRateByLevel=new HashMap<>();
        employees=new ArrayList<>();

    }

    PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        employees = new ArrayList<>();
    }

    public void readEmployees(InputStream in) {
        employees=new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(line->EmployeeFactory.createEmployee(line,hourlyRateByLevel,ticketRateByLevel))
                .collect(Collectors.toList());

        //  System.out.println(employees.size());
    }

    public Map<String, Set<Employee>> printEmployeesByLevels (OutputStream os, Set<String> levels){
        Map<String, Set<Employee>> grouped = employees
                .stream()
                .collect(Collectors.groupingBy(Employee::getLevel,
                        (Supplier<TreeMap<String, Set<Employee>>>)TreeMap::new,
                        Collectors.toCollection(TreeSet::new)));

        Set<String> keys = new HashSet<>(grouped.keySet());
        keys.stream().filter(x -> !levels.contains(x)).forEach(grouped::remove);
        return grouped;
    }
}

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });


    }
}