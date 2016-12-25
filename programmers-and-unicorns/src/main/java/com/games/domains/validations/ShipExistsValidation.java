package com.games.domains.validations;


import com.games.domains.Harbor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.games.domains.Harbor.INVALID_SHIP;


public class ShipExistsValidation implements ConstraintValidator<ShipExists, Harbor> {
    public void initialize(ShipExists constraint) {
    }

    public boolean isValid(Harbor shipType, ConstraintValidatorContext context) {
        return shipType != INVALID_SHIP;
    }
}
