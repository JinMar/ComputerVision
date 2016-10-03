package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Marek on 03.10.2016.
 */
@Entity
@Table(name = "PART")
public class Part implements Serializable, CustomEntity {
    @Id
    @Column(name = "PART_ID")
    private String partId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHAIN_ID")
    private Chain chain;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "METHODATTRIBUTE_ID")
    private MethodAttributes methodAttributes;

    @Column(name = "POSITION")
    private int position;

    @Column(name = "CURRENTVALUE")
    private String currentValue;

    @Column(name = "DEFAUELT_VALUE")
    private String defaultValue;

    @Column(name = "MAX_VALUE")
    private double maxValue;

    @Column(name = "MIN_VALUe")
    private double minValue;

    public Part() {
        partId = UUID.randomUUID().toString();
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public Chain getChain() {
        return chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public MethodAttributes getMethodAttributes() {
        return methodAttributes;
    }

    public void setMethodAttributes(MethodAttributes methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultVAlue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }
}
