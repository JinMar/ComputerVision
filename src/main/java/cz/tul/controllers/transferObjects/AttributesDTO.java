package cz.tul.controllers.transferObjects;

import cz.tul.entities.AttributeType;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Bc. Marek Jindrák on 04.10.2016.
 */
public class AttributesDTO implements Serializable {


    private String defaultValues;
    private double minValue;
    private double maxValue;
    private String name;
    private AttributeType attributeType;
    private Map<String, String> options;

    //SETTERS

    public void setDefaultValues(String defaultValues) {
        this.defaultValues = defaultValues;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }


    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    //GETTERS

    public String getDefaultValues() {
        return defaultValues;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public String getName() {
        return name;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public Map<String, String> getOptions() {
        return options;
    }
}
