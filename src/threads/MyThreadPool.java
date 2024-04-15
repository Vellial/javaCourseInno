package threads;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool implements Executor {
    private int count;
    private boolean isRunning = true;

    LinkedList<Runnable> queue;

    public MyThreadPool(int count) {
        this.count = count;

        final ReentrantLock mainLock = new ReentrantLock();
        mainLock.lock();
        try {
            for (int i = 0; i < count; i++) {
                new Thread(new Worker()).start();
            }
        } finally {
            mainLock.unlock();
        }
    }

    public void execute(Runnable runnableThread) {
        if (isRunning) {
            queue.offer(runnableThread);
        }
        if (!isRunning) {
            throw new IllegalStateException("Пул потоков завершился, новые потоки добавить нельзя");
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = queue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }


}
