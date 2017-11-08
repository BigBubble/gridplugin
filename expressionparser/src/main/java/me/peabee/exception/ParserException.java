package me.peabee.exception;

/**
 * Created by pengbo on 17-11-7.
 */
public class ParserException extends RuntimeException{
    private String message;
    public ParserException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
