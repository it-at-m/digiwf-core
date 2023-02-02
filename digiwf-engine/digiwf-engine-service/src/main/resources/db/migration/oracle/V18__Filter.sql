create table DWF_FILTER
(
    id_             varchar2(36 char) not null,
    filterstring_   varchar2(255 char) not null,
    userid_         varchar2(36 char) not null,
    pageid_         varchar2(36 char) not null,
    primary key (id_)
);

CREATE INDEX IDX_DWF_FILTER_USERID ON DWF_FILTER (userid_);
