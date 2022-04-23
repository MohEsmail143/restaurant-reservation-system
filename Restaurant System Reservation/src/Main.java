import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import gui.LoginScreen;
import tablesanddishes.RestDish;
import tablesanddishes.RestTable;
import users.Users;
import xml.MainXML;
import xml.RestaurantXML;

public class Main
{
	private static List<Users> usersList = new ArrayList<>();
	private static List<RestTable> tablesList = new ArrayList<>();
	private static List<RestDish> dishesList = new ArrayList<>();

	public static void main(String[] args) throws JAXBException
	{
		MainXML mainXML = new MainXML();
		RestaurantXML r = new RestaurantXML();
		r = mainXML.unmarshalRest();

		usersList = mainXML.getUsers(r);
		tablesList = mainXML.getTables(r);
		dishesList = mainXML.getDishes(r);

		LoginScreen logScreen = new LoginScreen(usersList, tablesList, dishesList);
		logScreen.setVisible(true);
	}

}
