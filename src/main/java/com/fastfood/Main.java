package com.fastfood;

import com.fastfood.geojson.GeoJsonGenerator;
import com.fastfood.model.Category;
import com.fastfood.model.Franchise;
import com.fastfood.scraping.FranchiseScraping;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		FranchiseScraping franchiseScraping = new FranchiseScraping();
		Map<Category,Set<Franchise>> categorySetMap = franchiseScraping.doScraping();

		GeoJsonGenerator generator = new GeoJsonGenerator();
		for (Entry<Category,Set<Franchise>> entry : categorySetMap.entrySet())
		{
			generator.generateGeoJsonByCategory(entry.getKey(), entry.getValue());
		}
	}

}
