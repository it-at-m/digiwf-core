 create table DWF_FORM (
       ID varchar2(36 char) not null,
        CONFIG CLOB,
        KEY varchar2(255 char) not null,
        VERSION varchar2(255 char) not null,
        primary key (ID)
    );
    
alter table DWF_FORM add constraint UK_9fp7hhatghl1fehsvgdb4dqb4 unique (KEY);


 create table DWF_PROCESSCONFIG (
        ID varchar2(36 char) not null,
        CONFIG CLOB,
        KEY varchar2(255 char) not null,
        VERSION varchar2(255 char) not null,
        primary key (ID)
    );

alter table DWF_PROCESSCONFIG add constraint UK_9fp7hhatghl1fehsvgdb5dqb5 unique (KEY);