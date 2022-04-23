package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Client;
import users.Cooker;
import users.Manager;
import users.Users;
import users.Waiter;

public class MainXML
{

	static List<Users> usersList = new ArrayList<>();
	static List<RestTable> tablesList = new ArrayList<>();
	static List<DishXML> dishesList = new ArrayList<>();

	public RestaurantXML unmarshalRest() throws JAXBException
	{
		JAXBContext jax = JAXBContext.newInstance(RestaurantXML.class);
		Unmarshaller u = jax.createUnmarshaller();
		RestaurantXML r = (RestaurantXML) u.unmarshal(new File("database.xml"));
		return r;
	}

	public static void marshalRest(List<Users> uList, List<RestTable> tList, List<RestDish> dList)
			throws JAXBException, FileNotFoundException
	{
		RestaurantXML r = new RestaurantXML(uList, tList, dList);
		JAXBContext jaxb = JAXBContext.newInstance(RestaurantXML.class);
		Marshaller m = jaxb.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		OutputStream os = new FileOutputStream("database.xml");
		m.marshal(r, os);
	}

	public List<Users> getUsers(RestaurantXML r)
	{

		List<UserXML> list_users = r.getUsersObject().getUsersList();
		List<Users> l_u = new ArrayList<>();

		for (UserXML u : list_users)
		{
			if (u.getRole().equals("Manager"))
			{
				Users m = new Manager(u.getName(), u.getRole(), u.getUsername(), u.getPassword(), u.getReservedTable());
				l_u.add(m);
			}

			if (u.getRole().equals("Waiter"))
			{
				Users w = new Waiter(u.getName(), u.getRole(), u.getUsername(), u.getPassword(), u.getReservedTable());
				l_u.add(w);
			}

			if (u.getRole().equals("Client"))
			{
				Users cl = new Client(u.getName(), u.getRole(), u.getUsername(), u.getPassword(), u.getReservedTable());
				l_u.add(cl);
			}

			if (u.getRole().equals("Cooker"))
			{
				Users c = new Cooker(u.getName(), u.getRole(), u.getUsername(), u.getPassword(), u.getReservedTable());
				l_u.add(c);
			}
		}
		return l_u;
	}

	public List<RestTable> getTables(RestaurantXML r)
	{

		List<TableXML> list_tables = r.getTablesObject().getTablesList();
		List<RestTable> l_t = new ArrayList<>();

		for (TableXML x : list_tables)
		{
			List<RestDish> l_d = new ArrayList<>();
			if (x.getOrderedDishes() != null)
			{
				List<DishXML> list_dishes = x.getOrderedDishes();

				for (DishXML dxml : list_dishes)
				{
					RestDish d = new RestDish(dxml.getName(), dxml.getPrice(), dxml.getType());
					l_d.add(d);
				}
			}

			RestTable table = new RestTable(x.getNumber(), x.getNumberOfSeats(), x.isSmoking(), x.getTotal(),
					x.isAvailable(), x.getReservedBy(), l_d);
			l_t.add(table);
		}

		return l_t;
	}

	public List<RestDish> getDishes(RestaurantXML r)
	{

		List<DishXML> list_dishes = r.getDishesObject().getDishesList();
		List<RestDish> l_d = new ArrayList<>();

		for (DishXML x : list_dishes)
		{
			RestDish d = new RestDish(x.getName(), x.getPrice(), x.getType());
			l_d.add(d);
		}

		return l_d;
	}

}
