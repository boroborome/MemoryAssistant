package com.happy3w.memoryassistant.model;

import lombok.*;

import javax.persistence.*;
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
public class MAInfoKey implements Serializable
{
	@Id
	@Column(name = "infoid")
	private long infoid;

	@Id
	@Column(name = "wordid")
	private long wordid;
}
