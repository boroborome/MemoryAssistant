package com.happy3w.memoryassistant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeConverter;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tblInformation")
public class MAInformation {

    @Id
    @Column(name = "tid")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
//	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name = "tblInfoKeyRelation",
//			joinColumns = @JoinColumn(name = "infoid"),
//			inverseJoinColumns = @JoinColumn(name = "wordid"))
    @Column(name = "keywords")
    @Convert(converter = DbConverter.class)
    private List<MAKeyword> lstKeyword = new ArrayList<MAKeyword>();


    @PreUpdate
    @PrePersist
    void onPrePersistOrUpdate() {
        if (createdTime == null) {
            this.createdTime = new Date();
        }

        modifyTime = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MAInformation that = (MAInformation) o;
        return id == that.id &&
                Objects.equals(content, that.content) &&
                Objects.equals(lstKeyword, that.lstKeyword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
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
