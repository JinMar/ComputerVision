package cz.tul.bussiness.workflow.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public class AssigmentError extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(AssigmentError.class);

    public AssigmentError(String message) {
        super(message);
    }
}
