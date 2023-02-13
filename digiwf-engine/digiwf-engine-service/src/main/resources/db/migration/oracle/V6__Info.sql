create table DWF_INFO
(
    TBLLOCK char(1) not null,
    MAINTENANCE_INFO1 varchar2(255 char),
    MAINTENANCE_INFO2 varchar2(255 char),
    ENVIRONMENT varchar2(255 char),
    constraint PK_DWFINFO PRIMARY KEY (TBLLOCK),
    constraint CK_DWFINFO_LOCKED CHECK (TBLLOCK='X')
);