package executors;

import annotations.methodAnnotations.Test;

public class OnlyTest {
    @Test(priority = 2)
    public void itsSecondTest() {
        System.out.println("It's simple test");
    }
}
