package com.kilicciogluemre.GlobalException;

public class Exceptions {

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) { super(message); }
    }

    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String message) { super(message); }
    }

    public static class InactiveProductException extends RuntimeException {
        public InactiveProductException(String message) { super(message); }
    }

    public static class NoOrdersFoundException extends RuntimeException {
        public NoOrdersFoundException(String message) { super(message); }
    }

    public static class InvalidDataException extends RuntimeException {
        public InvalidDataException(String message) { super(message); }
    }
}
