package com.ibm.academia.restapi.ipfraude.modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import com.ibm.academia.restapi.ipfraude.modelo.dto.CountryDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class BlackList implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ip", nullable = false, unique = true)
	private String ip;
	
	@Column(name = "banned", nullable = false)
	private Boolean banned;
	
	@Embedded
	private CountryDTO country;
	
	@Column(name = "created_date")
	private Date creationDate;
	
	@PrePersist()
	private void pre() {
		creationDate = new Date();
	}

	public BlackList(Long id, String ip, Boolean banned, CountryDTO country) {
		super();
		this.id = id;
		this.ip = ip;
		this.banned = banned;
		this.country = country;
	}
	
	private static final long serialVersionUID = -7721707040201541767L;
}
