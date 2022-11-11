## Architecture

The integration artifact is built as a Spring Boot starter. I.e. there must not be a central instance of the service, but potentially n services are operated in different domains.

It must be noted that the API must be accessible from the outside (internet) for verification. 

How the Service should technically work:

<div>
    <img src="https://github.com/it-at-m/digiwf-verification-integration/blob/dev/images/dataflow.png" alt="Data Flow"/>
</div>


How the Service should be used in a domain-specific scenario:
<div>
    <img src="https://github.com/it-at-m/digiwf-verification-integration/blob/dev/images/architecture.png" alt="Architecture"/>
</div>

