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
@Table(name = "PART")
public class Part implements Serializable, CustomEntity {
    @Id
    @Column(name = "PART_ID")
    private String partId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHAIN_ID")
    private Chain chain;

    @OneToMany(mappedBy = "part")
    private Set<PartAttributeValue> partAttributeValues = new HashSet<>();

    @Column(name = "POSITION")
    private int position;

    @Column(name = "STATE")
    private StateEnum state;

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

    public void setState(StateEnum state) {
        this.state = state;
    }

    //GETTERS


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

    public StateEnum getState() {
        return state;
    }
}
