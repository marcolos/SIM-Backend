package it.dinfo.stlab.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
//import it.dinfo.stlab.filter.MySecurityContext;
import it.dinfo.stlab.config.Config;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.model.UserAccount;
import it.dinfo.stlab.model.UserRole;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class RequestFilterWithJWT implements ContainerRequestFilter {

    //@Inject
    //private KeyGenerator keyGenerator;

    @Context
    UriInfo uriInfo;

    @Inject
    UserAccountDao userAccountDao;



    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header, uri path and http method from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String uriPath = requestContext.getUriInfo().getPath();
        String httpMethod = requestContext.getMethod();

        //PUBLIC SERVICE
        if(uriPath.startsWith("/auth")){
            if(StringUtils.isAllBlank(authorizationHeader) || authorizationHeader.equals("Bearer null")) //se il campo Authorization dell'header è vuoto
                return;  //return e quindi si salta il filtro
            else   // quando siamo gia loggati(abbiamo qualcosa nel campo Authorization)
                requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        }
        if(uriPath.startsWith("/infomobility-services/") && uriPath.contains("/vehicles")){
            return;  //non applico il filtro per il servizio getAllAvailableVehicles. Roba della home
        }
        if(uriPath.equals("/municipality") && httpMethod.equals("GET")){
            return;  //non applico il filtro per la getAll delle municipality. Roba anche della home
        }
        if(uriPath.contains("/municipality/") && httpMethod.equals("GET")){
            return;  //apro tutte le get di municipality
        }
        if(uriPath.equals("/smart-stations") && httpMethod.equals("GET")){
            return;  //non applico il filtro per la getAll delle smart-stations. Roba anche della home
        }
        if(uriPath.contains("/smart-stations/") && httpMethod.equals("GET")){
            return;  //apro tutte le get di smart-station
        }
        if(uriPath.equals("/infomobility-services") && httpMethod.equals("GET")){
            return;  //non applico il filtro per la getAll delle infomobility-services. Roba anche della home
        }
        if(uriPath.contains("/infomobility-services/") && httpMethod.equals("GET")){
            return;  //apro tutte le get di infomobility
        }
        if(uriPath.contains("/smart-stations/municipality") && httpMethod.equals("GET")){
            return;  //non applico il filtro. Roba della home
        }
        if(uriPath.contains("/infomobility-services/smart-station") && httpMethod.equals("GET")){
            return;  //non applico il filtro. Roba della home
        }
        if(uriPath.contains("/smart-stations/ambientalData") && httpMethod.equals("GET")){
            return;  //non applico il filtro
        }
        /*if(uriPath.startsWith("/altro/path/che/non/deve/essere/protetto"))
            return;*/


        // AUTHENTICATED SERVICE
        String principalEmail;
        if(authorizationHeader != null) {

            String token = authorizationHeader.substring("Bearer".length()).trim();
            try {
                Algorithm algorithm = Algorithm.HMAC256(Config.JTW_TOKEN_KEY);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
                principalEmail = jwt.getClaim("email").asString();
                //requestContext.setSecurityContext(new MySecurityContext(principalEmail, requestContext, userAccountDao));
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return new Principal() {
                            @Override
                            public String getName() {
                                return principalEmail;
                            }
                        };
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        UserAccount principal = userAccountDao.findByEmail(principalEmail);
                        if(principal == null)
                            return false;

                        if(!principal.isInRole(UserRole.valueOf(role)))
                            return false;

                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return requestContext.getSecurityContext().isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return SecurityContext.BASIC_AUTH;
                    }
                });

            } catch (JWTVerificationException exception){
                //Invalid signature/claims
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }


        }
        else{
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }





        /*// Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();


        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);

        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }*/
    }
}
