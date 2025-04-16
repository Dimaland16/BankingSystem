package by.softclub.test.clientservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Client not found with ID: " + id);
    }
}