package cz.tul.entities;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
public enum StateEnum {
    COMPLETE("complete"),
    ERROR("error"),
    ACTIVE("active");
    private String state;

    StateEnum(String key) {
        this.state = key;
    }

    public String getState() {
        return state;
    }
}
