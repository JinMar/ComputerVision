package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 01.02.2017.
 */
public enum HoughTransformationEnum {
    CIRCLE("Circle"),
    LINE("Line"),
    STANDARD("HOUGH_STANDARD"),
    PROBABILISTIC("HOUGH_PROBABILISTIC"),
    MULTISCALE("HOUGH_MULTI_SCALE"),
    GRADIENT("HOUGH_GRADIENT");

    private String houghTransformatioName;

    HoughTransformationEnum(String key) {
        this.houghTransformatioName = key;
    }

    public String getGgometricTransformationName() {
        return houghTransformatioName;
    }
}

