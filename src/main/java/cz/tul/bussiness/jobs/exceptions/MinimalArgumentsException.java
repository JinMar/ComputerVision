package cz.tul.bussiness.jobs.exceptions;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public class MinimalArgumentsException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(SelectionLayerException.class);

    public MinimalArgumentsException(String message) {
        super(message);
    }

    public MinimalArgumentsException(String text, int... values) {
        super(String.format(text, values));
    }
}
