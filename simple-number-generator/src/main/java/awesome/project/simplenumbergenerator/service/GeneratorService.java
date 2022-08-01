package awesome.project.simplenumbergenerator.service;

import awesome.project.simplenumbergenerator.entity.Generated;
import awesome.project.simplenumbergenerator.entity.GeneratedColumn;
import awesome.project.simplenumbergenerator.entity.NumbersCount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GeneratorService {

    private static final int ARRAY_COUNT = 5;
    private static final int MIN_NUMBERS_COUNT = 10;
    private static final int MAX_NUMBERS_COUNT = 100;
    private static long lastRequestTime = System.currentTimeMillis();

    public boolean isSimple(final int number) {
        return IntStream.rangeClosed(2, number / 2).noneMatch(i -> number % i == 0);
    }

    public Generated getSimpleNumbersArrays(NumbersCount numbersCount) {
        if (numbersCount.getAuto()) {
            if (lastRequestTime + 5000 > System.currentTimeMillis()
                    || numbersCount.getCount() == null) {
                lastRequestTime = 0;
                return Generated.generatedWithAuto();
            }
            lastRequestTime = System.currentTimeMillis();
        }

        int length = numbersCount.getCount() == null ? MIN_NUMBERS_COUNT : numbersCount.getCount();
        if (length < MIN_NUMBERS_COUNT || length > MAX_NUMBERS_COUNT) {
            length = MIN_NUMBERS_COUNT;
        }

        List<GeneratedColumn> columns = new ArrayList<>();
        int[][] array = new int[ARRAY_COUNT][length];
        HashSet<Integer> arraySet = new HashSet<>();
        int lineNumber = 0;
        int columnNumber = 0;
        while (lineNumber < length && columnNumber < ARRAY_COUNT) {
            int number = (int) (Math.random() * 100000);
            while (!isSimple(number) || arraySet.contains(number)) {
                number++;
            }
            arraySet.add(number);
            array[columnNumber][lineNumber] = number;
            if (lineNumber == length - 1) {
                columns.add(new GeneratedColumn(array[columnNumber]));
                columnNumber++;
                lineNumber = 0;
            } else {
                lineNumber++;
            }
        }
        return new Generated(columns, numbersCount.getAuto());
    }
}
