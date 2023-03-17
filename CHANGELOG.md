# Change Log

## [apps unrleased] - yyyy-mm-dd

### Added

- tasklist
  - Custom date component to support date input via keyboard

### Fixed

- tasklist
  - Improved pagination

## [services unrleased] - yyyy-mm-dd

### Added

- engine
    - upgrade dms interface
    - added example-processes
- digiwf-integrations
  - digiwf-cosys-integration
    - error handling added
  - created digiwf-example-integration to show how to implement a new integration
- digiwf-libs
  - created digiwf-message
  - created digiwf-integration-lib

### Fixed

- engine
    - whitelist regex in application.properties
    - Improved logging of `DigiWFValidationException`
    - fix service start initialization
    - simplify configuration ops

## [apps 0.21.3] - 2023-01-17

### Added

### Fixed

- tasklist
  - stop passing emtpy value in form
  - continue button error
  - multi user input validation
  - maxItems serialization bug
  - naming processes search field
  - fix on blur input bug

## [apps 0.21.0] - 2022-11-27

### Added

- tasklist
    - persistent filters
    - save filter as url query param
- digiwf-form-builder
    - validation of list items
- docs
    - Added vuepress docs page

### Fixed

- digiwf-form-builder:
    - move field to nested objects
    - general styling issues
    - reactivity when updating schema

## [services 0.14.0] - 2022-11-27

### Added

- digiwf-libs
    - add functionality to extract error information in json serialization
- digiwf-s3-integration
    - supports creation of presigned urls for multiple file paths
    - switch to webClient
    - generate open api client
    - allow to use any s3 storage with the cosys integration (domain specific s3 storage)
- digiwf-email-integration
    - modules added
- digiwf-verification-integration
    - verification integration modules added
- alw-integration
    - modules added
- digiwf-camunda-prometheus
    - modules added

### Fixed

- (digiwf-engine) added a `no-ldap` profile for development
- (digiwf-engine) cannot resolve identifier 'app_file_s3_async_config' on process start bug

## [core 0.11.0] - 2022-08-30

### Added

- (digiwf-libs) add cloud stream function utils
    - add start process endpoint
- (digiwf-connector) extract api to own package
- (digiwf-process) add module and api for starting processes via events

### Fixed

