package users;

import tablesanddishes.RestTable;

public class Cooker extends Users
{

	public Cooker(String name, String role, String username, String password)
	{
		super(name, role, username, password);
	}

	public Cooker(String name, String role, String username, String password, RestTable reservedTable)
	{
		super(name, role, username, password, reservedTable);
	}
}
