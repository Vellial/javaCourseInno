package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class TestPool {
    public static void main(String[] args) {
        MyThreadPool myPool = new MyThreadPool(4);

        for (int i = 0; i < 5; i++) {
            Task task = new Task(i);
            myPool.execute(task);
        }

        myPool.shutdown();

    }
}
