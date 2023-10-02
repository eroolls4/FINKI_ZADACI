package TermFrequency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

class TermFrequency {
    Map<String, Integer> frequency;
    Set<String> stop;

    public TermFrequency(InputStream inputStream, String[] stopWords) {
        frequency = new TreeMap<String, Integer>();
        stop = new HashSet<String>();
        stop.addAll(Arrays.asList(stopWords));

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.trim();
            if (line.length() > 0) {
                String[] words = line.split(" ");
                for (String word : words) {
                    String key = normalize(word);
                    if (key.isEmpty() || stop.contains(key)) {
                        continue;
                    }
                  frequency.put(key,frequency.getOrDefault(key,0)+1);
                }
            }
        }
        scanner.close();
    }

    private static String normalize(String word) {
        return word.toLowerCase().replace(",", "").replace(".", "").trim();
    }

    public int countTotal() {
        return frequency.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int countDistinct() {
        return frequency.keySet().size();
    }

    public List<String> mostOften(int k) {
        return  frequency.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }


}
public class TermFrequencyTest {
    public static void main(String[] args){
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}