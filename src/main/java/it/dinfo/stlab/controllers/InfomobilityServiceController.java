package it.dinfo.stlab.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.dinfo.stlab.eai.train.config.TrainServiceConfig;
import it.dinfo.stlab.dao.InfomobilityServiceDao;
import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.dao.SmartStationDao;
import it.dinfo.stlab.dto.InfomobilityServiceDTO;
import it.dinfo.stlab.dto.InfomobilityServiceDTOLight;
import it.dinfo.stlab.eai.train.controllers.TrainController;
import it.dinfo.stlab.mappers.InfomobilityServiceMapper;
import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;
import it.dinfo.stlab.eai.train.TrainsServicesInterface;
import it.dinfo.stlab.eai.train.dto.jackson.TrainsDeparturesResponseDTO;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//per JAXB
//import it.dinfo.stlab.client.train.jaxb.TrainsDeparturesResponseDTO;


//@Dependent //serve se non è presente il file beans.xml
public class InfomobilityServiceController {

    @Inject private InfomobilityServiceDao infomobilityServiceDao;
    @Inject private SmartStationDao smartStationDao;
    @Inject private InfomobilityServiceMapper infomobilityServiceMapper;
    @Inject private TrainController trainController;


    public List<InfomobilityServiceDTO> getAll(){
        List<InfomobilityServiceDTO> infomobilityServiceDTOs = new ArrayList<>();
        List<InfomobilityServiceProvider> infomobilityServiceProviders = infomobilityServiceDao.findAll();
        for (InfomobilityServiceProvider infomobilityServiceProvider : infomobilityServiceProviders) {
            InfomobilityServiceDTO dto = infomobilityServiceMapper.convert(infomobilityServiceProvider);
            infomobilityServiceDTOs.add(dto);
        }
        return infomobilityServiceDTOs;
    }

    public List<InfomobilityServiceDTO> getAllAuthorizedIspForAdmin(UserAccount user){
        List<InfomobilityServiceDTO> infomobilityServiceDTOs = new ArrayList<>();
        List<InfomobilityServiceProvider> isps = infomobilityServiceDao.findAllAuthorizedIspForAdmin(user);
        for (InfomobilityServiceProvider isp : isps) {
            InfomobilityServiceDTO dto = infomobilityServiceMapper.convert(isp);
            infomobilityServiceDTOs.add(dto);
        }
        return infomobilityServiceDTOs;
    }

    public InfomobilityServiceDTO getById(String uuid){
        InfomobilityServiceProvider infomobilityServiceProvider = infomobilityServiceDao.findById(uuid);
        InfomobilityServiceDTO dto = infomobilityServiceMapper.convert(infomobilityServiceProvider);
        return dto;
    }

    public String create(InfomobilityServiceDTO dtoReceived){
        InfomobilityServiceProvider isp = infomobilityServiceMapper.transfer(dtoReceived, new InfomobilityServiceProvider());
        isp.setId(UUID.randomUUID().toString());
        infomobilityServiceDao.save(isp);
        return isp.getId();
    }

    public void update(String uuid, InfomobilityServiceDTO dtoReceived){
        InfomobilityServiceProvider isp = infomobilityServiceMapper.transfer(dtoReceived, new InfomobilityServiceProvider());
        isp.setId(uuid);
        infomobilityServiceDao.update(isp);
    }

    public void delete(String uuid){
        infomobilityServiceDao.delete(uuid);

    }

    public InfomobilityServiceDTO enableDisable(String uuid, Boolean enable){
        InfomobilityServiceProvider isp = infomobilityServiceDao.findById(uuid);
        isp.setEnabled(enable);
        infomobilityServiceDao.update(isp);
        return infomobilityServiceMapper.convert(isp);
    }

    public List<String> getAvailableVehicles(String uuid, String ssuuid){
        return infomobilityServiceDao.findAvailableVehicles(uuid, ssuuid);
    }

    public List<InfomobilityServiceDTOLight> getAllIspAssociableForOneSsForOneAdmin(UserAccount user, SmartStation ss){
        Municipality municipality = ss.getMunicipality();
        List<InfomobilityServiceProvider> isps = infomobilityServiceDao.findAllIspForMunicipalityAuthorizationForAdmin(user, municipality);
        List<InfomobilityServiceDTOLight> ispDTOLights = new ArrayList<>();

        for (InfomobilityServiceProvider isp : isps) {
            ispDTOLights.add(new InfomobilityServiceDTOLight(isp));
        }

        return ispDTOLights;
    }

    public TrainsDeparturesResponseDTO getAvailableVehicles(SmartStation ss, InfomobilityServiceProvider isp) throws IOException {
        //TRENI
        TrainsDeparturesResponseDTO trainsDeparturesResponseDTO = trainController.getDepartureInfo(ss.getExternalPlaceId());
        return trainsDeparturesResponseDTO;
        //potrei avere altro in fututo, ad esempio METRO,BUS ecc e potrei ritornate una dto che contiene tutti i dto dei servizi
    }



    public List<InfomobilityServiceDTO> getAllIspInOneSS(String ssId){
        SmartStation ss = smartStationDao.findById(ssId);
        List<InfomobilityServiceProvider> isps = ss.getInfomobilityServiceProviders();
        List<InfomobilityServiceDTO> infomobilityServiceDTOs = new ArrayList<>();
        for (InfomobilityServiceProvider isp : isps) {
            InfomobilityServiceDTO dto = infomobilityServiceMapper.convert(isp);
            infomobilityServiceDTOs.add(dto);
        }
        return infomobilityServiceDTOs;
    }


}
