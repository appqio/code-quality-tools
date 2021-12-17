package io.appq.checkstyle.checks.common;

public class TestClassWithStringConcatenation {

    public TestClassWithStringConcatenation() {
    }

    /**
     * Method scope vars should not be tested
     */
    public void doSomething(String s){
        String a = "My name is " + name + ".";
        String message = "";
        message += "hello";
        System.out.println("error: " + message);

        String b = "Single line literal"; //OK
        String.format("My name is %s.", "chiang"); //OK
        new StringBuilder().append("abc").append("def"); //OK
    }

}