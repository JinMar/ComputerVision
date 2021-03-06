package cz.tul.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Marek Jindrak on 02.10.2016.
 */
@Entity
@Table(name = "chain")
public class Chain implements Serializable, CustomEntity {
    @Id
    @Column(name = "CHAIN_ID")
    private String chainId;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "STATE")
    private String state;

    @Column(name = "MESSAGE")
    private String message;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chain")
    private Set<Part> chainParts = new HashSet<>();

    public Chain() {
        chainId = UUID.randomUUID().toString();
    }

    //SETTERS

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setChainParts(Set<Part> chainParts) {
        this.chainParts = chainParts;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //GETTERS

    public String getChainId() {
        return chainId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Set<Part> getChainParts() {
        return chainParts;
    }

    public String getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
