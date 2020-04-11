package legends.model.events;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.EntityRelatedEvent;
import legends.model.events.basic.SiteRelatedEvent;

import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("entity fled site")
public class EntityFledSite extends Event implements EntityRelatedEvent, SiteRelatedEvent {
	
	@Xml("fled_civ_id")
	private int entityId = -1;
	@Xml("site_id")
	private int siteId = -1;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	@Override
	public String getShortDescription() {
		final String site = World.getSite(siteId).getLink();
		final String entity = World.getEntity(entityId).getLink();
		
		return String.format("%s fled from %s", entity, site);
		// This is not a refugee event...
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public boolean isRelatedToEntity(int entityId) {
		return this.entityId == entityId;
	}
}