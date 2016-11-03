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
@Table(name = "method")
public class Method implements Serializable, CustomEntity {
    @Id
    @Column(name = "METHOD_ID")
    private String methodId;

    @Column(name = "NAME")
    private String name;


    @OneToMany(mappedBy = "method")
    private Set<Operation> operations = new HashSet<Operation>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FUNCTION_ID")
    private Function function;


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


    public void setFunction(Function function) {
        this.function = function;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
    //GETTERS


    public Set<Operation> getOperations() {
        return operations;
    }

    public Function getFunction() {
        return function;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getName() {
        return name;
    }

}
