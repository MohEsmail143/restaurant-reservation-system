package users;

import tablesanddishes.RestTable;

public abstract class Users
{
	private String name;
	private String role;
	private String username;
	private String password;
	private RestTable reservedTable;

	public Users(String name, String role, String username, String password)
	{
		this.name = name;
		this.role = role;
		this.username = username;
		this.password = password;
	}

	public Users(String name, String role, String username, String password, RestTable reservedTable)
	{
		super();
		this.name = name;
		this.role = role;
		this.username = username;
		this.password = password;
		this.reservedTable = reservedTable;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public RestTable getTable()
	{
		return reservedTable;
	}

	public void setTable(RestTable table)
	{
		this.reservedTable = table;
	}

}
