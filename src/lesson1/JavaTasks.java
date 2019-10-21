package lesson1;


import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JavaTasks {

    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // Сложность O(n*log(n)) Память O(11n)
    static public void sortTimes(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));

        ArrayList<String> AM = new ArrayList<>();
        ArrayList<String> PM = new ArrayList<>();

        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            String[] spliter = s.split(" ");
            if (Pattern.matches("((0[1-9]:)|(1[0-2]:))[0-5][0-9]:[0-5][0-9] ((AM)|(PM))", s)) {
                if (spliter[0].startsWith("12")) {
                    s = "00" + spliter[0].substring(2) + " " + spliter[1];
                }
                if (spliter[1].equals("AM")) {
                    AM.add(s);
                } else {
                    PM.add(s);
                }
            } else throw new NotImplementedError("Неверный формат");
        }
        Collections.sort(AM);
        Collections.sort(PM);
        AM.addAll(PM);

        for (String s : AM) {
            if (s.startsWith("00")) {
                s = "12" + s.substring(2);
            }
            writer.println(s);
        }
        writer.close();
    }


    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // Сложность O(n*log(n)) Память O(nk) где k максимальаня длина строки
    static public void sortAddresses(String inputName, String outputName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputName));
        PrintWriter writer = new PrintWriter(new File(outputName));

        TreeMap<String, TreeSet<String>> map = new TreeMap<>();
        TreeSet<String> nameSurname = new TreeSet<>();

        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            String[] spliter = s.split(" - ");
            String[] split = spliter[1].split(" ");
            if (Integer.parseInt(split[1]) < 10) {
                s = spliter[0] + " - " + split[0] + " 0" + split[1];
                spliter = s.split(" - ");
            }
            if (Pattern.matches(("[а-яА-Яa-zA-Z]+ [а-яА-Яa-zA-Z]+ - .+ [0-9][0-9]*"), s)) {
                if (map.containsKey(spliter[1])) {
                    nameSurname = map.get(spliter[1]);
                    nameSurname.add(spliter[0]);
                    map.put(spliter[1], nameSurname);
                } else {
                    nameSurname.add(spliter[0]);
                    map.put(spliter[1], nameSurname);
                }
                nameSurname = new TreeSet<>();

            } else throw new NotImplementedError(s);

        }

        String[] split;
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, TreeSet<String>> entry : map.entrySet()) {
            split = entry.getKey().split(" ");
            for (String en : entry.getValue()) {
                res.append(en + ", ");
            }
            writer.println(split[0] + " " + Integer.parseInt(split[1])
                    + " - " + res.substring(0, res.length() - 2));

            res.delete(0, res.length());
        }
        writer.close();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //Сложность О(n*log(n)) Память 7730(max)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        PrintWriter writer = new PrintWriter(new File(outputName));
        BufferedReader bf = new BufferedReader(new FileReader(new File(inputName)));

        TreeMap<Float, Integer> sort = new TreeMap<>();

        int a;
        String s = bf.readLine();

        while (s!=null) {
            if (sort.containsKey(Float.parseFloat(s))) {
                a = sort.get(Float.parseFloat(s));
                sort.put(Float.parseFloat(s), a + 1);
            } else {
                a = 1;
                sort.put(Float.parseFloat(s), a);
            }
            s = bf.readLine();
        }
        for (Map.Entry<Float, Integer> entry : sort.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                writer.println(entry.getKey());
            }
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
