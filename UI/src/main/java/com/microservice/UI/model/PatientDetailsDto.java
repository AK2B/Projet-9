package com.microservice.UI.model;

public class PatientDetailsDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String birthday;
    private String gender;
    private AddressDto address;
    private String phoneNumber;

    public PatientDetailsDto(Long id, String lastName, String firstName, String birthday, String gender,
                             AddressDto address, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PatientDetailsDto() {
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	 @Override
	    public String toString() {
	        return "PatientDetailsDto{" +
	                "id=" + id +
	                ", lastName='" + lastName + '\'' +
	                ", firstName='" + firstName + '\'' +
	                ", birthday='" + birthday + '\'' +
	                ", gender='" + gender + '\'' +
	                ", address=" + (address != null ? address.toString() : "null") +  
	                ", phoneNumber='" + phoneNumber + '\'' +
	                '}';
	    }
}
