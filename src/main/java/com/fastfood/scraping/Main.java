package com.fastfood.scraping;

import com.fastfood.model.Category;
import com.fastfood.model.Franchise;
import com.fastfood.model.Location;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.fastfood.util.Constants.*;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		Main main = new Main();

		main.scraping();
	}

	public void scraping() throws IOException
	{
		Set<Franchise> franchises = new HashSet<Franchise>();

		for ( Category c : Category.values() )
		{
			Document doc = Jsoup.connect(c.getUrl()).get();
			Elements franchisesElements = doc.getElementsByClass(LIST_T);

			for (Element franchiseElement : franchisesElements)
			{
				Franchise franchiseModel = generateFranchiseModel(franchiseElement, c);

				if ( franchises.contains(franchiseModel) )
				{
					continue;
				}

				franchises.add(franchiseModel);

				Elements locations = getLocationsElement(franchiseElement);

				int i = 0;
				for (Element location : locations)
				{
					franchiseModel.addLocation( generateLocationModel(location, ++i) );
				}

				generateGeoJson(franchiseModel);
			}
		}
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

		return new Location(address, city, zipCode, Double.parseDouble(latitudeStr), Double.parseDouble(longitudStr));
	}

	private String getAdressOrCityOrZipCode(Element location, int counter, int suffix)
	{
		Element addressSpan = location.getElementById(PREFIX_LOCATION_CLASS + (counter < 10 ? "0" : "") + counter + LABEL + suffix);
		return addressSpan != null ? addressSpan.text() : null;
	}


	private void generateGeoJson(Franchise franchise)
	{
		System.out.println(franchise.toString());
		//TODO
	}

}
