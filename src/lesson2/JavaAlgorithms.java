package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;


@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //Сложность О(n^2) где n - самая длинная строка из 2,
    // Память О(n^2)
    static public String longestCommonSubstring(String first, String second) {

        StringBuilder s = new StringBuilder();
        int a = 0;
        int counter = 0;

        short[][] table = new short[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                table[i][j] = 0;
                if (first.charAt(i) == second.charAt(j)) {
                    table[i][j] = 1;
                    if (i > 0 && j > 0) {
                        table[i][j] += table[i - 1][j - 1];
                    }
                    if (table[i][j] > counter) {
                        counter = table[i][j];
                        a = j;
                    }
                }
            }
        }

        while (counter > 0) {
            s.append(second.charAt(a));
            a--;
            counter--;
        }
        return s.reverse().toString();
    }


    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //Сложность О(n*log(log(n)))
    // Память O(n)
    static public int calcPrimesNumber(int limit) {
        boolean[] primes = new boolean[limit + 1];
        Arrays.fill(primes, true);
        int prime = 0;
        for (int i = 2; i < limit + 1; ++i) {
            if (primes[i]) {
                prime++;
                for (int j = 2; i * j < primes.length; ++j) {
                    primes[i * j] = false;
                }
            }
        }
        return prime;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    //Сложность О(4*3^(длина слова -1) * кол-во слов в списке * длину строки матрицы * длину столбца в матрице)
    //Память(длину строки матрицы * длину столбца в матрице * 3^(длина слова -1)->(сколько вызовет рекурсий в стеке))
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File(inputName)));
        ArrayList<String> balda = new ArrayList<>();
        Set<String> resultSet = new HashSet<>();
        String s = bf.readLine();

        while (s != null) {
            if (Pattern.matches(("([А-ЯA-Z] )+[А-ЯA-Z]"), s)) {
                balda.add(s);
                s = bf.readLine();
            } else throw new NotImplementedError();
        }

        int lineCounter = balda.size();
        int columnCounter = balda.get(0).length() / 2 + 1;
        char[][] matrixBalda = new char[lineCounter][columnCounter];
        for (int i = 0; i < lineCounter; i++) {
            String[] split = balda.get(i).split(" ");
            for (int j = 0; j < columnCounter; j++) {
                matrixBalda[i][j] = split[j].charAt(0);
            }
        }

        for (String word : words) {
            for (int i = 0; i < lineCounter; i++) {
                for (int j = 0; j < columnCounter; j++) {
                    if (word.charAt(0) == matrixBalda[i][j] && balda(matrixBalda, word, 0, i, j
                             , lineCounter, columnCounter)) {
                        resultSet.add(word);
                    }
                }
            }
        }
        return resultSet;
    }
    static private boolean balda(char[][] matrixBalda, String word, int index, int i, int j
            , int lineCounter, int columnCounter) {
        if (index == word.length()) {
            return true;
        }
        char letter = word.charAt(index);
        if (matrixBalda[i][j] != letter) {
            return false;
        }
        boolean left = false, right = false, top = false, bot = false;
        if (i > 0 ) {
            bot = balda(matrixBalda, word, index + 1, i - 1, j, lineCounter, columnCounter);
        }
        if (i < lineCounter - 1 ) {
            top = balda(matrixBalda, word, index + 1, i + 1, j, lineCounter, columnCounter);
        }
        if (j > 0 ) {
            left = balda(matrixBalda, word, index + 1, i, j - 1, lineCounter, columnCounter);
        }
        if (j < columnCounter - 1) {
            right = balda(matrixBalda, word, index + 1, i, j + 1, lineCounter, columnCounter);

        }
        return left || right || top || bot;
    }
}