-- create keyword table
create table tblKeyWord (wordid BIGINT,
	keyword varchar(255),
	PRIMARY KEY (wordid));
create index idxKeyWordConent on tblKeyWord(keyword);

-- create information table
create table tblInformation (createTime BIGINT,
	modifyTime BIGINT,
	content varchar(2000),
	keywords varchar(2000),
	PRIMARY KEY (createTime));
create index idxTblInfoMT on tblInformation(modifyTime);

-- create relation between information and keyword
create table tblInfoKeyRelation (wordid BIGINT,
	infoid BIGINT,
	PRIMARY KEY (wordid, infoid));
alter table tblInfoKeyRelation add constraint wordid_FK Foreign Key (wordid) references tblKeyWord (wordid);
alter table tblInfoKeyRelation add constraint infoid_FK Foreign Key (infoid) references tblInformation (createTime);

create index idxIkrKeyword on tblInfoKeyRelation(wordid);
create index idxIkrInfoid on tblInfoKeyRelation(infoid);

