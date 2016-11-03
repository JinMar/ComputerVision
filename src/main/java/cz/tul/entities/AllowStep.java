package cz.tul.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Bc. Marek Jindrák on 30.10.2016.
 */
@Entity
@Table(name = "allowstep")
public class AllowStep implements Serializable, CustomEntity {
    @Id
    @Column(name = "allowstep_id")
    private String allowStepId;

    @Column(name = "allowoperation_Id")
    private String allowoperationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;


    public AllowStep() {
        allowStepId = UUID.randomUUID().toString();

    }

    public String getAllowStepId() {
        return allowStepId;
    }

    public void setAllowStepId(String allowStepId) {
        this.allowStepId = allowStepId;
    }

    public String getAllowoperationId() {
        return allowoperationId;
    }

    public void setAllowoperationId(String allowoperationId) {
        this.allowoperationId = allowoperationId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
