package com.games.endpoints;


import com.games.domains.Board;
import com.games.domains.Ship;
import com.games.exceptions.ShipCollisionCheck;
import com.games.exceptions.ShipExistsCheck;
import com.games.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BoardsController implements BadRequestHandler {

    private GameService service;

    private Function<Exception, ObjectError> shipError;

    @Autowired
    public BoardsController(GameService service) {
        shipError = error.apply("board");
        this.service = service;
    }

    @RequestMapping(
            value = "/boards/{boardId}",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Board placeShip(@PathVariable Long boardId,
                           @Valid @RequestBody Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        return service.placeShip(boardId, ship);
    }

    @Override
    @ExceptionHandler({ShipExistsCheck.class, ShipCollisionCheck.class})
    public ResponseEntity processValidationError(Exception check) {
        return badRequest().body(getErrors(of(check).map(shipError).map(Stream::of).map(objectErrors)));
    }
}
