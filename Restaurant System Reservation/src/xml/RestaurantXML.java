package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;

@XmlRootElement(name = "restaurant")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestaurantXML
{

	@XmlElement(name = "users")
	private UsersXML users = null;

	@XmlElement(name = "tables")
	private TablesXML tables = null;

	@XmlElement(name = "dishes")
	private DishesXML dishes = null;

	public RestaurantXML(List<Users> usersList, List<RestTable> tablesList, List<RestDish> dishesList)
	{
		UsersXML usersXML = setUsersObject(usersList);
		TablesXML tablesXML = setTablesObject(tablesList);
		DishesXML dishesXML = setDishesObject(dishesList);

		this.users = usersXML;
		this.tables = tablesXML;
		this.dishes = dishesXML;
	}

	public RestaurantXML()
	{
		super();
	}

	public UsersXML getUsersObject()
	{
		return users;
	}

	public void setUsersObject(UsersXML users)
	{
		this.users = users;
	}

	public TablesXML getTablesObject()
	{
		return tables;
	}

	public void setTablesObject(TablesXML tables)
	{
		this.tables = tables;
	}

	public DishesXML getDishesObject()
	{
		return dishes;
	}

	public void setDishesObject(DishesXML dishes)
	{
		this.dishes = dishes;
	}

	public UsersXML setUsersObject(List<Users> usersList)
	{
		UsersXML usersXML = new UsersXML();

		List<UserXML> uList = new ArrayList<>();
		for (Users u : usersList)
		{
			UserXML userXML = new UserXML();
			userXML.setName(u.getName());
			userXML.setRole(u.getRole());
			userXML.setUsername(u.getUsername());
			userXML.setPassword(u.getPassword());
			userXML.setReservedTable(u.getTable());
			uList.add(userXML);
		}
		usersXML.setUsersList(uList);

		return usersXML;
	}

	public TablesXML setTablesObject(List<RestTable> tablesList)
	{
		TablesXML tablesXML = new TablesXML();

		List<TableXML> tList = new ArrayList<>();
		for (RestTable t : tablesList)
		{
			TableXML tableXML = new TableXML();
			tableXML.setNumber(t.getNumber());
			tableXML.setNumberOfSeats(t.getNumberOfSeats());
			tableXML.setSmoking(t.isSmoking());
			tableXML.setTotal(t.getTotal());
			tableXML.setAvailable(t.isAvailable());
			tableXML.setReservedBy(t.getReservedBy());
			tableXML.setOrderedDishes(t.getOrderedDishes());
			tList.add(tableXML);
		}
		tablesXML.setTablesList(tList);

		return tablesXML;
	}

	public DishesXML setDishesObject(List<RestDish> dishesList)
	{
		DishesXML dishesXML = new DishesXML();

		List<DishXML> dList = new ArrayList<>();
		for (RestDish d : dishesList)
		{
			DishXML dishXML = new DishXML();
			dishXML.setName(d.getName());
			dishXML.setPrice(d.getPrice());
			dishXML.setType(d.getType());
			dList.add(dishXML);
		}
		dishesXML.setDishesList(dList);

		return dishesXML;
	}

}
