package executors;

import annotations.annotationsForStatic.AfterSuite;
import annotations.annotationsForStatic.BeforeSuite;
import annotations.methodAnnotations.AfterTest;
import annotations.methodAnnotations.BeforeTest;
import annotations.methodAnnotations.CsvSource;
import annotations.methodAnnotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestRunner {

    public static void runTests(Class c) throws IllegalAccessException {
        System.out.println("**********************");

        Method[] allMethods = c.getDeclaredMethods();
        Method beforeTestMethod = null;
        Method afterTestMethod = null;

        checkCountBeforeAfter(allMethods);

        SortedMap<Integer, Method> testMethodsMap = new TreeMap<Integer, Method> ();

        // сортировка тестов по приоритету и выборка BeforeTest, AfterTest-методов
        for (Method m : allMethods) {
            if (m.isAnnotationPresent(BeforeTest.class)) {
                beforeTestMethod = m;
            }

            if (m.isAnnotationPresent(AfterTest.class)) {
                afterTestMethod = m;
            }

            if (m.isAnnotationPresent(Test.class)) {


                Test test = (Test) m.getAnnotation(Test.class);
                int priority = test.priority();

                if (priority > 0 && priority < 11)
                    testMethodsMap.put(priority, m);
                else
                    throw new IllegalArgumentException("Значение priority должно быть от 1 до 10");

            }
        }

        // проверка, что аннотации AfterSuite, BeforeSuite - над static методами
        for (Method m : allMethods) {
            if (m.isAnnotationPresent(BeforeSuite.class)
                    || m.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new IllegalAccessException("Методы с аннотациями BeforeSuite и AfterSuite должны быть static");
                }
            }

            // запуск метода с BeforeSuite перед всеми тестами, если такой есть
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                invokeMethod(m, c);
            }
        }

        // Запуск тестовых методов
        if (!testMethodsMap.isEmpty()) {
            Collection<Method> methods = testMethodsMap.values();

            Method finalBeforeTestMethod = beforeTestMethod;
            Method finalAfterTestMethod = afterTestMethod;

            methods.forEach(testMethod -> {
                if (finalBeforeTestMethod != null) {
                    invokeMethod(finalBeforeTestMethod, c);
                }

                if (testMethod.isAnnotationPresent(CsvSource.class)) {
                    CsvSource csvSource = (CsvSource) testMethod.getAnnotation(CsvSource.class);
                    String params = csvSource.params();
                    String[] paramsArray = params.split(",");

                    int parameterCount = testMethod.getParameterCount();
                    if (paramsArray.length != parameterCount) {
                        throw new IndexOutOfBoundsException("Кол-во парааметров не соответствует кол-ву аргументов метода");
                    }

                    Class<?>[] parameterTypes = testMethod.getParameterTypes();

                    Object[] args = new Object[parameterCount];

                    for (int i = 0; i < paramsArray.length; i++) {
                        String s = paramsArray[i];
                        Class<?> paramType = parameterTypes[i];

                        String paramTypeName = paramType.getName();
                        if (paramTypeName.equals("int")) {
                            args[i] = Integer.parseInt(s.trim());
                        }

                        if (paramTypeName.equals("java.lang.String")) {
                            args[i] = s;
                        }

                        if (paramTypeName.equals("boolean")) {
                            args[i] = Boolean.parseBoolean(s.trim());
                        }

                    }

                    try {
                        testMethod.invoke(c.getDeclaredConstructor().newInstance(), args);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        System.out.println(e.getCause());
                    }
                } else {
                    invokeMethod(testMethod, c);
                }

                if (finalAfterTestMethod != null) {
                    invokeMethod(finalAfterTestMethod, c);
                }
             });

        }

        // запуск метода с AfterSuite после всех тестов, если такой есть
        for (Method m : allMethods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                invokeMethod(m, c);
            }
        }

    }

    public static void invokeMethod(Method m, Class c) {
        try {
            m.invoke(c.getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException e) {
            System.out.println(e.getCause());
        }
    }

    public static void checkCountBeforeAfter(Method[] allMethods) {
        System.out.println("Проверка кол-ва методов Before и After");

        checkCountOfAnnotatedMethods(allMethods);

        System.out.println("Проверка прошла успешно");
    }

    public static void checkCountOfAnnotatedMethods(Method[] allMethods) {
        int countOfMethods = 0;
        for (Method m : allMethods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                countOfMethods++;
                if (countOfMethods > 1) {
                    System.out.println("Методов с аннотацией BeforeSuite больше одного (ожидается 1)");
                    return;
                }
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                countOfMethods++;
                if (countOfMethods > 1) {
                    System.out.println("Методов с аннотацией AfterSuite больше одного (ожидается 1)");
                    return;
                }
            }
        }
    }

}
