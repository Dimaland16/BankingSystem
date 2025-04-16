package by.softclub.test.clientservice.exception;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(int age) {
        super("Invalid age: " + age + ". Client must be between 18 and 100 years old.");
    }
}