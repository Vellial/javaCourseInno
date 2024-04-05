package executors;

import annotations.annotationsForStatic.AfterSuite;
import annotations.annotationsForStatic.BeforeSuite;
import annotations.methodAnnotations.CsvSource;
import annotations.methodAnnotations.Test;

public class ParametrizedTest {

    @BeforeSuite
    public static void beforeTestSuite() {
        System.out.println("*********** START ***********");
    }

    @CsvSource(params = "10, Java, 20, true")
    @Test(priority = 5)
    public void itsFirstTest(int first, String text, int second, boolean myBool) {
        System.out.println("We are programming on: " + text);
        System.out.println("First int: " + first);
        System.out.println("Second int: " + second);
        System.out.println("Is it works? - " + myBool);
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
        System.out.println("*********** END ***********");
    }

}
