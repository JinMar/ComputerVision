package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
public class Message implements Serializable {
    private String chainId;
    private String message;
    private boolean state;

    public Message() {
    }

    //SETTERS
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    //GETTERS

    public String getChainId() {
        return chainId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isState() {
        return state;
    }
}
