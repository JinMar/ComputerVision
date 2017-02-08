package cz.tul.services.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 13.12.2016.
 */
public class ImageNotFoundException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(ImageNotFoundException.class);

    public ImageNotFoundException(String message) {
        super(message);
    }
}
