package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
public class Message implements Serializable {
    private String chainId;
    private String message;

    public Message() {
    }

    //SETTERS
    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //GETTERS

    public String getChainId() {
        return chainId;
    }

    public String getMessage() {
        return message;
    }
}
