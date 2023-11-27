package TES12BABA;

import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String name;
    String department;
    double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }
}

public class GroupingByExample {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(

                new Employee("Alice", "HR", 50000),
                new Employee("Bob", "Finance", 60000),
                new Employee("Eroll", "Abc", 50000),
                new Employee("ZZZZ", "Abc", 60000),
                new Employee("Charlie", "HR", 55000),
                new Employee("David", "Finance", 70000)
        );

        Map<String, Double> totalSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        LinkedHashMap::new,
                        Collectors.summingDouble(Employee::getSalary)
                ));


        Map<String,Double> way2=employees.stream().collect(Collectors.toMap(Employee::getDepartment,Employee::getSalary,Double::sum,TreeMap::new));

        System.out.println(totalSalaryByDepartment);
        System.out.println(way2);
    }
}
