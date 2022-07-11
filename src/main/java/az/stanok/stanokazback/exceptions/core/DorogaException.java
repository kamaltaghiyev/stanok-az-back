package az.stanok.stanokazback.exceptions.core;

public class DorogaException extends RuntimeException {
    private final String messageLoc;

    public DorogaException(String message) {
        this(message, message);
    }

    public DorogaException(String message, Throwable e) {
        this(message, message, e);
    }

    public DorogaException(String message, String messageLoc) {
        super(message);
        this.messageLoc = messageLoc;
    }

    public DorogaException(String message, String messageLoc, Throwable e) {
        super(message, e);
        this.messageLoc = messageLoc;
    }

    public String getMessageLoc() {
        return messageLoc;
    }
}
