package streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamWorkers {
    public static void main(String[] args) {
        List<Worker> workers = new ArrayList<>(Arrays.asList(
                new Worker("Any", "engineer", 28),
                new Worker("Mike", "engineer", 35),
                new Worker("Roma", "engineer", 25),
                new Worker("Lana", "engineer", 31),
                new Worker("Misha", "engineer", 26),
                new Worker("Lera", "engineer", 37),
                new Worker("Nike", "driver", 41),
                new Worker("Mary", "driver", 58),
                new Worker("Oleg", "driver", 22),
                new Worker("Oleg", "driver", 42),
                new Worker("Aly", "manager", 32),
                new Worker("Greg", "manager", 34),
                new Worker("Tom", "manager", 46)
        ));

        workers.stream()
                .filter(m -> m.getPosition().equals("engineer"))
                .sorted(Comparator.comparingInt(Worker::getAge).reversed())
                .limit(3)
                .forEach(worker -> System.out.println(worker.getName()));

        workers.stream()
                .filter(m -> m.getPosition().equals("engineer"))
                .collect(Collectors.groupingBy(Worker::getPosition, Collectors.averagingInt(Worker::getAge)))
                .forEach((s, aDouble) -> {
                    System.out.println(s + " - age: " + aDouble);
                });
    }
}
