package it.dinfo.stlab.dto;

import it.dinfo.stlab.model.InfomobilityServiceProvider;

public class InfomobilityServiceDTOLight {

    private String id;
    private String name;

    public InfomobilityServiceDTOLight(){}

    public InfomobilityServiceDTOLight(InfomobilityServiceProvider isp){
        this.id = isp.getId();
        this.name = isp.getName();
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

}
