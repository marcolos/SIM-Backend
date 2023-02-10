package it.dinfo.stlab.eai.ambientaldata.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import it.dinfo.stlab.eai.ambientaldata.config.AmbientalDataConfig;
import it.dinfo.stlab.eai.ambientaldata.dto.AmbientalDataDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class AmbientalDataController {

    public AmbientalDataDTO getAmbientalDataInfo(String cmadMacAddress) throws IOException {
        String path = AmbientalDataConfig.AMBIENTALDATA_SERVICE_ENDPOINT;

        //Si utilizza la specifica
        Client client = ClientBuilder.newClient();
        String xml = client.target(path)
                .path(cmadMacAddress)
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        //JACKSON
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AmbientalDataDTO ambientalDataDTO = xmlMapper.readValue(xml, AmbientalDataDTO.class);
        return ambientalDataDTO;
    }
}
