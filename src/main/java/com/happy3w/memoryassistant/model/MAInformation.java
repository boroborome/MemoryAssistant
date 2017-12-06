package com.happy3w.memoryassistant.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tblInformation")
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
			joinColumns = @JoinColumn(name = "tblKeyWord_wordid", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "tblInformation_createTime", referencedColumnName = "id"))
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
