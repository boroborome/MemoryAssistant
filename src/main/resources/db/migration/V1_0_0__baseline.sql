-- create keyword table
create table tblKeyWord (tid BIGINT NOT NULL AUTO_INCREMENT,
	keyword varchar(255),
	PRIMARY KEY (tid));
create index idxKeyWordConent on tblKeyWord(keyword);

-- create information table
create table tblInformation (tid BIGINT NOT NULL AUTO_INCREMENT,
	modifyTime TIMESTAMP NULL DEFAULT NULL,
	content varchar(2000),
	keywords varchar(2000),
	createdTime TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (tid));
create index idxTblInfoMT on tblInformation(modifyTime);

-- create relation between information and keyword
create table tblInfoKeyRelation (wordid BIGINT,
	infoid BIGINT,
	PRIMARY KEY (wordid, infoid));

create index idxIkrKeyword on tblInfoKeyRelation(wordid);
create index idxIkrInfoid on tblInfoKeyRelation(infoid);

