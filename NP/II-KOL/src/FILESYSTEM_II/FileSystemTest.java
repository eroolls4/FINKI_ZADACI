package FILESYSTEM_II;


import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

class File implements Comparable<File>{
    private String name;
    private int size;
    private LocalDateTime createdAt;

    public File(String name, int size, LocalDateTime createdAt) {
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getYear(){
        return createdAt.getYear();
    }

    public String getMonthDay(){
        return createdAt.getMonth()+"-"+createdAt.getDayOfMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return size == file.size &&
                name.equals(file.name) &&
                createdAt.equals(file.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, createdAt);
    }

    @Override
    public int compareTo(File other) {
        return Comparator.comparing(File::getCreatedAt)
                .thenComparing(File::getName)
                .thenComparing(File::getSize)
                .compare(this,other);
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s",name,size,createdAt);
    }
}

class FileSystem{
    private HashMap<Character,TreeSet<File>> folders;

    public FileSystem() {
        folders = new HashMap<Character, TreeSet<File>>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime dateTime) {
        File file=new File(name,size,dateTime);
        folders.putIfAbsent(folder,new TreeSet<>());
        folders.get(folder).add(file);
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return folders.values().stream()
                .flatMap(x -> x.stream())
                .filter(file -> file.getName().charAt(0)=='.')
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folderCharacters) {
        // return collect.stream()
        //         .map(c -> folders.get(c))
        //         .mapToInt(x -> x.stream()
        //         .mapToInt(file -> file.getSize()).sum())
        //         .sum();

        return folders.values()
                .stream()
                .flatMap(x -> x.stream())
                .filter(file -> folderCharacters.contains(file))
                .mapToInt(file -> file.getSize())
                .sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return folders.values()
                .stream()
                .flatMap(x -> x.stream())
                .collect(Collectors.groupingBy(
                        File::getYear,
                        // v -> {
                        //     HashSet<File> files = new HashSet<File>();
                        //     files.add(v);
                        //     return files;
                        // },
                        // (o,n)->{
                        //     o.addAll(n);
                        //     return o;
                        // }
                        Collectors.toCollection(TreeSet::new)
                ));
    }

    public Map<String, Long> sizeByMonthAndDay() {
        return folders.values().stream()
                .flatMap(x -> x.stream())
                .collect(Collectors.groupingBy(
                        File::getMonthDay,
                        // x -> (long) x.getSize(),
                        // (ov,nv) -> ov+nv
                        Collectors.summingLong(File::getSize)

                ));
    }
}


// class File implements Comparable<File>{
//     char folder;
//     String name;
//     int size;
//     LocalDateTime createdAt;


//     public File(char folder, String name, int size, LocalDateTime createdAt) {
//         this.folder = folder;
//         this.name = name;
//         this.size = size;
//         this.createdAt = createdAt;
//     }

//     public String getName() {
//         return name;
//     }

//     public int getSize() {
//         return size;
//     }

//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }

//     public char getFolder() {
//         return folder;
//     }

//     public int getYear(){
//       return   getCreatedAt().getYear();
//     }

//     public String getMonthAndDay (){
//         return String.format("%s-%d", createdAt.getMonth().toString(), createdAt.getDayOfMonth());
//     }

//     @Override
//     public String toString() {
//         return String.format("%-10s %5dB %s",name,size,createdAt);
//     }

//     @Override
//     public int compareTo(File o) {
//         return Comparator.comparing(File::getCreatedAt)
//                          .thenComparing(File::getName)
//                          .thenComparing(File::getSize)
//                          .compare(this, o);
//     }
// }


// class FileSystem{


//     List<File> files;


//     public FileSystem() {
//         files=new ArrayList<>();
//     }

//     public void addFile(char fold, String name, int size, LocalDateTime localDateTime) {
//         File file = new File(fold, name, size, localDateTime);

//         files.add(file);
//     }

//     public List<File> findAllHiddenFilesWithSizeLessThen(int size) {

//     return   files.stream()
//                   .filter(file -> file.getName().startsWith("."))
//                   .filter(file -> file.getSize() < size)
//                   .sorted()
//                   .collect(Collectors.toList());
//     }

//     public int totalSizeOfFilesFromFolders(List<Character> folders) {

//         return files.stream()
//                     .filter(file -> folders.contains(file.folder))
//                     .mapToInt(File::getSize)
//                     .sum();
//     }

//     public Map<Integer, Set<File>> byYear() {

//         return files.stream()
//                     .collect(Collectors.groupingBy(
//                                                 File::getYear,
//                                                 Collectors.toCollection(TreeSet::new)
//         ));
//     }

//     public Map<String, Long> sizeByMonthAndDay() {

//         return  files.stream()
//                      .collect(Collectors.groupingBy(
//                                               File::getMonthAndDay,
//                                               Collectors.summingLong(File::getSize)
//         ));
//     }
// }

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

