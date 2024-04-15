package streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApiEx {
    public static void main(String[] args) {
        // 1.
        Stream<Integer> integerStream = Stream.of(1,2,4,5,2,35,6,8,4,2,1,3,2,1,9);
        integerStream
                .distinct()
                .forEach(System.out::println);

        // 2.
        Stream<Integer> thirdMaxValue = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        thirdMaxValue
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .limit(1)
                .forEach(System.out::println);

        // 3.
        Stream<Integer> nextIntegerStream = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        nextIntegerStream
                .sorted(Comparator.reverseOrder())
                .distinct()
                .skip(2)
                .limit(1)
                .forEach(System.out::println);

        // 6.
        Stream<String> stringStream = Stream.of("Omsk", "Moscow", "Saint-Petersburg", "Penza", "Pskov", "Tula");
        System.out.println(stringStream.max(Comparator.comparing(String::length)).orElseThrow());

        // 7. слово -- колво повторений
        String inputString = "this is new string why not is not not not is is is is is why";
        Arrays.stream(inputString.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((s, count) -> {
                    System.out.println("str: " + s + " count: " + count);
                });

        // 8. Строки по увеличению длины
        Stream<String> stringStreamL = Stream.of("Omsk", "Abcd", "Moscow", "Ooomsk", "Ladoga", "Saint-Petersburg", "Penza", "Pskov", "Tula");
        stringStreamL
                .sorted()
                .sorted(Comparator.comparing(String::length))
                .forEach(System.out::println);

        // 9.
        String[] tst = new String[]{
                "Test1 Test2 Test22 Test223 Test2",
                "Tst1 Test2 Test22 Test4223 Te3st2",
                "Test1 Test2234 Test22 Test25523 Test2",
                "Tt1 T3est2 6Test22 Test223 5Test2"
        };

        System.out.println(Arrays.stream(tst)
                .map(s -> s.split(" "))
                .flatMap(Stream::of)
                .max(Comparator.comparing(String::length)).orElse(null));

    }
}
