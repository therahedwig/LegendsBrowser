package legends.model.collections;

import legends.model.World;
import legends.model.collections.basic.EventCollection;
import legends.model.events.basic.EventLocation;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlComponent;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("persecution")
public class PersecutionCollection extends EventCollection {
	@Xml("target_entity_id")
	private int targetEntityId = -1;
	@XmlComponent
	private EventLocation location = new EventLocation();
	
	public EventLocation getLocation() {
		return location;
	}

	@Override
	public String getLink() {
			return "the <a href=\"" + getUrl() + "\" class=\"collection persecution\">" + getOrdinalString()
					+ "Persecution</a> of "+World.getEntity(targetEntityId).getLink()+location.getLink("in");
	}

	@Override
	public String getShortDescription() {
			return "the " + getOrdinalString() + "Persecution of " + World.getEntity(targetEntityId).getLink()+location.getLink("in");
	}

	public String getName() {
		return "The " + getOrdinalString() + "Persecution of " + World.getEntity(targetEntityId).getLink()+location.getLink("in");
	}
}
