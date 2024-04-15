package threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool implements Executor {
    private int count;
    private boolean isRunning = true;

    private final LinkedList<Runnable> queue;

    public MyThreadPool(int count) {
        this.count = count;
        queue = new LinkedList<>();

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
            synchronized (queue) {
                queue.add(runnableThread);
                queue.notify();
            }
        }
        if (!isRunning) {
            throw new IllegalStateException("Пул потоков завершился, новые потоки добавить нельзя");
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    private final class Worker implements Runnable {
        Runnable task;

        @Override
        public void run() {
            while (isRunning) {
                synchronized (queue) {

                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Произошла ошибка во время ожидания очереди: " + e.getMessage());
                        }
                    }

                    task = queue.poll();
                }

                try {
                    assert task != null;
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Пул потоков не выполнился: " + e.getMessage());
                }
            }
        }
    }


}
