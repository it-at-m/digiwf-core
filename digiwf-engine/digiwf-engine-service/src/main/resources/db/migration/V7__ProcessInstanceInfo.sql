create table DWF_PROCESS_INSTANCE_INFO
(
    id_                   varchar2(36 char) not null,
    processinstanceid_    varchar2(36 char),
    processname_          varchar2(255 char),
    processdefinitionkey_ varchar2(255 char),
    description_          varchar2(1000 char),
    status_               varchar2(255 char),
    statuskey_            varchar2(255 char),
    endtime_              date,
    starttime_            date,
    primary key (id_)
);

CREATE INDEX dwf_processinfo_instid ON DWF_PROCESS_INSTANCE_INFO (processinstanceid_);


create table DWF_PROCESS_INSTANCE_AUTH
(
    id_                varchar2(36 char) not null,
    processinstanceid_ varchar2(255 char),
    userid_            varchar2(255 char),
    primary key (id_)
);

CREATE INDEX dwf_processauth_instid ON DWF_PROCESS_INSTANCE_AUTH (processinstanceid_);
CREATE INDEX dwf_processauth_userid ON DWF_PROCESS_INSTANCE_AUTH (userid_);
