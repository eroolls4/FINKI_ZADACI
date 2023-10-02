package translateEnglishIntoMacedonian;

import java.io.*;
import java.util.*;

public class translate {
    public static void main(String[] args) throws IOException {
        /**
         * SOLVING WITH HASH CLASSES FROM JAVA
         */

        Map<String,String> mapa=new HashMap<>();
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(bf.readLine());
        for(int i=0;i<n;i++){
            String [] parts=bf.readLine().split(" ");
            String macedonian=parts[0];
            String ang=parts[1];
            mapa.put(ang,macedonian);
        }

        while (true){
            String word=bf.readLine();
            if(word.equals("KRAJ")){
                break;
            }
            if(mapa.containsKey(word)){
                System.out.println(mapa.get(word));
            }else{
                System.out.println("/");
            }
        }
    }
}
