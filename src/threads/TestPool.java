package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TestPool {
    public static void main(String[] args) {
        MyThreadPool myPool = new MyThreadPool(4);

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                System.out.println("Тут что-то делается");
                                return "";
                            },
                            myPool
                    ));
        }

        myPool.shutdown();

    }
}
