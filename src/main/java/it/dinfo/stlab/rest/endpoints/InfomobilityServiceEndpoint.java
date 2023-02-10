package it.dinfo.stlab.rest.endpoints;

import it.dinfo.stlab.controllers.InfomobilityServiceController;
import it.dinfo.stlab.dao.InfomobilityServiceDao;
import it.dinfo.stlab.dao.SmartStationDao;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.dto.*;
import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.MobilityType;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;
import it.dinfo.stlab.eai.train.dto.jackson.TrainsDeparturesResponseDTO;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


@Path("/infomobility-services")
public class InfomobilityServiceEndpoint {  //la classe identifica l'endpoint(/infomobility-services)

    @Inject private InfomobilityServiceController infomobilityServiceController;
    @Inject private SmartStationDao smartStationDao;
    @Inject private InfomobilityServiceDao infomobilityServiceDao;
    @Context private SecurityContext securityContext;
    @Inject private UserAccountDao userAccountDao;


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services
     * @ResponseBody: JSON contenente la lista di infomobility-services */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<InfomobilityServiceDTO> dtos = infomobilityServiceController.getAll();
        return Response.status(Response.Status.OK).entity(dtos).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services
     * @RequestBody: JSON contenente una rappresentazione di un infomobility-services da registrare all'interno del sistema SIM Backend
     * @ResponseBody: JSON contente una rappresentazione del nuovo infomobility-services, successivamente all'operazione di persistenza */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati ricevuti all’interno della Request
    @Produces({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati della Response
    @RolesAllowed("SUPER_ADMIN")
    public Response create(InfomobilityServiceDTO infomobilityServiceDTOReceived){
        String uuid = infomobilityServiceController.create(infomobilityServiceDTOReceived); //salvo nel db
        return this.getId(uuid); //faccio la query per verificare di averlo inserito
    }



    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/{uuid}
     * @PathParams: uuid: stringa in formato UUIDv4 riferita all'infomobility di interesse
     * @ResponseBody: JSON contenente l'infomobility service richiesto */
    @GET
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    //@RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response getId(@PathParam("uuid") String uuid) {
        InfomobilityServiceDTO dto = infomobilityServiceController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }



    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/{uuid}
     * @PathParams: uuid: stringa in formato UUIDv4 riferita all'infomobility di interesse
     * @RequestBody: JSON contenente una rappresentazione dell'infomobility service da modificare
     * @ResponseBody: JSON contenente l'infomobility service modificato */
    @PUT
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response update(@PathParam("uuid") String uuid, InfomobilityServiceDTO infomobilityDTOReceived) {
        infomobilityServiceController.update(uuid, infomobilityDTOReceived);
        InfomobilityServiceDTO dto = infomobilityServiceController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }



    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/{uuid}
     * @PathParams: uuid: stringa in formato UUIDv4 riferita all'infomobility di interesse
     * @ResponseBody: JSON contenente l'infomobility service cancellato */
    @DELETE
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response delete(@PathParam("uuid") String uuid) {
        InfomobilityServiceDTO dto = infomobilityServiceController.getById(uuid);
        infomobilityServiceController.delete(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/{uuid}?enable={en} dove {en} = true o false
     * @PathParams: uuid: stringa in formato UUIDv4 riferita all'infomobility di interesse
     * @QueryParams: enable: boolean
     * @ResponseBody: JSON contenente l'infomobility service che è stato ri-abilitata/disabilitata */
    @PATCH
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response enableDisable( @QueryParam("enable") Boolean enable, @PathParam("uuid") String uuid) {
        InfomobilityServiceDTO dto = infomobilityServiceController.enableDisable(uuid, enable);
        return Response.status(Response.Status.OK).entity(dto).build();
    }



    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/{uuid}/vehicles?ssuuid={ssuuid}
     * @PathParams: uuid: stringa in formato UUIDv4 riferita all'infomobility di interesse
     * @QueryParams: ssuuid: stringa in formato UUIDv4 riferita alla smart station di interesse
     * @ResponseBody: ritorna un documento JSON contenente un elenco di mezzi di mobilità disponibili per il service provider associato al servizio di infomobilità , nei pressi della SmartStation di riferimento */
    @GET
    @Path("/{uuid}/vehicles")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAvailableVehicles(@PathParam("uuid") String uuid , @QueryParam("ssuuid") String ssuuid ) throws IOException, JAXBException {
        SmartStation ss = smartStationDao.findById(ssuuid);
        InfomobilityServiceProvider isp = infomobilityServiceDao.findById(uuid);
        if(ss.getExternalPlaceId() == null || !isp.getMobilityTypes().contains(MobilityType.TRAIN))
            return Response.status(Response.Status.OK).entity(null).build();
        TrainsDeparturesResponseDTO trainsDeparturesResponseDTO = infomobilityServiceController.getAvailableVehicles(ss, isp);
        return Response.status(Response.Status.OK).entity(trainsDeparturesResponseDTO).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/associable-smart-stations/{ssUuid}
     * @PathParams: ssUuid
     * @ResponseBody: ritorna una lista di Isp che sono associabili per una determinata SS da un determinato ADMIN */
    @GET
    @Path("/associable-smart-stations/{ssUuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("ADMIN")
    public Response getAllIspAssociableForOneSsForOneAdmin(@PathParam("ssUuid") String ssId) {
        UserAccount user = userAccountDao.findByEmail(securityContext.getUserPrincipal().getName());
        SmartStation ss = smartStationDao.findById(ssId);
        List<InfomobilityServiceDTOLight> dtos = infomobilityServiceController.getAllIspAssociableForOneSsForOneAdmin(user, ss);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/getAllAuthorized
     * @ResponseBody: ritorna una lista di Isp che l'ADMIN può gestire */
    @GET
    @Path("/getAllAuthorized")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("ADMIN")
    public Response getAllAuthorizedIspForAdmin() {
        UserAccount user = userAccountDao.findByEmail(securityContext.getUserPrincipal().getName());
        List<InfomobilityServiceDTO> dtos = infomobilityServiceController.getAllAuthorizedIspForAdmin(user);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/infomobility-services/smart-station/{uuid}
     * @PathParams: uuid: id della smart-station
     * @ResponseBody: ritorna una lista di isp che sono presenti nella ss */
    @GET
    @Path("/smart-station/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllIspInOneSS(@PathParam("uuid") String ssId) {
        List<InfomobilityServiceDTO> dtos = infomobilityServiceController.getAllIspInOneSS(ssId);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }


}
