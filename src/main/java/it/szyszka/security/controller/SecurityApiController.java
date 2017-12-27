package it.szyszka.security.controller;

import it.szyszka.user.repository.UserRepository;
import it.szyszka.security.service.SecurityService;
import it.szyszka.utils.server.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by rafal on 30.09.17.
 */
@Controller
public class SecurityApiController {

    private static Logger logger = Logger.getLogger(SecurityApiController.class);

    @Autowired
    SecurityService securityService;
    @Autowired UserRepository userRepo;

    @RequestMapping(value = "/api/user/verifyEmail", method = RequestMethod.GET)
    public String verifyEmail(@RequestParam String token) {
        Response response = securityService.verifyToken(token, userRepo);
        if(response == Response.USER_ACCOUNT_ACTIVATED) {
            logger.info("Token: " + token + " successfully verified!");
            return "verification_success";
        } else {
            logger.info("Token: " + token + " not found!");
            return "verification_failed";
        }

    }

}
