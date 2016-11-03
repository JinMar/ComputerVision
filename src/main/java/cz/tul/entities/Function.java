package cz.tul.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Bc. Marek Jindrák on 31.10.2016.
 */
@Entity
@Table(name = "function")
public class Function implements Serializable, CustomEntity {
    private static final Logger logger = LoggerFactory.getLogger(Function.class);
    @Id
    @Column(name = "FUNCTION_ID")
    private String functionId;

    @OneToMany(mappedBy = "function")
    private Set<Method> methods = new HashSet<>();

    @Column(name = "NAME")
    private String name;

    public Function() {
        functionId = UUID.randomUUID().toString();
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFunctionId() {
        return functionId;
    }

    public Set<Method> getMethods() {
        return methods;
    }
}
