package cz.tul.controllers.transferObjects;

import cz.tul.entities.StateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 24.10.2016.
 */
public class ChainInfoDTO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ChainInfoDTO.class);
    private String chainId;

    private String message;

    private StateEnum state;

    public ChainInfoDTO(String chainId, String message, StateEnum state) {
        this.chainId = chainId;
        this.message = message;
        this.state = state;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getMessage() {
        return message;
    }

    public StateEnum getState() {
        return state;
    }
}
