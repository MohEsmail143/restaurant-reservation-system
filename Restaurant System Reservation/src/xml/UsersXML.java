package xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersXML
{

	@XmlElement(name = "user")
	private List<UserXML> user;

	public List<UserXML> getUsersList()
	{
		return user;
	}

	public void setUsersList(List<UserXML> users)
	{
		this.user = users;
	}

}
