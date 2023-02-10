package it.dinfo.stlab.controllers;

import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.dao.SmartStationDao;
import it.dinfo.stlab.dto.SmartStationDTO;
import it.dinfo.stlab.dto.SmartStationDTOLight;
import it.dinfo.stlab.mappers.SmartStationMapper;
import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Dependent //serve se non è presente il file beans.xml
public class SmartStationController {

    @Inject private SmartStationDao smartStationDao;
    @Inject private SmartStationMapper smartStationMapper;
    @Inject private MunicipalityDao municipalityDao;


    public List<SmartStationDTO> getAll(){
        List<SmartStationDTO> smartStationDTOs = new ArrayList<>();
        List<SmartStation> smartStations = smartStationDao.findAll();
        for (SmartStation smartStation : smartStations) {
            SmartStationDTO dto = smartStationMapper.convert(smartStation);
            smartStationDTOs.add(dto);
        }
        return smartStationDTOs;
    }


    public List<SmartStationDTO> getAllAuthorizedIspForAdmin(UserAccount user){
        List<SmartStationDTO> smartStationDTOs = new ArrayList<>();
        List<SmartStation> smartStations = smartStationDao.findAllAuthorizedSSForAdmin(user);
        for (SmartStation smartStation : smartStations) {
            SmartStationDTO dto = smartStationMapper.convert(smartStation);
            smartStationDTOs.add(dto);
        }
        return smartStationDTOs;
    }


    public SmartStationDTO getById(String uuid){
        SmartStation smartStation = smartStationDao.findById(uuid);
        SmartStationDTO dto = smartStationMapper.convert(smartStation);
        return dto;
    }

    public String create(SmartStationDTO dtoReceived){
        SmartStation smartStation = smartStationMapper.transfer(dtoReceived,new SmartStation());
        smartStation.setId(UUID.randomUUID().toString());
        smartStationDao.save(smartStation);
        return smartStation.getId();
    }

    public void update(String uuid, SmartStationDTO dtoReceived){
        SmartStation smartStation = smartStationMapper.transfer(dtoReceived,new SmartStation());
        smartStation.setId(uuid);
        smartStationDao.update(smartStation);
    }

    public void delete(String uuid){
        smartStationDao.delete(uuid);
    }

    public SmartStationDTO enableDisable(String uuid, Boolean enable){
        SmartStation ss = smartStationDao.findById(uuid);
        ss.setEnabled(enable);
        smartStationDao.update(ss);
        return smartStationMapper.convert(ss);
    }

    public List<SmartStationDTOLight> getAllSsAssociableForOneIspForOneAdmin(UserAccount user, InfomobilityServiceProvider isp){
        List<Municipality> municipalities = municipalityDao.findAllMunicipalityForAdminAuth(user, isp);
        List<SmartStationDTOLight> ssDTOLights = new ArrayList<>();
        List<SmartStation> smartStations = new ArrayList<>();
        for(Municipality m: municipalities){
            smartStations.addAll(smartStationDao.findAllSSForMunicipality(m));
        }
        for(SmartStation ss: smartStations){
            ssDTOLights.add(new SmartStationDTOLight(ss));
        }
        return ssDTOLights;
    }

    public List<SmartStationDTO> getAllSsByMunicipality(String municipalityId){
        List<SmartStationDTO> smartStationDTOs = new ArrayList<>();
        Municipality m = municipalityDao.findById(municipalityId);
        List<SmartStation> smartStations = smartStationDao.findAllSSForMunicipality(m);
        for (SmartStation smartStation : smartStations) {
            SmartStationDTO dto = smartStationMapper.convert(smartStation);
            smartStationDTOs.add(dto);
        }
        return smartStationDTOs;
    }

}
