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
import java.util.UUID;

public class GeoJsonGenerator
{
	public void generateGeoJson(Category category, Set<Franchise> franchises)
	{
		final GeometryFactory geometryFactory = new GeometryFactory();

		try
		{
			MfGeoJSONWriter geoJSONWriter = new MfGeoJSONWriter(
					new JSONWriter(new FileWriter(category.name() + ".geojson")) );

			Collection<MfFeature> featureCollection = new ArrayList<MfFeature>();

			for (Franchise franchise : franchises)
			{
				for (final Location location : franchise.getLocations())
				{
					MfFeature feature = new MfFeature()
					{
						@Override
						public String getFeatureId()
						{
							return UUID.randomUUID().toString();
						}

						@Override
						public MfGeometry getMfGeometry()
						{
							Point point = geometryFactory.createPoint(
									new Coordinate(
											location.getLatitude().doubleValue(),
											location.getLongitude().doubleValue()
									)
							);

							return new MfGeometry(point);
						}

						@Override
						public void toJSON(JSONWriter jsonWriter) throws JSONException
						{

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

	}
}
