package com.foo.endpoint;

import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.doamins.Unicorn;
import com.foo.services.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ProgrammersEndPoint {

    private final PairingService pairingService;

    @Autowired
    public ProgrammersEndPoint(PairingService pairingService) {
        this.pairingService = pairingService;
    }

    @RequestMapping(
            value = "/programmers",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(CREATED)
    public Pair createProgrammer(@RequestBody Programmer programmer) {
        return pairingService.createPairFor(programmer.getName());
    }

    @RequestMapping(
            value = "/programmers",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(OK)
    public List<Programmer> getProgrammers() {
        return pairingService.getProgrammers();
    }

    @RequestMapping(
            value = "/programmers/{programmerName}",
            method = PUT,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(OK)
    public Pair updateProgrammer(@PathVariable String programmerName,
                                 @RequestBody Unicorn unicorn) {
        return pairingService.update(unicorn, programmerName);
    }

    @RequestMapping(
            value = "/unicorns",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(OK)
    public List<Unicorn> getUnicorns() {
        return pairingService.getUnicorns();
    }

    @RequestMapping(
            value = "/pairs",
            method = GET,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(OK)
    public List<Pair> getPairs() {
        return pairingService.getPairs();
    }
}
