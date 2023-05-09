create sequence hibernate_sequence start with 1 increment by 1;

create table association_value_entry
(
    id                integer       not null,
    association_key   varchar2(255 char) not null,
    association_value varchar2(255 char),
    saga_id           varchar2(255 char) not null,
    saga_type         varchar2(255 char),
    primary key (id)
);

create table dead_letter_entry
(
    dead_letter_id       varchar2(255 char) not null,
    cause_message        varchar2(255 char),
    cause_type           varchar2(255 char),
    diagnostics          blob,
    enqueued_at          timestamp    not null,
    last_touched         timestamp,
    aggregate_identifier varchar2(255 char),
    event_identifier     varchar2(255 char) not null,
    message_type         varchar2(255 char) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar2(255 char),
    payload_type         varchar2(255 char) not null,
    sequence_number      integer,
    time_stamp           varchar2(255 char) not null,
    token                blob,
    token_type           varchar2(255 char),
    type                 varchar2(255 char),
    processing_group     varchar2(255 char) not null,
    processing_started   timestamp,
    sequence_identifier  varchar2(255 char) not null,
    sequence_index       integer       not null,
    primary key (dead_letter_id)
);

create table domain_event_entry
(
    global_index         integer       not null,
    event_identifier     varchar2(255 char) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar2(255 char),
    payload_type         varchar2(255 char) not null,
    time_stamp           varchar2(255 char) not null,
    aggregate_identifier varchar2(255 char) not null,
    sequence_number      integer       not null,
    type                 varchar2(255 char),
    primary key (global_index)
);

create table saga_entry
(
    saga_id         varchar2(255 char) not null,
    revision        varchar2(255 char),
    saga_type       varchar2(255 char),
    serialized_saga blob,
    primary key (saga_id)
);

create table snapshot_event_entry
(
    aggregate_identifier varchar2(255 char) not null,
    sequence_number      integer       not null,
    type                 varchar2(255 char) not null,
    event_identifier     varchar2(255 char) not null,
    meta_data            blob,
    payload              blob         not null,
    payload_revision     varchar2(255 char),
    payload_type         varchar2(255 char) not null,
    time_stamp           varchar2(255 char) not null,
    primary key (aggregate_identifier, sequence_number, type)
);

create table token_entry
(
    processor_name varchar2(255 char) not null,
    segment        integer      not null,
    owner          varchar2(255 char),
    timestamp      varchar2(255 char) not null,
    token          blob,
    token_type     varchar2(255 char),
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
