package it.dinfo.stlab.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import it.dinfo.stlab.config.Config;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.model.UserAccount;

import javax.inject.Inject;

public class AuthenticationController {

    @Inject private UserAccountDao userAccountDao;

    public UserAccount authenticate(String email, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        UserAccount userAccount = userAccountDao.findByEmailAndPassword(email,password);
        if(userAccount == null)
            return null;
        else
            return userAccount;
    }

    public String issueToken(String email, String userRole, String name) {
        Algorithm algorithm = Algorithm.HMAC256(Config.JTW_TOKEN_KEY);
        String token = JWT.create()
                .withClaim("email", email)
                .withClaim("userRole", userRole)
                .withClaim("name", name)
                .withIssuer("auth0")
                .sign(algorithm);
        return token;
    }
}
