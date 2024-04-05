import executors.*;

public class StartAll {
    public static void main(String[] args) throws IllegalAccessException {
        TestRunner.runTests(ParametrizedTest.class);
//        TestRunner.runTests(OnlyTest.class);
//        TestRunner.runTests(Cls1Test.class);
//        TestRunner.runTests(Cls2Test.class);
//        TestRunner.runTests(NoAfterTest.class);
//        TestRunner.runTests(NoBeforeTest.class);
//        TestRunner.runTests(BeforeBeforeTest.class);
//        TestRunner.runTests(AfterAfterTest.class);
    }
}
