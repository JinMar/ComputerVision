package cz.tul.provisioner.holder;

import cz.tul.entities.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Bc. Marek Jindrák on 26.10.2016.
 */
public class DataHolder {
    private static final Logger logger = LoggerFactory.getLogger(DataHolder.class);
    private String name;
    private Map<String, String> options;
    private AttributeType type;

    //setter
    public void setName(String name) {
        this.name = name;
    }


    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    //getter
    public Map<String, String> getOptions() {
        return options;
    }

    public String getName() {
        return name;
    }


    public AttributeType getType() {
        return type;
    }
}
