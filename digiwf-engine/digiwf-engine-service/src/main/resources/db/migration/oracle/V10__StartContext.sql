create table DWF_START_CONTEXT
(
    id_            varchar2(36 char) not null,
    userid_        varchar2(36 char) not null,
    definitionkey_ varchar2(255 char) not null,
    filecontext_   varchar2(36 char) not null,
    primary key (id_)
);

create index DWF_START_CONTEXT_USERDEF on DWF_START_CONTEXT (userid_, definitionkey_);