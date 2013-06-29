package com.fastfood.model;

public enum Category
{
	SANDWICH("http://www.infofranquicias.com/fl-111/franquicias/Bocadillerias.aspx"),
	HEALTHY("http://www.infofranquicias.com/fl-116/franquicias/Comida-sana.aspx"),
	DELIVERY("http://www.infofranquicias.com/fl-115/franquicias/Delivery.aspx"),
	GENERAL("http://www.infofranquicias.com/fl-16/franquicias/Fast-food-quick-service.aspx"),
	BURGER("http://www.infofranquicias.com/fl-114/franquicias/Hamburgueserias.aspx"),
	PIZZA("http://www.infofranquicias.com/fl-113/franquicias/Pizzas.aspx"),
	RESTAURANTS("http://www.infofranquicias.com/fl-18/franquicias/Restaurantes.aspx"),
	THEME_RESTAURANTS("http://www.infofranquicias.com/fl-74/franquicias/Restaurantes-tematicos.aspx"),
	VARIOUS("http://www.infofranquicias.com/fl-56/franquicias/Varios-hosteleria.aspx")
	;

	private Category(String url)
	{
		this.url = url;
	}

	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
