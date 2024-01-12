package com.microservice.APIpatient.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Le champ ne peut pas être null.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Le champ ne peut pas être null.")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Le champ ne peut pas être null.")
    @Column(name = "birthday")
    private Date birthday;

    @NotNull(message = "Le champ ne peut pas être null.")
    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;
      
	public Patient() {
		super();
	}

	public Patient(Long id, @NotNull(message = "Le champ ne peut pas être null.") String lastName,
			@NotNull(message = "Le champ ne peut pas être null.") String firstName,
			@NotNull(message = "Le champ ne peut pas être null.") Date birthday,
			@NotNull(message = "Le champ ne peut pas être null.") String gender, Address address, String phoneNumber) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthday = birthday;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
    
    
}