package cz.tul.entities;

/**
 * Created by Marek on 27.09.2016.
 */
public enum AttributeType {
    SIMPLE("SIMPLE"),
    MULTIPLEVALUES("MULTIPLEVALUES");

    private String attributeType;

    AttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeType() {
        return attributeType;
    }
}
