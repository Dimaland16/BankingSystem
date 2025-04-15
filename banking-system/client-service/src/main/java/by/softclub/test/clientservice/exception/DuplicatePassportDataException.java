package by.softclub.test.clientservice.exception;

public class DuplicatePassportDataException extends RuntimeException {
    public DuplicatePassportDataException(String series, String number) {
        super("Passport with series '" + series + "' and number '" + number + "' already exists");
    }
}