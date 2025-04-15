package by.softclub.test.clientservice.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Client with email '" + email + "' already exists");
    }
}
