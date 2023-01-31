package com.happy3w.memoryassistant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tblKeyWord")
@Getter
@Setter
@EqualsAndHashCode
public class MAKeyword {
    public static final char KeywordSplitChar = ' ';
    public static final String KeywordSplitStr = " ";

    @Id
    @Column(name = "tid")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "keyword")
    private String keyword;

    public static String list2String(List<MAKeyword> lstKeyword) {
        StringBuilder buf = new StringBuilder();
        if (lstKeyword != null && !lstKeyword.isEmpty()) {
            for (MAKeyword keyword : lstKeyword) {
                if (buf.length() > 0) {
                    buf.append(KeywordSplitChar);
                }
                buf.append(keyword.getKeyword());
            }
        }
        return buf.toString();
    }

    public static List<MAKeyword> string2List(String lstKeywrod) {
        //TODO [optimize] should filter repeat keyword
        List<MAKeyword> newLstKeyword = new ArrayList<MAKeyword>();
        String[] aryKeys = lstKeywrod.split(KeywordSplitStr);
        for (String key : aryKeys) {
            if (key == null || key.isEmpty()) {
                continue;
            }

            MAKeyword keyword = new MAKeyword();
            keyword.setKeyword(key);
            keyword.setId(-1);
            newLstKeyword.add(keyword);
        }
        return newLstKeyword;
    }

    @Override
    public String toString() {
        return this.id + ":" + this.keyword;
    }
}
