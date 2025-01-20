package com.algo.domain.common.validators;
import com.algo.domain.common.annotations.ValidObjectId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectIdValidator implements ConstraintValidator<ValidObjectId, String> {
    private static final String OBJECT_ID_REGEX = "^[0-9a-fA-F]{24}$";
    private static final Pattern OBJECT_ID_PATTERN = Pattern.compile(OBJECT_ID_REGEX);

    public ObjectIdValidator() {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && this.isValidObjectId(value);
    }

    public boolean isValidObjectId(String objectId) {
        if (objectId == null) {
            return false;
        } else {
            Matcher matcher = OBJECT_ID_PATTERN.matcher(objectId);
            return matcher.matches();
        }
    }
}
