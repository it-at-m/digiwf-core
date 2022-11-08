package io.muenchendigital.digiwf.s3.integration.api.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FolderInFilePathValidator implements ConstraintValidator<FolderInFilePath, String> {

    public static final String SEPARATOR = "/";

    @Override
    public void initialize(final FolderInFilePath contactNumber) {
    }

    /**
     * Checks if the file path contains a folder.
     *
     * @param pathToFile the file path.
     * @param context context in which the constraint is evaluated
     * @return false if the filepath contains no folder. Otherwise true.
     */
    public boolean isValid(final String pathToFile, final ConstraintValidatorContext context) {
        final String pathToFolder = StringUtils.substringBeforeLast(pathToFile, SEPARATOR);
        return StringUtils.contains(pathToFile, SEPARATOR) &&
                StringUtils.isNotEmpty(pathToFolder);
    }

}
