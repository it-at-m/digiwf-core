# Change Log

## [apps Unreleased] - yyyy-mm-dd

### Added

- tasklist
  - persistent filters
  - save filter as url query param
- digiwf-form-builder
  - validation of list items

### Fixed

- digiwf-form-builder:
  - move field to nested objects
  - general styling issues
  - reactivity when updating schema

## [services Unreleased] - yyyy-mm-dd

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

