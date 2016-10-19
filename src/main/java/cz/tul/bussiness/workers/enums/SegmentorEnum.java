package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 19.10.2016.
 */
public enum SegmentorEnum {
    TRESHHOLDING("treshholding"),
    COLORING("coloring");


    private String segmentorName;

    SegmentorEnum(String key) {
        this.segmentorName = key;
    }

    public String getSegmentorName() {
        return segmentorName;
    }
}
