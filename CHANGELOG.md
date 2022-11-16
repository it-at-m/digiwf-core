# Change Log

## [apps Unreleased] - yyyy-mm-dd

### Added

- tasklist
    - persistent filters
- digiwf-form-builder
    - validation of list items

### Fixed

- digiwf-form-builder:
    - move field to nested objects
    - general styling issues

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
    - integration modules added
- digiwf-verification-integration
    - verification integration modules added

### Fixed

## [core 0.11.0] - 2022-08-30

### Added

- (digiwf-libs) add cloud stream function utils
    - add start process endpoint
- (digiwf-connector) extract api to own package
- (digiwf-process) add module and api for starting processes via events

### Fixed

