/**
 * 
 */
package org.manuel.teambuilting.players.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

/**
 * @author Manuel Doncel Martos
 *
 */
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
@Immutable
@Data
@lombok.Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Audited
public class Player {
	
	@Id
	@GeneratedValue
	private BigInteger id;

	@Version
	public Long lockVersion;
	
	@NotNull
	@Size(min=2, max=200)
	private String name;

	@Size(min=2, max=200)
	private String nickname;
	
	private Character sex;

	@Size(min=5, max=200)
	private String bornAddress;

	@Size(min=10, max=200)
	private String imageLink;

	@PersistenceConstructor
	public Player(final String name, final String nickname, final Character sex, final String bornAddress, final String imageLink) {
		this.name = name;
		this.nickname = nickname;
		this.sex = sex;
		this.bornAddress = bornAddress;
		this.imageLink = imageLink;
	}

}