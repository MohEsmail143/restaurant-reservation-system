package tablesanddishes;

public class RestDish
{
	private String name;
	private int price;
	private String type;
	private double tax;

	public RestDish()
	{
		super();
	}

	public RestDish(String name, int price, String type)
	{
		this.name = name;
		this.price = price;
		this.type = type;
		setTax(this.type);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;

		if (this.type.equalsIgnoreCase("appetizer"))
			setTax(0.1);

		else if (this.type.equalsIgnoreCase("main_course"))
			setTax(0.15);

		else if (this.type.equalsIgnoreCase("desert"))
			setTax(0.2);
	}

	public void setTax(double tax)
	{
		this.tax = tax;
	}

	public void setTax(String type)
	{

		if (type.equalsIgnoreCase("appetizer"))
			setTax(0.1);

		else if (type.equalsIgnoreCase("main_course"))
			setTax(0.15);

		else if (type.equalsIgnoreCase("desert"))
			setTax(0.2);
	}

	public double getTax()
	{
		return tax;
	}

}
