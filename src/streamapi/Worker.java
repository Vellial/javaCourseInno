package streamapi;

public class Worker {
    private String name;
    private String position;
    private int age;

    public Worker(String name, String position, int age) {
        this.name = name;
        this.position = position;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
