package cz.tul.bussiness.register.exceptions;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public class IllegalInputException extends Exception {

    public IllegalInputException() {
        super("Input data are not unique !!!");
    }

    public IllegalInputException(String text) {
        super(text);
    }

    public IllegalInputException(String text, String... values) {
        super(String.format(text, values));
    }
}
