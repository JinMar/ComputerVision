package cz.tul.bussiness.workers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public class SelectionLayerException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(SelectionLayerException.class);

    public SelectionLayerException(String message) {
        super(message);
    }
}
