package legends.model.events;

import legends.model.World;
import legends.model.events.basic.EntityRelatedEvent;
import legends.model.events.basic.Event;
import legends.model.events.basic.HfRelatedEvent;
import legends.model.events.basic.SiteRelatedEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("entity expels hf")
public class EntityExpelsHf extends Event
	implements EntityRelatedEvent, SiteRelatedEvent, HfRelatedEvent {
	@Xml("entity_id")
	private int entityId = -1;
	@Xml("hfid")
	private int hfId = -1;
	@Xml("site_id")
	private int siteId = -1;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getHfId() {
		return hfId;
	}

	public void setHfId(int hfId) {
		this.hfId = hfId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public String getShortDescription() {
		final String site = World.getSite(siteId).getLink();
		final String entity = World.getEntity(entityId).getLink();
		final String hf = World.getHistoricalFigure(hfId).getLink();
		
		return String.format("%s expelled %s from %s", entity, hf, site);
	}

	@Override
	public boolean isRelatedToHf(int hfId) {
		return this.hfId == hfId;
	}

	@Override
	public boolean isRelatedToEntity(int entityId) {
		return this.entityId == entityId;
	}

}