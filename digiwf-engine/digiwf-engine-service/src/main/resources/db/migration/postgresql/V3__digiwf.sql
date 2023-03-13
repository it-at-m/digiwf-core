create table dwf_file
(
    id          varchar(36) not null,
    businesskey varchar(255),
    data        bytea,
    type        varchar(255),
    name        varchar(255),
    primary key (id)
);

create table dwf_filter
(
    id_           varchar(36)  not null,
    filterstring_ varchar(255) not null,
    pageid_       varchar(255) not null,
    userid_       varchar(255) not null,
    primary key (id_)
);

create table dwf_form
(
    id            varchar(36)  not null,
    authorization_ varchar(255),
    config        text,
    key           varchar(255) not null,
    version       varchar(255) not null,
    primary key (id)
);

create table dwf_info
(
    tbllock           varchar(1) not null,
    environment       varchar(255),
    maintenance_info1 varchar(255),
    maintenance_info2 varchar(255),
    primary key (tbllock)
);

create table dwf_json_schema
(
    key_    varchar(255) not null,
    schema_ text,
    primary key (key_)
);

create table dwf_process_instance_auth
(
    id_                varchar(36)  not null,
    processinstanceid_ varchar(255) not null,
    userid_            varchar(255) not null,
    primary key (id_)
);

create table dwf_process_instance_info
(
    id_                   varchar(36)  not null,
    processdefinitionkey_ varchar(255) not null,
    processname_          varchar(255) not null,
    description_          varchar(255),
    endtime_              timestamp,
    processinstanceid_    varchar(255),
    removaltime_          timestamp,
    starttime_            timestamp,
    status_               varchar(255),
    statuskey_            varchar(255),
    primary key (id_)
);

create table dwf_processconfig
(
    id      varchar(36)  not null,
    config  text,
    key     varchar(255) not null,
    version varchar(255) not null,
    primary key (id)
);

create table dwf_start_context
(
    id_            varchar(36)  not null,
    definitionkey_ varchar(255) not null,
    filecontext_   varchar(255) not null,
    userid_        varchar(255) not null,
    primary key (id_)
);

create table dwf_task_info
(
    id_             varchar(36)  not null,
    assignee_       varchar(255),
    definitionname_ varchar(255) not null,
    description_    varchar(255),
    instanceid_     varchar(255) not null,
    primary key (id_)
);
create index IDX_DWF_FILTER_USERID on dwf_filter (userid_);
create index IDX_DWF_FORMKEY on dwf_form (key);

alter table dwf_form add constraint UK_dwf_form_key unique (key);
create index IDX_DWF_SCHEMAKEY on dwf_json_schema (key_);
create index IDX_DWF_PROCAUTH_USERID on dwf_process_instance_auth (userid_);
create index IDX_DWF_PROCESSKEY on dwf_processconfig (key);

alter table dwf_processconfig add constraint UK_dwf_processconfig_key unique (key);
create index IDX_DWF_CONTEXT_USER on dwf_start_context (userid_, definitionkey_);

