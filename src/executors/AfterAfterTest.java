package executors;

import annotations.annotationsForStatic.AfterSuite;
import annotations.annotationsForStatic.BeforeSuite;
import annotations.methodAnnotations.Test;

public class AfterAfterTest {

    @BeforeSuite
    public static void beforeTestSuite() {
        System.out.println("This is preconditions 1");
    }

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

    @AfterSuite
    public static void afterTestSuite() {
        System.out.println("This is postconditions 1");
    }

    @AfterSuite
    public static void afterTestSuite2() {
        System.out.println("This is postconditions 2");
    }

}
