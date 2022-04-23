package users;

import tablesanddishes.RestTable;

public class Waiter extends Users
{

	public Waiter(String name, String role, String username, String password)
	{
		super(name, role, username, password);
	}

	public Waiter(String name, String role, String username, String password, RestTable reservedTable)
	{
		super(name, role, username, password, reservedTable);
	}
}
