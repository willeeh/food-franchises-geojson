package com.fastfood.geojson;

import com.fastfood.model.Category;
import com.fastfood.model.Franchise;
import com.fastfood.model.Location;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.json.JSONException;
import org.json.JSONWriter;
import org.mapfish.geo.MfFeature;
import org.mapfish.geo.MfFeatureCollection;
import org.mapfish.geo.MfGeoJSONWriter;
import org.mapfish.geo.MfGeometry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class GeoJsonGenerator
{
	public void generateGeoJson(final Category category, Set<Franchise> franchises)
	{
		final GeometryFactory geometryFactory = new GeometryFactory();

		FileWriter writer = null;
		try
		{
			writer = new FileWriter(category.name() + ".geojson");
			MfGeoJSONWriter geoJSONWriter = new MfGeoJSONWriter(new JSONWriter(writer));

			Collection<MfFeature> featureCollection = new ArrayList<MfFeature>();

			for (final Franchise franchise : franchises)
			{
				for (final Location location : franchise.getLocations())
				{
					MfFeature feature = new MfFeature()
					{
						@Override
						public String getFeatureId()
						{
							return "";
						}

						@Override
						public MfGeometry getMfGeometry()
						{
							Point point = null;
							try
							{
								point = geometryFactory.createPoint(
										new Coordinate(
												location.getLongitude().doubleValue(),
												location.getLatitude().doubleValue()
												)
								);
							}
							catch (Exception ex)
							{
								System.err.println("Error. " + ex);
							}

							return new MfGeometry(point);
						}

						@Override
						public void toJSON(JSONWriter jsonWriter) throws JSONException
						{
							jsonWriter.key("marker-symbol").value(category.getMarkerSymbol());
							jsonWriter.key("marker-color").value(category.getMarkerColor());

							jsonWriter.key("name").value(franchise.getName());
							jsonWriter.key("address").value(location.getAddress());
							jsonWriter.key("city").value(location.getCity());
							jsonWriter.key("zipCode").value(location.getZipCode());
						}
					};

					featureCollection.add(feature);
				}
			}

			geoJSONWriter.encodeFeatureCollection( new MfFeatureCollection(featureCollection) );

		}
		catch (IOException e)
		{
			System.err.println("Error. " + e);
		}
		catch (JSONException e)
		{
			System.err.println("Error. " + e);
		}
		finally
		{
			try
			{
				if (writer != null)
				{
					writer.close();
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}
}
