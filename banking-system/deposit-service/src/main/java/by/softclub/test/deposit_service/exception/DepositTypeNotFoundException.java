package by.softclub.test.deposit_service.exception;

public class DepositTypeNotFoundException extends RuntimeException {
    public DepositTypeNotFoundException(String message) {
        super(message);
    }
}