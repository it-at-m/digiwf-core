create table VERIFICATION (
       ID varchar(36) not null,
        PROCESS_INSTANCE_ID varchar(255) not null,
        MESSAGE_NAME varchar(255) not null,
        EXPIRY_TIME timestamp,
        SUBJECT varchar(255),
        TOKEN varchar(255) not null,
        primary key (ID)
    )

create index IDX_PROCINSTID_MSGNAME on VERIFICATION (PROCESS_INSTANCE_ID, MESSAGE_NAME)

alter table VERIFICATION add constraint UK8alwtjycuu3asfrlsu2nnwqq8 unique (PROCESS_INSTANCE_ID, MESSAGE_NAME)

alter table VERIFICATION add constraint UK_tiglkupondvw9m2u6xe6u9x63 unique (TOKEN)