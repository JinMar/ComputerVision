package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Marek on 03.10.2016.
 */
@Entity
@Table(name = "METHODATTRIBUTES")
public class MethodAttributes implements Serializable, CustomEntity {

    @Id
    @Column(name = "METHODATTRIBUTE_ID")
    private String methodAttributesId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "METHOD_ID")
    private Method method;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATTRIBUTE_ID")
    private Attribute attribute;


    @Column(name = "ATTRIBUTE_TYPE")
    private AttributeType attributeType;

    //kdyz je -1 nebyla nastavena hodnota
    @Column(name = "DEFAULT_VALUE")
    private String defaultValues;
    //kdyz je -1 nebyla nastavena hodnota
    @Column(name = "MIN_VALUE")
    private double minValue;
    //kdyz je -1 nebyla nastavena hodnota
    @Column(name = "MAX_VALUE")
    private double maxValue;


    public MethodAttributes() {
        methodAttributesId = UUID.randomUUID().toString();
        minValue = -1;
        maxValue = -1;
        defaultValues = "-1";
    }


    //SETTERS

    public void setMethodAttributesId(String methodAttributesId) {
        this.methodAttributesId = methodAttributesId;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public void setDefaultValues(String defaultValues) {
        this.defaultValues = defaultValues;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    //GETTERS


    public String getMethodAttributesId() {
        return methodAttributesId;
    }

    public Method getMethod() {
        return method;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public String getDefaultValues() {
        return defaultValues;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}
