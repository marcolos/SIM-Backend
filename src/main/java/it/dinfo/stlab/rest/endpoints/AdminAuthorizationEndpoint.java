package it.dinfo.stlab.rest.endpoints;


import it.dinfo.stlab.controllers.AdminAuthorizationController;
import it.dinfo.stlab.dto.AdminAuthorizationDTO;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/adminAuthorization")  //la classe identifica l'endpoint(/adminAuth)
@RolesAllowed("SUPER_ADMIN")
public class AdminAuthorizationEndpoint {

    @Inject
    private AdminAuthorizationController adminAuthController;


    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization
     * @ResponseBody: ritorna un documento JSON contenente la lista delle adminAuth
     * */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<AdminAuthorizationDTO> dtos = adminAuthController.getAll();
        return Response.status(Response.Status.OK).entity(dtos).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization
     * @RequestBody: documento JSON contenente una rappresentazione di un adminAuth da registrare all'interno del sistema SIM Backend
     * @ResponseBody: documento JSON contente una rappresentazione del nuovo adminAuth, successivamente all'operazione di persistenza
     * */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati ricevuti all’interno della Request
    @Produces({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati della Response
    public Response create(AdminAuthorizationDTO adminAuthDTOReceived){
        String uuid = adminAuthController.create(adminAuthDTOReceived); //salvo nel db
        return this.getId(uuid); //faccio la query per verificare di averlo inserito
    }

    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization/{uuid}
     * @PathParams: : uuid: stringa in formato UUIDv4 che rappresenta l'id dell'adminAuth
     * @ResponseBody: documento JSON contente l'adminAuth richiesto
     * */
    @GET
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getId(@PathParam("uuid") String uuid) {
        AdminAuthorizationDTO dto = adminAuthController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization/{uuid}
     * @PathParams: : uuid: stringa in formato UUIDv4 che rappresenta l'id dell'adminAuth
     * @RequestBody: documento JSON contenente una rappresentazione dell'adminAuth da modificare
     * @ResponseBody: documento JSON contente l'adminAuth modificato
     * */
    @PUT
    @Path("/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("uuid") String uuid, AdminAuthorizationDTO adminAuthDTOReceived) {
        adminAuthController.update(uuid, adminAuthDTOReceived);
        AdminAuthorizationDTO dto = adminAuthController.getById(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization/{uuid}
     * @PathParams: : uuid: stringa in formato UUIDv4 che rappresenta l'id dell'adminAuth
     * @ResponseBody: documento JSON contente l'adminAuth eliminato
     * */
    @DELETE
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("uuid") String uuid) {
        AdminAuthorizationDTO dto = adminAuthController.getById(uuid);
        adminAuthController.delete(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    /**
     * @URI: SIM_BACKEND_URI/adminAuthorization/user/{uuid}
     * @PathParams: : uuid: stringa in formato UUIDv4 che rappresenta l'id dell'user
     * @ResponseBody: ritorna un json contente tutte le authorization per un user
     * */
    @GET
    @Path("/user/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllAuthForOneUser(@PathParam("uuid") String uuid) {
        List<AdminAuthorizationDTO> dtos = adminAuthController.getAllAuthForOneUser(uuid);
        return Response.status(Response.Status.OK).entity(dtos).build();
    }
}
