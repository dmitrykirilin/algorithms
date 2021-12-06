package main.java.com.luxoft.kirilin.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LongestSequence {

    /**
     * Требуется найти в бинарном векторе самую длинную
     * последовательность единиц и вывести её длину.
     *
     * Желательно получить решение, работающее за линейное время
     * и при этом проходящее по входному массиву только один раз.
     */
    
    public static void main(String[] args) throws IOException {
        String allFileInString = new Scanner(new File(args[0]), StandardCharsets.UTF_8).useDelimiter("\\Z").next();
        List<String> rows = new ArrayList<>(Arrays.asList(allFileInString.split("\\r\\n")));
        int result = 0;
        for (String row : rows) {
            int currentLength = 0;
            String binaryString = Integer.toBinaryString(Integer.parseInt(row));
            for (char c : binaryString.toCharArray()) {
                if(c == '1'){
                    currentLength++;
                    continue;
                }
                if(c == '0'){
                    if(currentLength == 0) continue;
                    if(result < currentLength) result = currentLength;
                    currentLength = 0;
                }
            }
            if(result < currentLength) result = currentLength;
        }
        System.out.println(result);
    }
}
