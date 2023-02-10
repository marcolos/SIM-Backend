package it.dinfo.stlab.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SmartStation")
public class SmartStation {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    //Relazione NtoN bidirezionale (non si mette il mappedBy in nessuna classe e si mettono le joinColumns e inverseJoinColumns nelle due classi invertite
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable
        (
            name="SmartStation_InfomobilityServiceProvider",
            joinColumns= @JoinColumn(name="smart_station_id"),
            inverseJoinColumns= @JoinColumn(name="infomobility_service_provider_id")
        )
    //RELAZIONE 1toN (con @JoinColumn aggiunge campo in Infomobility, senza crea un altra tabella)
    /*@OneToMany(fetch=FetchType.EAGER)  // relazione tra SmartStation e Infomobility è 1toN monodirezionale
    @JoinColumn(name = "fk_smart_station")*/
    private List<InfomobilityServiceProvider> infomobilityServiceProviders;

    //per servizio esterno dei treni
    @Column(name = "external_place_id")
    private String externalPlaceId;

    //per servizio esterno dei dati ambientali
    @Column(name = "cmad_mac_address")
    private String cmadMacAddress;



    public SmartStation(){
        this.infomobilityServiceProviders = new ArrayList<>();
    }

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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public List<InfomobilityServiceProvider> getInfomobilityServiceProviders() {
        return infomobilityServiceProviders;
    }

    public void setInfomobilityServiceProviders(List<InfomobilityServiceProvider> infomobilityServiceProviders) {
        this.infomobilityServiceProviders = infomobilityServiceProviders;
    }

    public String getExternalPlaceId() {
        return externalPlaceId;
    }

    public void setExternalPlaceId(String externalPlaceId) {
        this.externalPlaceId = externalPlaceId;
    }

    public String getCmadMacAddress() {
        return cmadMacAddress;
    }

    public void setCmadMacAddress(String cmadMacAddress) {
        this.cmadMacAddress = cmadMacAddress;
    }
}
