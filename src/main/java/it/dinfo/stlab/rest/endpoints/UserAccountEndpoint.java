package it.dinfo.stlab.rest.endpoints;

import it.dinfo.stlab.controllers.UserAccountController;
import it.dinfo.stlab.dto.UserAccountDTO;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/users")  //la classe identifica l'endpoint(/users)
@RolesAllowed("SUPER_ADMIN")
public class UserAccountEndpoint {

    @Inject
    private UserAccountController userAccountController;


    /**  //1° servizio dell'endpoint [LIST]
           verbo HTTP: GET
           URI: SIM_BACKEND_URI/users
           PathParams: null
           QueryParams: null
           RequestBody: null
           Response Body: ritorna un documento JSON contenente la lista degli utenti dotati di account */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<UserAccountDTO> dtos = userAccountController.getAll();
        return Response.status(Response.Status.OK).entity(dtos).build();
    }



    /**  //2° servizio dell'endpoint [CREATE]
            verbo HTTP: POST
            URI: SIM_BACKEND_URI/users
            PathParams: null
            QueryParams: null
            RequestBody: documento JSON contenente una rappresentazione di un account utente da registrare all'interno del sistema SIM Backend
            Response Body: documento JSON contente una rappresentazione del nuovo account utente, successivamente all'operazione di persistenza */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati ricevuti all’interno della Request
    @Produces({MediaType.APPLICATION_JSON})  //dichiaro il formato dei dati della Response
    public Response create(UserAccountDTO userAccountDTOReceived){
        String uuid = userAccountController.create(userAccountDTOReceived); //salvo nel db
        return this.getId(uuid); //faccio la query per verificare di averlo inserito
    }


    /**  //3° servizio dell'endpoint [RETRIEVE]
            verbo HTTP: GET
            URI: SIM_BACKEND_URI/users/{uuid}
            PathParams: uuid: stringa in formato UUIDv4
            QueryParams: null
            RequestBody: null
            Response Body: ritorna un documento JSON contenente l'utente richiesto */
    @GET
    @Path("/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getId(@PathParam("uuid") String uuid) {
        UserAccountDTO dto = userAccountController.getById(uuid);
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
    public Response update(@PathParam("uuid") String uuid, UserAccountDTO userAccountDTOReceived) {
        userAccountController.update(uuid, userAccountDTOReceived);
        UserAccountDTO dto = userAccountController.getById(uuid);
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
    public Response delete(@PathParam("uuid") String uuid) {
        UserAccountDTO dto = userAccountController.getById(uuid);
        userAccountController.delete(uuid);
        return Response.status(Response.Status.OK).entity(dto).build();
    }


}
