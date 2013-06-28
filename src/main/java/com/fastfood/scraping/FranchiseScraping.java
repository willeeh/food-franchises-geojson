package com.fastfood.scraping;

import com.fastfood.model.Category;
import com.fastfood.model.Franchise;
import com.fastfood.model.Location;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.fastfood.util.Constants.*;
import static com.fastfood.util.Constants.LABEL;

public class FranchiseScraping
{
	public Map<Category, Set<Franchise>> doScraping() throws IOException
	{
		Map<Category, Set<Franchise>> franchisesByCategory = new HashMap<Category, Set<Franchise>>();

		for ( Category category : Category.values() )
		{
			Document doc = Jsoup.connect(category.getUrl()).get();
			Elements franchisesElements = doc.getElementsByClass(LIST_T);

			Set<Franchise> franchises = new HashSet<Franchise>();

			for (Element franchiseElement : franchisesElements)
			{
				Franchise franchiseModel = generateFranchiseModel(franchiseElement, category);

				if ( franchises.contains(franchiseModel) )
				{
					continue;
				}

				Elements locations = getLocationsElement(franchiseElement);

				int i = 0;
				for (Element location : locations)
				{
					Location locationModel = generateLocationModel(location, ++i);
					if (locationModel != null)
					{
						franchiseModel.addLocation(locationModel);
					}
				}

				franchises.add(franchiseModel);
			}

			franchisesByCategory.put(category, franchises);

		}

		return franchisesByCategory;
	}

	private Franchise generateFranchiseModel(Element franchiseElement, Category category)
	{
		Element franchiseImageElement = franchiseElement.getElementsByClass(LIST_L).get(0).getElementsByTag(A).get(0).getElementsByTag(IMG).get(0);
		String franchiseImage = franchiseImageElement.attr(SRC);
		Element franchiseLink = getFranchiseLink(franchiseElement);
		String franchiseName = franchiseLink.text();

		return new Franchise(franchiseName, category.name(), franchiseImage);
	}

	private Element getFranchiseLink(Element franchiseElement)
	{
		return franchiseElement.getElementsByClass(LIST_R).get(0).getElementsByTag(A).get(0);
	}

	private Elements getLocationsElement(Element franchiseElement)
	{
		try
		{
			Document franquiseInfoDoc = Jsoup.connect( BASE_URL + getFranchiseLink(franchiseElement).attr(HREF) ).get();
			Element locationsLink = franquiseInfoDoc.getElementById(FRANCHISE_INFO_ID);
			Document franquiseLocationsDoc = Jsoup.connect( BASE_URL + locationsLink.attr(HREF) ).get();
			Elements locations = franquiseLocationsDoc.getElementsByClass(FRANCHISE_LOCATION_REPEATER_CLASS);

			if (locations.size() == 0)
			{
				return locations.empty();
			}

			return locations.get(0).getElementsByClass(FRANCHISE_LOCATION_CLASS);
		}
		catch (IOException e)
		{
			System.err.println("Error. " + e);
		}

		return new Elements().empty();
	}

	private Location generateLocationModel(Element location, int counter)
	{
		String address = getAdressOrCityOrZipCode(location, counter, 1);
		String city = getAdressOrCityOrZipCode(location, counter, 2);
		String zipCode = getAdressOrCityOrZipCode(location, counter, 3);

		String javascript = location.attr(ON_CLICK);
		String latitudeStr = javascript.substring( javascript.indexOf("(")+1, javascript.indexOf(",")).trim();
		String longitudStr = javascript.substring( javascript.indexOf(",")+1, javascript.indexOf(")")).trim();

		double latitude = Double.parseDouble(latitudeStr);
		double longitude = Double.parseDouble(longitudStr);

		if ( !isOutOfSpain(latitude, longitude) )
		{
			return new Location(address, city, zipCode, latitude, longitude);
		}

		return null;
	}

	private String getAdressOrCityOrZipCode(Element location, int counter, int suffix)
	{
		Element addressSpan = location.getElementById(PREFIX_LOCATION_CLASS + (counter < 10 ? "0" : "") + counter + LABEL + suffix);
		return addressSpan != null ? addressSpan.text() : null;
	}

	private boolean isOutOfSpain(double latitude, double longitude)
	{
		return longitude < -18d || longitude > 5d || latitude > 44d || latitude < 27d;
	}
}
