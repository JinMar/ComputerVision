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
@Table(name = "METHOD")
public class Method implements Serializable, CustomEntity {
    @Id
    @Column(name = "METHOD_ID")
    private String methodId;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "method")
    private Set<MethodAttributes> methodAttributes = new HashSet<MethodAttributes>();

    public Method() {
        methodId = UUID.randomUUID().toString();
    }

    //SETTERS

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethodAttributes(Set<MethodAttributes> methodAttributes) {
        this.methodAttributes = methodAttributes;
    }

    //GETTERS


    public String getMethodId() {
        return methodId;
    }

    public String getName() {
        return name;
    }

    public Set<MethodAttributes> getMethodAttributes() {
        return methodAttributes;
    }
}
