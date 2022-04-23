package xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tables")
@XmlAccessorType(XmlAccessType.FIELD)

public class TablesXML
{

	@XmlElement(name = "table")
	private List<TableXML> table;

	public List<TableXML> getTablesList()
	{
		return table;
	}

	public void setTablesList(List<TableXML> table)
	{
		this.table = table;
	}

}
