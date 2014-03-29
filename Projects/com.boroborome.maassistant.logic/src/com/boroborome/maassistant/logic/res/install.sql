-- create keyword table
create table tblKeyWord (wordid BIGINT,
	keyword varchar(255),
	CONSTRAINT primaryKey PRIMARY KEY (wordid));
create index idxKeyWordConent on tblKeyWord(keyword);

-- create information table
create table tblInformation (createTime BIGINT,
	modifyTime BIGINT,
	content varchar(2000),
	CONSTRAINT primaryKey PRIMARY KEY (createTime));
create index idxTblInfoMT on tblInformation(modifyTime);

-- create relation between information and keyword
create table tblInfoKeyRelation (wordid BIGINT foreign key references tblKeyWord(wordid),
	infoid BIGINT foreign key references tblInformation(createTime),
	CONSTRAINT primaryKey PRIMARY KEY (wordid, infoid));

create index idxIkrKeyword on tblInfoKeyRelation(wordid);
create index idxIkrInfoid on tblInfoKeyRelation(infoid);

