package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    @OneToMany(mappedBy = "methodAttributes")
    private Set<PartAttributeValue> partAttributeValues = new HashSet<PartAttributeValue>();

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "options", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "keymap")
    @Column(name = "valuemap")
    private Map<String, String> options;

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

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public void setPartAttributeValues(Set<PartAttributeValue> partAttributeValues) {
        this.partAttributeValues = partAttributeValues;
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

    public Map<String, String> getOptions() {
        return options;
    }

    public Set<PartAttributeValue> getPartAttributeValues() {
        return partAttributeValues;
    }
}
