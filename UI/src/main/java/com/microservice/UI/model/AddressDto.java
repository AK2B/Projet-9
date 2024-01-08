package com.microservice.UI.model;

public class AddressDto {

    private Long id;
    private String address;

    public AddressDto(Long id, String address) {
        this.id = id;
        this.address = address;
    }

    public AddressDto() {
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	 @Override
	    public String toString() {
	        return "AddressDto{" +
	                "id=" + id +
	                ", address='" + address + '\'' +
	                '}';
	    }

}
