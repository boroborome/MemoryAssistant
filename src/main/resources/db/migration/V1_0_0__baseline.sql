-- create keyword table
create table tblKeyWord (`id` BIGINT NOT NULL AUTO_INCREMENT,
	keyword varchar(255),
	PRIMARY KEY (`id`));
create index idxKeyWordConent on tblKeyWord(keyword);

-- create information table
create table tblInformation (`id` BIGINT NOT NULL AUTO_INCREMENT,
	modifyTime TIMESTAMP NULL DEFAULT NULL,
	content varchar(2000),
	keywords varchar(2000),
	createTime TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`id`));
create index idxTblInfoMT on tblInformation(modifyTime);

-- create relation between information and keyword
create table tblInfoKeyRelation (wordid BIGINT,
	infoid BIGINT,
	PRIMARY KEY (wordid, infoid));
alter table tblInfoKeyRelation add constraint wordid_FK Foreign Key (wordid) references tblKeyWord (`id`);
alter table tblInfoKeyRelation add constraint infoid_FK Foreign Key (infoid) references tblInformation (`id`);

create index idxIkrKeyword on tblInfoKeyRelation(wordid);
create index idxIkrInfoid on tblInfoKeyRelation(infoid);

