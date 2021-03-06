package cz.tul.controllers.transferObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 24.10.2016.
 */
public class WrappedChainDTO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(WrappedChainDTO.class);
    private boolean ready;
    private String message;
    private List<PartData> parts;
    private boolean error;

    public WrappedChainDTO() {

    }

    //SETTERS
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setParts(List<PartData> parts) {
        this.parts = parts;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    //GETTERS
    public boolean isReady() {
        return ready;
    }

    public List<PartData> getParts() {
        return parts;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
