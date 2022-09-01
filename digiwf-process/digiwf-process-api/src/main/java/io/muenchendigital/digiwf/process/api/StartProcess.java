package io.muenchendigital.digiwf.process.api;

import java.util.Map;

/**
 * Interface of the object for starting a process
 */
public interface StartProcess {

    /**
     * Return the key of the process that should be started
     *
     * @return key of the process
     */
    String getKey();

    /**
     * Return the file context for document storage.
     */
    String getFileContext();

    /**
     * Returns the data that is passed to the created process instance
     *
     * @return data that is passed
     */
    Map<String, Object> getData();

}
