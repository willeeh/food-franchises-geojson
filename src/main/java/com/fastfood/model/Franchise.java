package com.fastfood.model;

import java.util.ArrayList;
import java.util.List;

public class Franchise
{
	private String name;

	private String category;

	private String image;

	private List<Location> locations = new ArrayList<Location>();

	public Franchise(String name, String category, String image)
	{
		this.name = name;
		this.category = category;
		this.image = image;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void addLocation(Location location)
	{
		locations.add(location);
	}

	public List<Location> getLocations()
	{
		return locations;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Franchise franchise = (Franchise) o;

		if (!name.equals(franchise.name)) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	@Override
	public String toString()
	{
		return "Franchise{" +
				"name='" + name + '\'' +
				", image='" + image + '\'' +
				", locations=" + locations +
				'}';
	}
}
