package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
@Entity
@Table(name = "partattributevalue")
public class PartAttributeValue implements Serializable, CustomEntity {
    @Id
    @Column(name = "PARTATTRIBUTEVALUE_ID")
    private String partAttributeValueId;
    @Lob
    @Column(name = "VALUE")
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PART_ID")
    private Part part;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "METHODATTRIBUTE_ID")
    private MethodAttributes methodAttributes;

    public PartAttributeValue() {
        partAttributeValueId = UUID.randomUUID().toString();
    }

    //SETTERS

    public void setPartAttributeValueId(String partAttributeValueId) {
        this.partAttributeValueId = partAttributeValueId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setMethodAttributes(MethodAttributes methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    //GETTERS


    public String getPartAttributeValueId() {
        return partAttributeValueId;
    }

    public String getValue() {
        return value;
    }

    public Part getPart() {
        return part;
    }

    public MethodAttributes getMethodAttributes() {
        return methodAttributes;
    }
}
