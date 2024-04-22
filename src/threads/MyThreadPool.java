package threads;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool implements Executor {
    private int count;
    private boolean isRunning = true;

    private final LinkedList<Runnable> queue;

    private Thread[] workers;

    public MyThreadPool(int count) {
        this.count = count;
        queue = new LinkedList<>();
        workers = new Thread[count];

        for (int i = 0; i < count; i++) {
            Worker w = new Worker();
            workers[i] = new Thread(w);
            workers[i].start();
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
        if (!isRunning) { return; }
        isRunning = false;

        for (Thread w : workers) {
            w.interrupt();
        }
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
