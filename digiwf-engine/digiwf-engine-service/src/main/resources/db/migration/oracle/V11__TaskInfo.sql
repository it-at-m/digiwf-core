create table DWF_TASK_INFO
(
    id_             varchar2(36 char) not null,
    description_    varchar2(1024 char),
    definitionname_ varchar2(255 char) not null,
    assignee_       varchar2(255 char),
    instanceid_     varchar2(36 char) not null,
    primary key (id_)
);
