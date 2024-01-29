package de.muenchen.oss.digiwf.dms.integration.application.port.in;

public interface SearchFileUseCase {

    /**
     * Searches for a file. A Search can be refined by an optional 'Fachdatum'/business case.
     *
     * @param searchString String to search for
     * @param user         account name
     * @param reference    (optional) 'Fachdatum'/business case to refine a search
     * @param value        (optional) value of 'Fachdatum'/business case
     * @return File id.
     */
    String searchFile(String searchString, String user, String reference, String value);
}
