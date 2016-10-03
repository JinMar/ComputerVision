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
@Table(name = "ATTRIBUTE")
public class Attribute implements Serializable, CustomEntity {
    @Id
    @Column(name = "ATTRIBUTE_ID")
    private String attributeId;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "attribute")
    private Set<MethodAttributes> methodAttributes = new HashSet<MethodAttributes>();

    public Attribute() {
        attributeId = UUID.randomUUID().toString();
    }

    //SETTERS

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethodAttributes(Set<MethodAttributes> methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    //GETTERS


    public String getAttributeId() {
        return attributeId;
    }

    public String getName() {
        return name;
    }

    public Set<MethodAttributes> getMethodAttributes() {
        return methodAttributes;
    }
}
