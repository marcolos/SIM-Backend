package it.dinfo.stlab.rest.endpoints;

import it.dinfo.stlab.controllers.SmartStationController;
import it.dinfo.stlab.dao.InfomobilityServiceDao;
import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.dao.SmartStationDao;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.dto.SmartStationDTO;
import it.dinfo.stlab.dto.SmartStationDTOLight;
import it.dinfo.stlab.eai.ambientaldata.controllers.AmbientalDataController;
import it.dinfo.stlab.eai.ambientaldata.dto.AmbientalDataDTO;
import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.List;


@Path("/smart-stations")
public class SmartStationEndpoint {  //la classe identifica l'endpoint(/infomobility-services)

    @Inject
    private SmartStationDao smartStationDao;

    @Inject
    private SmartStationController smartStationController;

    @Inject
    private UserAccountDao userAccountDao;

    @Inject
    private InfomobilityServiceDao ispDao;

    @Inject
    private AmbientalDataController ambientalDataController;

    @Context
    private SecurityContext securityContext;


    /**
           URI: SIM_BACKEND_URI/smart-stations
           Response Body: JSON contenente la lista delle stazioni intelligenti */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<SmartStationDTO> dtos = smartStationController.getAll();
        return Response.status(Response.Status.OK).entity(dtos).build();
    }



    /**
            URI: SIM_BACKEND_URI/smart-stations
            RequestBody: JSON contenente una rappresentazione di una smart station da registrare all'interno del sistema SIM Backend
            Response Body: JSON contenente una rappresentazione della nuova smart station, successivamente all'operazione di persistenza */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati ricevuti all’interno della Request
    @Produces({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati della Response
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response create(SmartStationDTO smartStationDTOReceived){
        String uuid = smartStationController.create(smartStationDTOReceived); //salvo nel db
        return this.getId(uuid); //faccio la query per verificare di averlo inserito
    }

    /**
            URI: SIM_BACKEND_URI/smart-stations/{uuid}
            PathParams: uuid = stringa in formato UUIDv4 della smart-station di interesse
            Response Body: JSON contenente la smart-station richiesta */
    @GET
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    //@RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response getId(@PathParam("uuid") String uuid) {
        SmartStationDTO dto = smartStationController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**
            URI: SIM_BACKEND_URI/smart-stations/{uuid}
            PathParams: uuid: stringa in formato UUIDv4 della smart-station di interesse
            RequestBody: JSON contenente una rappresentazione della smart station da modificare
            Response Body: JSON contenente la smart station modificata */
    @PUT
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response update(@PathParam("uuid") String uuid, SmartStationDTO smartStationDTOReceived) {
        smartStationController.update(uuid, smartStationDTOReceived);
        SmartStationDTO dto = smartStationController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }


    /**
            URI: SIM_BACKEND_URI/smart-stations/{uuid}
            PathParams: uuid: stringa in formato UUIDv4 della smart-station di interesse
            RequestBody: JSON contenente una rappresentazione della smart station da cancellare
            Response Body: JSON contenente la smart station cancellata */
    @DELETE
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response delete(@PathParam("uuid") String uuid) {
        SmartStationDTO dto = smartStationController.getById(uuid); //salvo il dto primo della cancellazione in modo da poterlo inviare in risposta
        smartStationController.delete(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }


    /**
             URI: SIM_BACKEND_URI/smart-stations/{uuid}?enable={en}
             PathParams: uuid: stringa in formato UUIDv4 della smart-station di interesse
             QueryParams: enable: boolean
             Response Body: JSON contenente la smart station che è stata ri-abilitata/disabilitata */
    @PATCH
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response enableDisable( @QueryParam("enable") Boolean enable, @PathParam("uuid") String uuid) {
        SmartStationDTO dto = smartStationController.enableDisable(uuid, enable);
        return Response.status(Response.Status.OK).entity(dto).build();
    }



    /**
     * @URI: SIM_BACKEND_URI/smart-stations/associable-infomobility-services/{ispUuid}
     * @PathParams: ispUuid: id dell'isp
     * @ResponseBody: ritorna una lista di Ss che sono associabili per una determinata Isp da un determinato ADMIN
     */
    @GET
    @Path("/associable-infomobility-services/{ispUuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("ADMIN")
    public Response getAllSsAssociableForOneIspForOneAdmin(@PathParam("ispUuid") String ispId) {
        UserAccount user = userAccountDao.findByEmail(securityContext.getUserPrincipal().getName());
        InfomobilityServiceProvider isp = ispDao.findById(ispId);
        List<SmartStationDTOLight> dtos = smartStationController.getAllSsAssociableForOneIspForOneAdmin(user, isp);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }


    /**
     * @URI: SIM_BACKEND_URI/smart-stations/getAllAuthorized
     * @ResponseBody: ritorna una lista di SS che l'ADMIN può gestire
     * */
    @GET
    @Path("/getAllAuthorized")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("ADMIN")
    public Response getAllAuthorizedSSForAdmin() {
        UserAccount user = userAccountDao.findByEmail(securityContext.getUserPrincipal().getName());
        List<SmartStationDTO> dtos = smartStationController.getAllAuthorizedIspForAdmin(user);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/smart-stations/municipality/{uuid}
     * @PathParams: uuid: id della municipality
     * @ResponseBody: ritorna una lista di SS che sono presenti nella municipality
     * */
    @GET
    @Path("/municipality/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllSsByMunicipality(@PathParam("uuid") String municipalityId) {
        List<SmartStationDTO> dtos = smartStationController.getAllSsByMunicipality(municipalityId);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/smart-stations/ambientalData/{uuid}
     * @PathParams: uuid: id della smart-stations
     * @ResponseBody: ritorna JSON contenente dati ambientali della ss selezionata
     * */
    @GET
    @Path("/ambientalData/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAmbientalDataForSS(@PathParam("uuid") String ssId) throws IOException {
        SmartStation ss = smartStationDao.findById(ssId);
        AmbientalDataDTO dto = ambientalDataController.getAmbientalDataInfo(ss.getCmadMacAddress());
        return Response.status(Response.Status.OK).entity(dto).build();
    }


}
