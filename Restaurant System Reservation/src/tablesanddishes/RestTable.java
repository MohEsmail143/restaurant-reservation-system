package tablesanddishes;

import java.util.ArrayList;
import java.util.List;

public class RestTable
{
	private byte number;
	private byte numberOfSeats;
	private boolean smoking;
	private double total;
	private boolean available = true;
	private String reservedBy;
	private List<RestDish> orderedDishes = new ArrayList<>();

	public RestTable()
	{
		super();
	}

	public RestTable(byte number, byte numberOfSeats, boolean smoking)
	{
		this.number = number;
		this.numberOfSeats = numberOfSeats;
		this.smoking = smoking;
	}

	public RestTable(byte number, byte numberOfSeats, boolean smoking, double total, boolean available,
			String reservedBy, List<RestDish> orderedDishes)
	{
		super();
		this.number = number;
		this.numberOfSeats = numberOfSeats;
		this.smoking = smoking;
		this.total = total;
		this.available = available;
		this.reservedBy = reservedBy;
		this.orderedDishes = orderedDishes;
	}

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

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public boolean isSmoking()
	{
		return smoking;
	}

	public void setSmoking(boolean smoking)
	{
		this.smoking = smoking;
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

	public List<RestDish> getOrderedDishes()
	{
		return orderedDishes;
	}

	public void setOrderedDishes(List<RestDish> orderedDishes)
	{
		this.orderedDishes = orderedDishes;
	}

}
