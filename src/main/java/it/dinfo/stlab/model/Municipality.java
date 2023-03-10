package it.dinfo.stlab.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Municipality")
public class Municipality {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    //utilizzo la bidirezionalit√† esclusimante per l'utilizzo del cascade, ie, per fare in modo che quando elimino una Municipality, si eliminino anche le righe corrispondenti della tabella AdminAuthorization
    @OneToMany(mappedBy = "municipality",cascade = {CascadeType.REMOVE})
    private List<AdminAuthorization> adminAuthorizations;

    public Municipality() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public List<AdminAuthorization> getAdminAuthorizations() {
        return adminAuthorizations;
    }

    public void setAdminAuthorizations(List<AdminAuthorization> adminAuthorizations) {
        this.adminAuthorizations = adminAuthorizations;
    }
}
