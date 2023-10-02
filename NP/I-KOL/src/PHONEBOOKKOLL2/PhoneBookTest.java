package PHONEBOOKKOLL2;

import java.util.*;

class Contact {
    String name;
    String number;
    static Comparator<Contact> COMPARATOR = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }
}

class DuplicateNumberException extends Exception {
    DuplicateNumberException (String number) {
        super(String.format("Duplicate number: %s", number));
    }
}

class PhoneBook {

    //prevent duplli
    Set<String> uniquePhoneNumbers;
    //           NAME   SETOFCONTACTS
    Map<String, Set<Contact>> contactsByName;
    Map<String,Set<Contact>> contactsBySubNumber;

    PhoneBook () {
        uniquePhoneNumbers = new HashSet<>();
        contactsByName = new HashMap<>();
        contactsBySubNumber=new TreeMap<>();
    }


    private List<String> getSubNumber(String number){
        List<String> res = new ArrayList<>();

        // 070123456  3 -> 070,701,012,123 ...
        //            4 -> 0701,7012,....


        //(минимална должина на бројот [number] е 3)
        for (int x = 3; x <= number.length(); x++) {
            for (int k = 0; k <= number.length() - x; k++) {
                res.add(number.substring(k, k + x));
            }
        }
        return res;
     }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact contact = new Contact(name, number);
        if (uniquePhoneNumbers.contains(number)) {
            throw new DuplicateNumberException(number);
        } else {
            uniquePhoneNumbers.add(number);
        }

        getSubNumber(number).forEach(sub -> {
            contactsBySubNumber.putIfAbsent(sub, new TreeSet<>());
            contactsBySubNumber.get(sub).add(contact);
        });

        contactsByName.putIfAbsent(name, new TreeSet<>(Contact.COMPARATOR));
        contactsByName.get(name).add(contact);

    }

    public void contactsByName(String name) {
        if (!contactsByName.containsKey(name)) {
            System.out.println("NOT FOUND");
            return ;
        } else {
        contactsByName.get(name)
                       .forEach(System.out::println);
        }
    }

    public void contactsByNumber(String part) {
        if (!contactsBySubNumber.containsKey(part)) {
            System.out.println("NOT FOUND");
            return ;
        } else {
             contactsBySubNumber.get(part)
                                .forEach(System.out::println);
        }
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
               phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}
