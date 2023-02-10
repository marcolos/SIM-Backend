package it.dinfo.stlab.eai.train;

//import it.dinfo.stlab.client.train.jaxb.ArrivalsResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

//si usa solo quando si usa RESTEASY, ovvero l'implentazione della specifica, nel controller
public interface TrainsServicesInterface {

    @GET
    @Path("/GetArrivals")
    @Produces(MediaType.APPLICATION_XML)
    String arrivalsTrainByPlaceId(@QueryParam("PlaceId") String placeId);

    @GET
    @Path("/GetDepartures")
    @Produces(MediaType.APPLICATION_XML)
    String departuresTrainByPlaceId(@QueryParam("PlaceId") String placeId);

    @GET
    @Path("/GetPlaces")
    @Produces(MediaType.APPLICATION_XML)
    String getPlaces(@QueryParam("PlaceId") String placeId);

}