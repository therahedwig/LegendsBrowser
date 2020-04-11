package legends.model.events;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.HfRelatedEvent;
import legends.model.events.basic.SiteRelatedEvent;

import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("agreement concluded")
public class AgreementConcluded extends Event {


	@Override
	public String getShortDescription() {
		
		return "an agreement concluded.";
	}
}