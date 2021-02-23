package com.example.demo.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.demo.domain.enums.EnumStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Pi {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	@Column
	private String alias;
	@Column
	private String location;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PiSettings piSettings;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	@Enumerated(EnumType.STRING)
	private EnumStatus status = EnumStatus.OFF;
	
}
