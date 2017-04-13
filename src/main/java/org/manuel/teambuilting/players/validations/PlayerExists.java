package org.manuel.teambuilting.players.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Manuel on 12/12/2016.
 */
@Constraint(validatedBy = {PlayerExistsValidator.class})
@Documented
@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerExists {

    String message() default "{The player does not exist}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
