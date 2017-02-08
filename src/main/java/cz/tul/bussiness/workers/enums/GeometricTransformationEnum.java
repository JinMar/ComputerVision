package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public enum GeometricTransformationEnum {
    ROTATE("Rotate"),
    RESIZE("Resize");
    private String geometricTransformation;

    GeometricTransformationEnum(String key) {
        this.geometricTransformation = key;
    }

    public String getGgometricTransformationName() {
        return geometricTransformation;
    }
}
