create sequence hibernate_sequence start with 1 increment by 1;

create table association_value_entry
(
    id                bigint       not null,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255),
    primary key (id)
);

create table dead_letter_entry
(
    dead_letter_id       varchar(255) not null,
    cause_message        varchar(255),
    cause_type           varchar(255),
    diagnostics          blob,
    enqueued_at          timestamp    not null,
    last_touched         timestamp,
    aggregate_identifier varchar(255),
    event_identifier     varchar(255) not null,
    message_type         varchar(255) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    sequence_number      bigint,
    time_stamp           varchar(255) not null,
    token                blob,
    token_type           varchar(255),
    type                 varchar(255),
    processing_group     varchar(255) not null,
    processing_started   timestamp,
    sequence_identifier  varchar(255) not null,
    sequence_index       bigint       not null,
    primary key (dead_letter_id)
);

create table domain_event_entry
(
    global_index         bigint       not null,
    event_identifier     varchar(255) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    aggregate_identifier varchar(255) not null,
    sequence_number      bigint       not null,
    type                 varchar(255),
    primary key (global_index)
);

create table saga_entry
(
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga blob,
    primary key (saga_id)
);

create table snapshot_event_entry
(
    aggregate_identifier varchar(255) not null,
    sequence_number      bigint       not null,
    type                 varchar(255) not null,
    event_identifier     varchar(255) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    primary key (aggregate_identifier, sequence_number, type)
);

create table token_entry
(
    processor_name varchar(255) not null,
    segment        integer      not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          blob,
    token_type     varchar(255),
    primary key (processor_name, segment)
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
