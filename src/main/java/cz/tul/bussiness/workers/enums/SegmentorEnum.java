package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 19.10.2016.
 */
public enum SegmentorEnum {
    TRESHHOLDING("treshholding"),
    COLORING("coloring"),
    THRESH_BINARY("THRESH_BINARY"),
    THRESH_BINARY_INV("THRESH_BINARY_INV"),
    THRESH_TOZERO("THRESH_TOZERO"),
    THRESH_TRUNC("THRESH_TRUNC"),
    WATERSHED("WATERSHED"),
    DISTANCETRANSFORM("DISTANCE_TRANSFORM");


    private String segmentorName;

    SegmentorEnum(String key) {
        this.segmentorName = key;
    }

    public String getSegmentorName() {
        return segmentorName;
    }
}
