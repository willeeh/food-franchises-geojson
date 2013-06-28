package com.fastfood.model;

public class Location
{
	private String address;

	private String city;

	private String zipCode;

	private Number latitude;

	private Number longitude;

	public Location(String address, String city, String zipCode, Number latitude, Number longitude)
	{
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public Number getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Number latitude)
	{
		this.latitude = latitude;
	}

	public Number getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Number longitude)
	{
		this.longitude = longitude;
	}

	@Override
	public String toString()
	{
		return "Location{" +
				"address='" + address + '\'' +
				", city='" + city + '\'' +
				", zipCode='" + zipCode + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
