package executors;

import annotations.annotationsForStatic.BeforeSuite;
import annotations.methodAnnotations.Test;

public class NoBeforeTest {
    // 2.. 3.. 1..

    @Test(priority = 5)
    public void itsFirstTest() {
        System.out.println("We run our tests - 1");
    }

    @Test(priority = 2)
    public void itsSecondTest() {
        System.out.println("We run our tests - 2");
    }

    @Test(priority = 3)
    public void itsNextTest() {
        System.out.println("We run our tests - 3");
    }


}
