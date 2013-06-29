package com.fastfood.model;

public enum Category
{
	SANDWICH("http://www.infofranquicias.com/fl-111/franquicias/Bocadillerias.aspx", "1abc9c", "fast-food"),
	HEALTHY("http://www.infofranquicias.com/fl-116/franquicias/Comida-sana.aspx", "3498db", "restaurant"),
	DELIVERY("http://www.infofranquicias.com/fl-115/franquicias/Delivery.aspx", "9b59b6", "restaurant"),
	GENERAL("http://www.infofranquicias.com/fl-16/franquicias/Fast-food-quick-service.aspx", "f1c40f", "restaurant"),
	BURGER("http://www.infofranquicias.com/fl-114/franquicias/Hamburgueserias.aspx", "e67e22", "fast-food"),
	PIZZA("http://www.infofranquicias.com/fl-113/franquicias/Pizzas.aspx", "95a5a6", "fast-food"),
	RESTAURANTS("http://www.infofranquicias.com/fl-18/franquicias/Restaurantes.aspx", "e74c3c", "restaurant"),
	THEME_RESTAURANTS("http://www.infofranquicias.com/fl-74/franquicias/Restaurantes-tematicos.aspx", "34495e", "bar"),
	VARIOUS("http://www.infofranquicias.com/fl-56/franquicias/Varios-hosteleria.aspx", "2ecc71", "bar")
	;

	private Category(String url, String markerColor, String markerSymbol)
	{
		this.url = url;
		this.markerColor = markerColor;
		this.markerSymbol = markerSymbol;
	}

	private String url;

	private String markerColor;

	private String markerSymbol;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getMarkerColor()
	{
		return markerColor;
	}

	public String getMarkerSymbol()
	{
		return markerSymbol;
	}
}
