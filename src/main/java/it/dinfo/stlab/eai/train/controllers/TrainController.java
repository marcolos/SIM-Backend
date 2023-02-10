package it.dinfo.stlab.eai.train.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.dinfo.stlab.eai.train.TrainsServicesInterface;
import it.dinfo.stlab.eai.train.dto.jackson.TrainsDeparturesResponseDTO;
import it.dinfo.stlab.eai.train.config.TrainServiceConfig;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

public class TrainController {

    public TrainsDeparturesResponseDTO getDepartureInfo(String placeId) throws IOException {
        String path = TrainServiceConfig.TRAIN_SERVICE_ENDPOINT;

        //si utilizza RESTEASY, ovvero l'implentazione della specifica. Quando usiamo questo si utilizza l'interfaccia TrainsServicesInterface
        /*ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(path));
        TrainsServicesInterface proxy = target.proxy(TrainsServicesInterface.class);
        String xml = proxy.departuresTrainByPlaceId(placeId);*/

        //Si utilizza la specifica
        Client client = ClientBuilder.newClient();
        String xml = client.target(path)
                .path("GetDepartures")
                .queryParam("PlaceId", placeId)
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        /*NB Volendo potremmo ottenere una Response invece che una Stringa semplicemente facendo .get(Response.class); e mettere il tipo Response.
        In questo modo avremmo già la Response pronta che basterà farla ritornare al nostro endpoint. Il parse da xml viene fatto automaticamente da jaxrs
        inserendo nell'endpoint le annotazioni @Produces({MediaType.APPLICATION_JSON}) e @Consumes({MediaType.APPLICATION_XML}).
        Io ho preferito fare un parse manuale grazie alla classe TrainsDeparturesResponseDTO, in modo da poter scartare alcuni campi che mi arrivano dal servizio esterno*/


        //JAXB
        /*JAXBContext jaxbContext = JAXBContext.newInstance(TrainsDeparturesResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TrainsDeparturesResponse trainsDeparturesResponse = (TrainsDeparturesResponse) jaxbUnmarshaller.unmarshal(new StringReader(xml));
        return trainsDeparturesResponse;*/

        //JACKSON
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        TrainsDeparturesResponseDTO trainsDeparturesResponseDTO = xmlMapper.readValue(xml, TrainsDeparturesResponseDTO.class);
        return trainsDeparturesResponseDTO;
    }
}
