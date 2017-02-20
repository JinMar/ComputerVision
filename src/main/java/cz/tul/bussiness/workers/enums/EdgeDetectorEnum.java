package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public enum EdgeDetectorEnum {
    SOBEL("sobel"),
    LAPLACIAN("laplacian"),
    CANNY("canny");


    private String detectorlName;

    EdgeDetectorEnum(String key) {
        this.detectorlName = key;
    }

    public String getDetectorlName() {
        return detectorlName;
    }
}
