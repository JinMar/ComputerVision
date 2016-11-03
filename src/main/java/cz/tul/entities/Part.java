package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Marek on 03.10.2016.
 */
@Entity
@Table(name = "part")
public class Part implements Serializable, CustomEntity {
    @Id
    @Column(name = "PART_ID")
    private String partId;

    @Column(name = "URL")
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHAIN_ID")
    private Chain chain;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "part")
    private Set<PartAttributeValue> partAttributeValues = new HashSet<>();

    @Column(name = "POSITION")
    private int position;

    @Column(name = "STATE")
    private String state;

    @Column(name = "functionId")
    private String functionId;

    @Column(name = "methodId")
    private String methodId;

    public Part() {
        partId = UUID.randomUUID().toString();
    }

    //SETTERS

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public void setPartAttributeValues(Set<PartAttributeValue> partAttributeValues) {
        this.partAttributeValues = partAttributeValues;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
    //GETTERS


    public String getUrl() {
        return url;
    }

    public String getPartId() {
        return partId;
    }

    public Chain getChain() {
        return chain;
    }

    public Set<PartAttributeValue> getPartAttributeValues() {
        return partAttributeValues;
    }

    public int getPosition() {
        return position;
    }

    public String getState() {
        return state;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFunctionId() {
        return functionId;
    }

    public String getMethodId() {
        return methodId;
    }
}
