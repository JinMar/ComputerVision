package cz.tul.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Bc. Marek Jindrák on 01.11.2016.
 */
@Entity
@Table(name = "operation")
public class Operation implements Serializable, CustomEntity {
    private static final Logger logger = LoggerFactory.getLogger(Operation.class);

    @Id
    @Column(name = "OPERATION_ID")
    private String operationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "METHOD_ID")
    private Method method;

    @OneToMany(mappedBy = "operation")
    private Set<OperationAttributes> operationAttributes = new HashSet<>();

    @OneToMany(mappedBy = "operation")
    private Set<AllowStep> allowSteps = new HashSet<>();


    @Column(name = "NAME")
    private String name;

    public Operation() {
        operationId = UUID.randomUUID().toString();
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OperationAttributes> getOperationAttributes() {
        return operationAttributes;
    }

    public void setOperationAttributes(Set<OperationAttributes> operationAttributes) {
        this.operationAttributes = operationAttributes;
    }

    public String getOperationId() {
        return operationId;
    }


    public String getName() {
        return name;
    }

    public Set<AllowStep> getAllowSteps() {
        return allowSteps;
    }

    public void setAllowSteps(Set<AllowStep> allowSteps) {
        this.allowSteps = allowSteps;
    }


}
