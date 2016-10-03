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
@Table(name = "ATRIBUTE")
public class Atribute implements Serializable, CustomEntity {
    @Id
    @Column(name = "ATRIBUTE_ID")
    private String atributeId;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "atribute")
    private Set<MethodAttributes> methodAtributes = new HashSet<MethodAttributes>();

    public Atribute() {
        atributeId = UUID.randomUUID().toString();
    }

    public Set<MethodAttributes> getMethodAtributes() {
        return methodAtributes;
    }

    public void setMethodAtributes(Set<MethodAttributes> methodAtributes) {
        this.methodAtributes = methodAtributes;
    }

    public String getAtributeId() {
        return atributeId;
    }

    public void setAtributeId(String atributeId) {
        this.atributeId = atributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
