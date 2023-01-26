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

create index IDX_association_value_entry_stakav on association_value_entry (saga_type, association_key, association_value);
create index IDX_association_value_entry_sist on association_value_entry (saga_id, saga_type);
