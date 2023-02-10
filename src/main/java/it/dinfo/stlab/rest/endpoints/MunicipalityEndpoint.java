package it.dinfo.stlab.rest.endpoints;

import it.dinfo.stlab.controllers.MunicipalityController;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.dto.MunicipalityDTO;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.UserAccount;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/municipality")  //la classe identifica l'endpoint(/municipality)
public class MunicipalityEndpoint {

    @Inject private MunicipalityController municipalityController;

    @Inject private UserAccountDao userAccountDao;

    @Context SecurityContext securityContext;

    /**  //1° servizio dell'endpoint [LIST]
     verbo HTTP: GET
     URI: SIM_BACKEND_URI/municipality
     PathParams: null
     QueryParams: null
     RequestBody: null
     Response Body: ritorna un documento JSON contenente la lista delle municipality */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<MunicipalityDTO> dtos = municipalityController.getAll();
        return Response.status(Response.Status.OK).entity(dtos).build();
    }



    /**  //2° servizio dell'endpoint [CREATE]
     verbo HTTP: POST
     URI: SIM_BACKEND_URI/municipality
     PathParams: null
     QueryParams: null
     RequestBody: documento JSON contenente una rappresentazione di un municipality da registrare all'interno del sistema SIM Backend
     Response Body: documento JSON contente una rappresentazione del nuovo municipality, successivamente all'operazione di persistenza */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati ricevuti all’interno della Request
    @Produces({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati della Response
    @RolesAllowed("SUPER_ADMIN")
    public Response create(MunicipalityDTO municipalityDTOReceived){
        String uuid = municipalityController.create(municipalityDTOReceived); //salvo nel db
        return this.getId(uuid); //faccio la query per verificare di averlo inserito
    }

    /**  //3° servizio dell'endpoint [RETRIEVE]
     verbo HTTP: GET
     URI: SIM_BACKEND_URI/municipality/{uuid}
     PathParams: uuid: stringa in formato UUIDv4
     QueryParams: null
     RequestBody: null
     Response Body: ritorna un documento JSON contenente l'utente richiesto */
    @GET
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    //@RolesAllowed({"SUPER_ADMIN","ADMIN"})
    public Response getId(@PathParam("uuid") String uuid) {
        MunicipalityDTO dto = municipalityController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**  //4° servizio dell'endpoint [UPDATE]
     verbo HTTP: PUT
     URI: SIM_BACKEND_URI/users/{uuid}
     PathParams: uuid: stringa in formato UUIDv4
     QueryParams: null
     RequestBody: documento JSON contenente una rappresentazione dell'account utente da modificare
     Response Body: ritorna un documento JSON contenente l'utente modificato */
    @PUT
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("SUPER_ADMIN")
    public Response update(@PathParam("uuid") String uuid, MunicipalityDTO municipalityDTOReceived) {
        municipalityController.update(uuid, municipalityDTOReceived);
        MunicipalityDTO dto = municipalityController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**  //5° servizio dell'endpoint [DELETE]
     verbo HTTP: DELETE
     URI: SIM_BACKEND_URI/users/{uuid}
     PathParams: uuid: stringa in formato UUIDv4
     QueryParams: null
     RequestBody: documento JSON contenente una rappresentazione dell'account utente da cancellare
     Response Body: ritorna un documento JSON contenente l'utente cancellato */
    @DELETE
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("SUPER_ADMIN")
    public Response delete(@PathParam("uuid") String uuid) {
        MunicipalityDTO dto = municipalityController.getById(uuid);
        municipalityController.delete(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("/getAllAuthorized")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("ADMIN")
    public Response getAllAuthMunicipalityForAdmin() {
        UserAccount user = userAccountDao.findByEmail(securityContext.getUserPrincipal().getName());
        List<MunicipalityDTO> dtos = municipalityController.getAllForAdmin(user);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }
}
