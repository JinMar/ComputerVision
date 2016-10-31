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

    @Id
    @Column(name = "param")
    private String param;


    @JoinColumn(name = "method_id")
    private String method;

    @Column(name = "allowstep")
    private String allowStep;

    @Column(name = "selfRelation")
    private boolean selfRelation;

    @Column(name = "selfParam")
    private String selfParam;

    public AllowStep() {
        allowStepId = UUID.randomUUID().toString();
        selfRelation = false;
    }

    public AllowStep(String param) {
        allowStepId = UUID.randomUUID().toString();
        selfRelation = false;
        this.param = param;

    }

    public String getAllowStepId() {
        return allowStepId;
    }

    public void setAllowStepId(String allowStepId) {
        this.allowStepId = allowStepId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAllowStep() {
        return allowStep;
    }

    public void setAllowStep(String allowStep) {
        this.allowStep = allowStep;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isSelfRelation() {
        return selfRelation;
    }

    public void setSelfRelation(boolean selfRelation) {
        this.selfRelation = selfRelation;
    }

    public String isSelfParam() {
        return selfParam;
    }

    public void setSelfParam(String selfParam) {
        this.selfParam = selfParam;
    }
}
