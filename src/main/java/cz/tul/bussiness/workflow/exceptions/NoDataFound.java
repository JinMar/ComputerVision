package cz.tul.bussiness.workflow.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public class NoDataFound extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(NoDataFound.class);

    public NoDataFound(String message) {
        super(message);
    }
}
