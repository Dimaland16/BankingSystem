package by.softclub.test.deposit_service.exception;

public class DepositTypeAlreadyExistsException extends RuntimeException {
    public DepositTypeAlreadyExistsException(String message) {
        super(message);
    }
}
