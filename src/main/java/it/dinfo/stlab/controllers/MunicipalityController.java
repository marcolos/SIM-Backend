package it.dinfo.stlab.controllers;

import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.dto.MunicipalityDTO;
import it.dinfo.stlab.dto.SmartStationDTO;
import it.dinfo.stlab.mappers.MunicipalityMapper;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MunicipalityController {

    @Inject private MunicipalityDao municipalityDao;
    @Inject private MunicipalityMapper municipalityMapper;


    public List<MunicipalityDTO> getAll(){
        List<MunicipalityDTO> municipalityDTOS = new ArrayList<>();
        List<Municipality> municipalities = municipalityDao.findAll();

        for (Municipality municipality : municipalities) {
            MunicipalityDTO dto = municipalityMapper.convert(municipality);
            municipalityDTOS.add(dto);
        }
        return municipalityDTOS;
    }


    public List<MunicipalityDTO> getAllForAdmin(UserAccount user){

        List<MunicipalityDTO> municipalityDTOS = new ArrayList<>();
        List<Municipality> municipalities = municipalityDao.findAllForAdmin(user);

        for (Municipality municipality : municipalities) {
            MunicipalityDTO dto = municipalityMapper.convert(municipality);
            municipalityDTOS.add(dto);
        }
        return municipalityDTOS;
    }

    public MunicipalityDTO getById(String uuid){
        Municipality municipality = municipalityDao.findById(uuid);
        MunicipalityDTO dto = municipalityMapper.convert(municipality);
        return dto;
    }

    public String create(MunicipalityDTO dtoReceived){
        Municipality municipality = municipalityMapper.transfer(dtoReceived,new Municipality());
        municipality.setId(UUID.randomUUID().toString());
        municipalityDao.save(municipality);
        return municipality.getId();
    }

    public void update(String uuid, MunicipalityDTO dtoReceived){
        Municipality municipality = municipalityMapper.transfer(dtoReceived,new Municipality());
        municipality.setId(uuid);
        municipalityDao.update(municipality);
    }

    public void delete(String uuid){
        municipalityDao.delete(uuid);
    }
}
