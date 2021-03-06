package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Marek on 02.10.2016.
 */
@Entity
@Table(name = "attribute")
public class Attribute implements Serializable, CustomEntity {
    @Id
    @Column(name = "ATTRIBUTE_ID")
    private String attributeId;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "attribute")
    private Set<OperationAttributes> operationAttributes = new HashSet<>();

    public Attribute() {
        attributeId = UUID.randomUUID().toString();
    }

    public Attribute(String name) {
        attributeId = UUID.randomUUID().toString();
        this.name = name;
    }


    //SETTERS


    public void setOperationAttributes(Set<OperationAttributes> operationAttributes) {
        this.operationAttributes = operationAttributes;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public void setName(String name) {
        this.name = name;
    }


    //GETTERS


    public Set<OperationAttributes> getOperationAttributes() {
        return operationAttributes;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public String getName() {
        return name;
    }


}
