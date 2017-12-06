package com.happy3w.memoryassistant.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tblInformation")
@Getter
@Setter
public class MAInformation
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

	@Basic
	@Column(name = "createdTime")
	private Date createdTime;

	@Basic
	@Column(name = "modifyTime")
	private Date modifyTime;

	@Basic
	@Column(name = "content")
	private String content;

	@Basic
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tblInfoKeyRelation",
			joinColumns = @JoinColumn(name = "tblKeyWord_wordid", referencedColumnName = "wordid"),
			inverseJoinColumns = @JoinColumn(name = "tblInformation_createTime", referencedColumnName = "infoid"))
//	@Column(name = "milestoneItems")
//	@Convert(converter = DbConverter.class)
	private List<MAKeyword> lstKeyword = new ArrayList<MAKeyword>();


    @PreUpdate
    @PrePersist
    void onPrePersistOrUpdate() {
        if (createdTime == null) {
            this.createdTime = new Date();
        }

        modifyTime = new Date();
    }

	public static class DbConverter implements AttributeConverter<List<MAKeyword>, String> {

		@Override
		public String convertToDatabaseColumn(List<MAKeyword> maKeywords) {
			return MAKeyword.list2String(maKeywords);
		}

		@Override
		public List<MAKeyword> convertToEntityAttribute(String s) {
			return MAKeyword.string2List(s);
		}
	}
}
