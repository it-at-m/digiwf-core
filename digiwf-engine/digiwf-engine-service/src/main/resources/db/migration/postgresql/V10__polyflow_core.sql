create sequence hibernate_sequence start with 1 increment by 1;

create table association_value_entry
(
    id                int8         not null,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255),
    primary key (id)
);

create table saga_entry
(
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga bytea,
    primary key (saga_id)
);

create table snapshot_event_entry
(
    aggregate_identifier varchar(255) not null,
    sequence_number      bigint       not null,
    type                 varchar(255) not null,
    event_identifier     varchar(255) not null,
    meta_data            bytea,
    payload              bytea         not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    primary key (aggregate_identifier, sequence_number, type)
);

create table token_entry
(
    processor_name varchar(255) not null,
    segment        int4         not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          bytea,
    token_type     varchar(255),
    primary key (processor_name, segment)
);

CREATE TABLE dead_letter_entry
(
    dead_letter_id       VARCHAR(255) NOT NULL,
    cause_message        VARCHAR(255),
    cause_type           VARCHAR(255),
    diagnostics          BYTEA,
    enqueued_at          TIMESTAMP    NOT NULL,
    last_touched         TIMESTAMP,
    aggregate_identifier VARCHAR(255),
    event_identifier     VARCHAR(255) NOT NULL,
    message_type         VARCHAR(255) NOT NULL,
    meta_data            BYTEA,
    payload              BYTEA        NOT NULL,
    payload_revision     VARCHAR(255),
    payload_type         VARCHAR(255) NOT NULL,
    sequence_number      INT8,
    time_stamp           VARCHAR(255) NOT NULL,
    token                BYTEA,
    token_type           VARCHAR(255),
    type                 VARCHAR(255),
    processing_group     VARCHAR(255) NOT NULL,
    processing_started   TIMESTAMP,
    sequence_identifier  VARCHAR(255) NOT NULL,
    sequence_index       INT8         NOT NULL,
    PRIMARY KEY (dead_letter_id)
);

create table domain_event_entry
(
    global_index         INT8         not null,
    event_identifier     varchar(255) not null,
    meta_data            BYTEA,
    payload              BYTEA        not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    aggregate_identifier varchar(255) not null,
    sequence_number      INT8         not null,
    type                 varchar(255),
    primary key (global_index)
);


create index IDX_association_value_entry_stakav on association_value_entry (saga_type, association_key, association_value);
create index IDX_association_value_entry_sist on association_value_entry (saga_id, saga_type);
create index IDX_dead_letter_entry_pg on dead_letter_entry (processing_group);
create index IDX_dead_letter_entry_pgsi on dead_letter_entry (processing_group, sequence_identifier);

alter table dead_letter_entry
    add constraint UC_dead_letter_entry_pgsisi unique (processing_group, sequence_identifier, sequence_index);

alter table domain_event_entry
    add constraint UC_domain_event_entry_aisn unique (aggregate_identifier, sequence_number);

alter table domain_event_entry
    add constraint UC_domain_event_entry_ei unique (event_identifier);

alter table snapshot_event_entry
    add constraint UC_snapshot_event_entry_ei unique (event_identifier);
