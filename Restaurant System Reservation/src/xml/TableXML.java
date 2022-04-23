package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tablesanddishes.RestDish;

@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableXML
{

	@XmlElement(name = "number")
	private byte number;

	@XmlElement(name = "number_of_seats")
	private byte numberOfSeats;

	@XmlElement(name = "smoking")
	private boolean smoking;

	@XmlElement(name = "total")
	private double total = 0;

	@XmlElement(name = "available")
	private boolean available = true;

	@XmlElement(name = "reserved_by")
	private String reservedBy = null;

	@XmlElement(name = "ordered_dishes")
	private List<DishXML> orderedDishes = null;

	public byte getNumber()
	{
		return number;
	}

	public void setNumber(byte number)
	{
		this.number = number;
	}

	public byte getNumberOfSeats()
	{
		return numberOfSeats;
	}

	public void setNumberOfSeats(byte numberOfSeats)
	{
		this.numberOfSeats = numberOfSeats;
	}

	public boolean isSmoking()
	{
		return smoking;
	}

	public void setSmoking(boolean smoking)
	{
		this.smoking = smoking;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	public String getReservedBy()
	{
		return reservedBy;
	}

	public void setReservedBy(String reservedBy)
	{
		this.reservedBy = reservedBy;
	}

	public List<DishXML> getOrderedDishes()
	{
		return orderedDishes;
	}

	/*
	 * public void setOrderedDishes(List<DishXML> orderedDishes) {
	 * this.orderedDishes = orderedDishes; }
	 */

	public void setOrderedDishes(List<RestDish> dishesList)
	{

		List<DishXML> tempOrderedDishes = new ArrayList<>();

		for (RestDish d : dishesList)
		{
			DishXML dishXML = new DishXML();
			dishXML.setName(d.getName());
			dishXML.setPrice(d.getPrice());
			dishXML.setType(d.getType());
			tempOrderedDishes.add(dishXML);
		}

		this.orderedDishes = tempOrderedDishes;
	}
}
