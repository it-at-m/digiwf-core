package de.muenchen.oss.digiwf.dms.integration.application.port.in;

public interface SearchSubjectAreaUseCase {

    /**
     * Search on a subject scope with optional refinement on a specific business case.
     *
     * @param searchString String to search for
     * @param user         account name
     * @param reference    (optional) 'Fachdatum' to refine a search
     * @param value        (optional) value of 'Fachdatum'
     * @return Subject id.
     */
    String searchSubjectArea(String searchString, String user, String reference, String value);
}
