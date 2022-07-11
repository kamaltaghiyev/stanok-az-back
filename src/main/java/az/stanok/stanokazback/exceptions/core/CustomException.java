package az.stanok.stanokazback.exceptions.core;

public class CustomException extends RuntimeException {
    private final String messageLoc;

    public CustomException(String message) {
        this(message, message);
    }

    public CustomException(String message, Throwable e) {
        this(message, message, e);
    }

    public CustomException(String message, String messageLoc) {
        super(message);
        this.messageLoc = messageLoc;
    }

    public CustomException(String message, String messageLoc, Throwable e) {
        super(message, e);
        this.messageLoc = messageLoc;
    }

    public String getMessageLoc() {
        return messageLoc;
    }
}
