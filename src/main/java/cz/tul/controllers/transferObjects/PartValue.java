package cz.tul.controllers.transferObjects;

import cz.tul.provisioner.holder.DataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 26.10.2016.
 */
public class PartValue extends DataHolder {
    private static final Logger logger = LoggerFactory.getLogger(PartValue.class);
    private String value;
    private double min;
    private double max;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
