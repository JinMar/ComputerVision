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
    @JoinColumn(name = "METHOD_ID")
    private Method method;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "part")
    private Set<PartAttributeValue> partAttributeValues = new HashSet<>();

    @Column(name = "POSITION")
    private int position;

    @Column(name = "STATE")
    private String state;

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

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Method getMethod() {
        return method;
    }
}
