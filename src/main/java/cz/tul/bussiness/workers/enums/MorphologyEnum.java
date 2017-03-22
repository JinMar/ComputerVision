package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 19.10.2016.
 */
public enum MorphologyEnum {
    ERODE("erode"),
    DILATE("dilate"),
    OPEN("open"),
    CLOSE("close"),
    ERODEGS("erodeGS"),
    DILATEGS("dilateGS"),
    OPENGS("openGS"),
    CLOSEGS("closeGS"),
    TOPHAT("tophhat");
    private String morphologyName;

    MorphologyEnum(String key) {
        this.morphologyName = key;
    }

    public String getMorphologyName() {
        return morphologyName;
    }
}
