package com.foo.endpoint;

import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.services.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ProgrammersEndPoint {

    private final PairingService pairingService;

    @Autowired
    public ProgrammersEndPoint(PairingService pairingService) {
        this.pairingService = pairingService;
    }

    @RequestMapping(value = "/programmers", method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Pair createProgrammer(@RequestBody Programmer programmer) {
        return pairingService.createPairFor(programmer.getName());
    }
}
