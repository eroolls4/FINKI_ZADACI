//package PhoneBook;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.*;
//
//class InvalidFormatException extends Exception{
//    public InvalidFormatException() {
//    }
//}
//
//
//class InvalidNameException extends Exception{
//    public InvalidNameException() {
//
//    }
//}
//
//class InvalidNumberException  extends Exception{
//    public InvalidNumberException () {
//
//    }
//}
//
//class MaximumSizeExceddedException extends Exception{
//    public MaximumSizeExceddedException() {
//
//    }
//}
//
//
//class Contact {
//    String name;
//    List<String> numbers;
//
//    Map<String, Contact> contactByName;
//
//
//    public Contact(String name, String... phonenumber) throws InvalidNumberException, InvalidNameException, MaximumSizeExceddedException {
//        if (!isValidName(name)) {
//            throw new InvalidNameException();
//        }
//        for (String n : phonenumber) {
//            if (!isValidNumber(n)) {
//                throw new InvalidNumberException();
//            }
//        }
//        if (phonenumber.length > 5) {
//            throw new MaximumSizeExceddedException();
//        }
//        this.name = name;
//        numbers = List.of(phonenumber);
//    }
//
//    public static boolean isValidName(String name) {
//        if (name.length() < 4 || (name.length() > 10)) {
//            return false;
//        }
//        String regex = "^[a-zA-Z]+$";
//        return name.matches(regex);
//    }
//
//    public static boolean isValidNumber(String number) {
//        String regex = "^(070|071|072|075|076|077|078)\\d{6}$";
//        return number.matches(regex);
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public List<String> getNumbers() {
//        return numbers.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
//    }
//
//    public void addNumber(String phonenumber) throws InvalidNumberException {
//        if (!isValidNumber(phonenumber)) {
//            throw new InvalidNumberException();
//        }
//        numbers.add(phonenumber);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(name).append("\n");
//        sb.append(numbers.size());
//        numbers.stream().sorted(Comparator.naturalOrder()).forEach(n -> sb.append(n).append("\n"));
//        ;
//        return sb.toString();
//    }
//
//    public static Contact valueOf(String s) throws InvalidFormatException {
//        String lines[] = s.split("\n");
//        try {
//            return new Contact(lines[0], Arrays.copyOfRange(lines, 2, lines.length));
//        } catch (InvalidNameException | InvalidNumberException | MaximumSizeExceddedException e) {
//            throw new InvalidFormatException();
//        }
//    }
//}
//
//
//class PhoneBook{
//        List<Contact> contacts;
//        Map<String,Contact> contactByName;
//
//
//
//    public PhoneBook() {
//        contacts=new ArrayList<>();
//        contactByName=new TreeMap<>();
//    }
//
//    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
//        String name=contact.getName();
//        if(!contactByName.containsKey(name)){
//            contacts.add(contact);
//           contactByName.put(name,contact);
//        }else{
//            throw new InvalidNameException();
//        }
//        if(contactByName.size() > 250){
//            throw new MaximumSizeExceddedException();
//        }
//    }
//    public Contact getContactForName(String name){
//        return contactByName.get(name);
//    }
//
//    public int numberOfContacts(){
//        return  contactByName.size();
//    }
//
//    public List<Contact> getContacts() {
//        return contactByName.values().stream().collect(Collectors.toList());
//    }
//
//
//    public boolean removeContact(String name){
//        if(contactByName.containsKey(name)){
//            contactByName.remove(name);
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    @Override
//    public String toString() {
//           StringBuilder sb=new StringBuilder();
//           contactByName.entrySet().stream().forEach(entry ->{
//               sb.append(entry.getValue()).append("\n");
//           });
//           return sb.toString();
//    }
//
//    public static boolean saveAsTextFile(PhoneBook phonebook,String path){
//        try (PrintWriter out =
//                     new PrintWriter(new File(path))) {
//            out.println(phonebook.toString());
//        }
//        catch ( IOException e ) {
//            return false;
//        }
//        return true;
//    }
//
//    public static PhoneBook loadFromTextFile(String path) throws IOException,InvalidFormatException {
//        try (Scanner jin  =
//                     new Scanner(new File(path))) {
//            PhoneBook res = new PhoneBook();
//            StringBuilder sb = new StringBuilder();
//            while ( jin.hasNextLine() ) {
//                String line = jin.nextLine();
//                if ( line.length() == 0 ) {
//                    if ( sb.toString().length() > 1 )
//                        res.addContact(Contact.valueOf(sb.toString()));
//                    sb = new StringBuilder();
//                }
//                else {
//                    sb.append(line).append('\n');
//                }
//
//            }
//            return res;
//        }
//        catch (InvalidNameException|MaximumSizeExceddedException e) {
//            throw new InvalidFormatException();
//        }
//    }
//
//
//    public long[] getContactsForNumber(String s) {
//        return new long[0];
//    }
//}
//
//
//
//
//public class PhonebookTester {
//
//    public static void main(String[] args) throws Exception {
//        Scanner jin = new Scanner(System.in);
//        String line = jin.nextLine();
//        switch( line ) {
//            case "test_contact":
//                testContact(jin);
//                break;
//            case "test_phonebook_exceptions":
//                testPhonebookExceptions(jin);
//                break;
//            case "test_usage":
//                testUsage(jin);
//                break;
//        }
//    }
//
//    private static void testFile(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while ( jin.hasNextLine() )
//            phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
//        String text_file = "phonebook.txt";
//        PhoneBook.saveAsTextFile(phonebook,text_file);
//        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
//        if ( ! pb.equals(phonebook) ) System.out.println("Your file saving and loading doesn't seem to work right");
//        else System.out.println("Your file saving and loading works great. Good job!");
//    }
//
//    private static void testUsage(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while ( jin.hasNextLine() ) {
//            String command = jin.nextLine();
//            switch ( command ) {
//                case "add":
//                    phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
//                    break;
//                case "remove":
//                    phonebook.removeContact(jin.nextLine());
//                    break;
//                case "print":
//                    System.out.println(phonebook.numberOfContacts());
//                    System.out.println(Arrays.toString(phonebook.getContacts()));
//                    System.out.println(phonebook.toString());
//                    break;
//                case "get_name":
//                    System.out.println(phonebook.getContactForName(jin.nextLine()));
//                    break;
//                case "get_number":
//                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
//                    break;
//            }
//        }
//    }
//
//    private static void testPhonebookExceptions(Scanner jin) {
//        PhoneBook phonebook = new PhoneBook();
//        boolean exception_thrown = false;
//        try {
//            while ( jin.hasNextLine() ) {
//                phonebook.addContact(new Contact(jin.nextLine()));
//            }
//        }
//        catch ( InvalidNameException e ) {
//
//            exception_thrown = true;
//        }
//        catch ( Exception e ) {}
//        if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw InvalidNameException");
//        /*
//		exception_thrown = false;
//		try {
//		phonebook.addContact(new Contact(jin.nextLine()));
//		} catch ( MaximumSizeExceddedException e ) {
//			exception_thrown = true;
//		}
//		catch ( Exception e ) {}
//		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
//        */
//    }
//
//    private static void testContact(Scanner jin) throws Exception {
//        boolean exception_thrown = true;
//        String names_to_test[] = { "And\nrej","asd","AAAAAAAAAAAAAAAAAAAAAA","Ð�Ð½Ð´Ñ€ÐµÑ˜A123213","Andrej#","Andrej<3"};
//        for ( String name : names_to_test ) {
//            try {
//                new Contact(name);
//                exception_thrown = false;
//            } catch (InvalidNameException e) {
//                exception_thrown = true;
//            }
//            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
//        }
//        String numbers_to_test[] = { "+071718028","number","078asdasdasd","070asdqwe","070a56798","07045678a","123456789","074456798","073456798","079456798" };
//        for ( String number : numbers_to_test ) {
//            try {
//                new Contact("Andrej",number);
//                exception_thrown = false;
//            } catch (InvalidNumberException e) {
//                exception_thrown = true;
//            }
//            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
//        }
//        String nums[] = new String[10];
//        for ( int i = 0 ; i < nums.length ; ++i ) nums[i] = getRandomLegitNumber();
//        try {
//            new Contact("Andrej",nums);
//            exception_thrown = false;
//        } catch (MaximumSizeExceddedException e) {
//            exception_thrown = true;
//        }
//        if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
//        Random rnd = new Random(5);
//        Contact contact = new Contact("Andrej",getRandomLegitNumber(rnd),getRandomLegitNumber(rnd),getRandomLegitNumber(rnd));
//        System.out.println(contact.getName());
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//    }
//
//    static String[] legit_prefixes = {"070","071","072","075","076","077","078"};
//    static Random rnd = new Random();
//
//    private static String getRandomLegitNumber() {
//        return getRandomLegitNumber(rnd);
//    }
//
//    private static String getRandomLegitNumber(Random rnd) {
//        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
//        for ( int i = 3 ; i < 9 ; ++i )
//            sb.append(rnd.nextInt(10));
//        return sb.toString();
//    }
//
//
//}
