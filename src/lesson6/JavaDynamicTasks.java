package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

import static java.lang.Math.max;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */ //сложность O(nm),память O(nm) , где n, m — размеры строк.
    public static String longestCommonSubSequence(String first, String second) {
        int[][] table = new int[first.length() + 1][second.length() + 1];
        StringBuilder s = new StringBuilder();
        for (int i = first.length() - 1; i >= 0; i--) {
            for (int j = second.length() - 1; j >= 0; j--) {
                if (first.charAt(i) == second.charAt(j)) {
                    table[i][j] = 1 + table[i + 1][j + 1];
                } else {
                    table[i][j] = max(table[i + 1][j], table[i][j + 1]);
                }
            }
        }
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;
        while (table[i][j] != 0 && i < first.length() && j < second.length()) {
            if (first.charAt(i) == second.charAt(j)) {
                result.append(first.charAt(i));
                i++;
                j++;
            } else {
                if (table[i][j] == table[i + 1][j])
                    i++;
                else
                    j++;
            }
        }
        return result.toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    //сложность О(n^2),память O(n),Где n-кол-во элементов в лист.
    /**Все что закоментированно я пытался реализовать данное задание за O(nlog(n)), но как мне кажется
     * это возможно только для самой длинной последоательности ,которая встречается позже всех.
    */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {

        int n = list.size();
        if (n == 0) return new LinkedList<>();

        int[] d = new int[n];
        //  int[] pos = new int[n];
        int[] prev = new int[n];
        int length ;
        int counter = 0;
        // pos[0] = -1;
        // d[0] = Integer.MIN_VALUE;
        // for (int i = 1; i < n; i++) {
        //     d[i] = Integer.MAX_VALUE;
        // }
        // for (int i = 0; i < n; i++) {
        //     int l = -1;
        //     int j = list.size();
        //     while (l < j - 1) {
        //         int m = (l + j) / 2;
        //         if (d[m] < list.get(i))
        //             l = m;
        //         else
        //             j = m;
        //     }
        //     if (d[j - 1] < list.get(i) && list.get(i) < d[j] ) {
        //         d[j] = list.get(i);
        //         pos[j] = i;
        //         prev[i] = pos[j - 1];
        //         length = max(length, j);
        //     }
        // }
        for (int i = 0; i < n; i++) {
            d[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && d[j] + 1 > d[i]) {
                    d[i] = d[j] + 1;
                    prev[i] = j;
                }
            }
        }
        int pos = 0;
        length = d[0];
        for (int i = 0; i < n; i++) {
            if (d[i] > length) {
                pos = i;
                length = d[i];
            }
        }
        List<Integer> answer = new LinkedList<>();
        // int p = pos[length];
        // while (p != -1) {
        //     answer.add(list.get(p));
        //     p = prev[p];
        // }
        while (pos != -1) {
            answer.add(list.get(pos));
            pos = prev[pos];
        }
        Collections.reverse(answer);
        return answer;
    }


    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
