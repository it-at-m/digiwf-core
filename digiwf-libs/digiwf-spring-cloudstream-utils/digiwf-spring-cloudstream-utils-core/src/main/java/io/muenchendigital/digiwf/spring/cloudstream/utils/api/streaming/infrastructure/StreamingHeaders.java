package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StreamingHeaders {

    public static final String TYPE = "type";
    public static final String DIGIWF_MESSAGE_NAME = "digiwf.messagename";
    public static final String DIGIWF_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";
}
