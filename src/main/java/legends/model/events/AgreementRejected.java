package legends.model.events;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.SiteRelatedEvent;

import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

// This one showed up on an event asking for the unconditional surrender of a site by another site.

@XmlSubtype("agreement rejected")
public class AgreementRejected extends Event implements SiteRelatedEvent {

	@Xml("site_id")
	private int siteId = -1;

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	@Override
	public String getShortDescription() {
		final String site = World.getSite(siteId).getLink();
		return "an agreement was rejected in "+site;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}
}