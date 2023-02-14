create table DWF_FILE
(
    ID          varchar2(36 char)  not null,
    DATA        CLOB,
    TYPE        varchar2(255 char) not null,
    NAME        varchar2(255 char) not null,
    BUSINESSKEY varchar2(255 char) not null,
    primary key (ID)
);

CREATE INDEX dwf_file_businessKey ON DWF_FILE (BUSINESSKEY);
