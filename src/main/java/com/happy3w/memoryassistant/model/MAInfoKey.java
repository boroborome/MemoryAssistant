package com.happy3w.memoryassistant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tblInfoKeyRelation")
@IdClass(MAInfoKey.class)
@Getter
@Setter
@EqualsAndHashCode
public class MAInfoKey implements Serializable {
    @Id
    @Column(name = "infoid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long infoid;

    @Id
    @Column(name = "wordid")
    private long wordid;
}
