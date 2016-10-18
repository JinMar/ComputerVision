package cz.tul.bussiness.register.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public class NoMethodFoundException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(NoMethodFoundException.class);

    public NoMethodFoundException() {
        super("No methods has been found !!!");
    }
}
