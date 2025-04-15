package by.softclub.test.clientservice.exception;

public class DuplicatePhoneNumberException extends RuntimeException {
    public DuplicatePhoneNumberException(String phoneNumber) {
        super("Client with phone number '" + phoneNumber + "' already exists");
    }
}