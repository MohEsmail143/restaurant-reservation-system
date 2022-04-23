package users;

import tablesanddishes.RestTable;

public class Manager extends Users
{

	public Manager(String name, String role, String username, String password)
	{
		super(name, role, username, password);
	}

	public Manager(String name, String role, String username, String password, RestTable reservedTable)
	{
		super(name, role, username, password, reservedTable);
	}
}
