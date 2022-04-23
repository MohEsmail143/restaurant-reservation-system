package xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dishes")
@XmlAccessorType(XmlAccessType.FIELD)

public class DishesXML
{

	@XmlElement(name = "dish")
	private List<DishXML> dish;

	public List<DishXML> getDishesList()
	{
		return dish;
	}

	public void setDishesList(List<DishXML> dish)
	{
		this.dish = dish;
	}

}
