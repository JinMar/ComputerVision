package cz.tul.controllers.transferObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 24.10.2016.
 */
public class ChainInfo implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ChainInfo.class);
    private String chainId;

    public static Logger getLogger() {
        return logger;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
