package threads;

public class Task implements Runnable {
    private int numberOfTask;

    public Task(int n) {
        numberOfTask = n;
    }

    @Override
    public void run() {
        System.out.println("This is task # " + numberOfTask);
    }
}
