package cz.tul.bussiness.jobs.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 23.02.2017.
 */
public class NoTemplateFound extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(NoTemplateFound.class);

    public NoTemplateFound(String message) {
        super(message);
    }

    public NoTemplateFound(String text, int... values) {
        super(String.format(text, values));
    }

}
