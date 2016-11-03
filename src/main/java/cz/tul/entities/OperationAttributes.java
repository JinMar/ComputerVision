package cz.tul.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Bc. Marek Jindrák on 01.11.2016.
 */
@Entity
@Table(name = "operationAttributes")
public class OperationAttributes implements Serializable, CustomEntity {
    private static final Logger logger = LoggerFactory.getLogger(OperationAttributes.class);

    @Id
    @Column(name = "OPERATIONATTRIBUTES_ID")
    private String operationAttributesId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;

    @OneToMany(mappedBy = "operationAttributes")
    private Set<PartAttributeValue> partAttributeValues = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ATTRIBUTE_ID")
    private Attribute attribute;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "options", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "keymap")
    @Column(name = "valuemap")
    private Map<String, String> options;

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

    public OperationAttributes() {
        operationAttributesId = UUID.randomUUID().toString();
    }

    public String getOperationAttributesId() {
        return operationAttributesId;
    }

    public void setOperationAttributesId(String operationAttributesId) {
        this.operationAttributesId = operationAttributesId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Set<PartAttributeValue> getPartAttributeValues() {
        return partAttributeValues;
    }

    public void setPartAttributeValues(Set<PartAttributeValue> partAttributeValues) {
        this.partAttributeValues = partAttributeValues;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(String defaultValues) {
        this.defaultValues = defaultValues;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}

