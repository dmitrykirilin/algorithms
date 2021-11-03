package main.java.com.luxoft.kirilin.tasks;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Brackets {

/**
*   Дано целое число n.
*    Требуется вывести все правильные скобочные последовательности длины 2 ⋅ n,
*    упорядоченные лексикографически.
*    В задаче используются только круглые скобки.
*
*    Единственная строка входного файла содержит целое число n, 0 ≤ n ≤ 11
*
*    Ввод	Вывод
*       3    ((()))
*            (()())
*            (())()
*            ()(())
*            ()()()
*/

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(10));
        StringBuilder builder = new StringBuilder(0);
//        int initVal = Integer.parseInt(args[0]);
        int initVal = 10;
        builder.append("1".repeat(initVal - 1));
        int min = Integer.parseInt(builder.toString(), 2);

        builder.append("0".repeat(initVal));
        int max = Integer.parseInt(builder.toString(), 2);

        Map<Integer, String> collect = Stream.iterate(1, n -> n + 1).limit(initVal + 1)
                .map("0"::repeat)
                .collect(Collectors.toMap(String::length, Function.identity()));

        for (int i = min; i < max; i++) {
            String binaryString = Integer.toBinaryString(i);
            if (binaryString.length() < initVal * 2) {
                binaryString = collect.get(initVal * 2 - binaryString.length()).concat(binaryString);
            }
            if (binaryString.charAt(0) == '1' || binaryString.codePoints().filter(ch -> ch == '0').count() != initVal) {
                continue;
            }
            char[] chars = binaryString.toCharArray();
            if (checkingSequence(chars)) {
                System.out.println(binaryString
                        .replace('0', '(')
                        .replace('1', ')'));
            }
        }

    }

    private static boolean checkingSequence(char[] chars) {
        for (int j = 0, left = 0, right = 0; j < chars.length; j++) {
            if (chars[j] == '0') {
                left++;
                continue;
            }
            right++;
            if (right > left) {
                return false;
            }
        }
        return true;
    }
}
