package it.dinfo.stlab.mappers;

import it.dinfo.stlab.dto.MunicipalityDTO;
import it.dinfo.stlab.model.Municipality;

import javax.enterprise.context.Dependent;

//@Dependent //serve se non è presente il file beans.xml
public class MunicipalityMapper {

    /* conversione Entity -> DTO */
    public MunicipalityDTO convert(Municipality m){
        if(m == null)
            return null;

        MunicipalityDTO dto = new MunicipalityDTO();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setLat(m.getLat());
        dto.setLon(m.getLon());

        return dto;
    }

    /* conversione DTO -> Entity */
    public Municipality transfer(MunicipalityDTO dto, Municipality m){
        if(dto == null)
            return null;
        if(m == null)
            return null;

        m.setId(dto.getId());
        m.setName(dto.getName());
        m.setLat(dto.getLat());
        m.setLon(dto.getLon());

        return m;
    }
}

