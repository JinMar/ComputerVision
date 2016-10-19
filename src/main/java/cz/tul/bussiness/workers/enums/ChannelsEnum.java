package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public enum ChannelsEnum {
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    GRAY("gray"),
    Y("y"),
    CB("cb"),
    CR("cr"),
    H("h");


    private String channelName;

    ChannelsEnum(String key) {
        this.channelName = key;
    }

    public String getChannelName() {
        return channelName;
    }
}
