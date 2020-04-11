package legends.model.events;

import legends.model.World;
import legends.model.events.basic.EntityRelatedEvent;
import legends.model.events.basic.Event;
import legends.model.events.basic.SiteRelatedEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("merchant")
public class MerchantEvent extends Event implements EntityRelatedEvent, SiteRelatedEvent {
	@Xml("trader_entity_id,source")
	private int sourceId = -1;
	@Xml("depot_entity_id,destination")
	private int destinationId = -1;
	@Xml("site_id,site")
	private int siteId = -1;
	@Xml("all_dead")
	private boolean allDead;
	@Xml("hardship")
	private boolean hardship;
	@Xml("lost_value")
	private boolean lostValue;

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	@Override
	public boolean isRelatedToEntity(int entityId) {
		return this.sourceId == entityId || this.destinationId == entityId;
	}
	
	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}
	
	public boolean allDead() {
		return allDead;
	}
	
	public void setAllDead(boolean allDead) {
		this.allDead = allDead;
	}
	
	
	public boolean hardship() {
		return hardship;
	}
	
	public void setHardship(boolean hardship) {
		this.hardship = hardship;
	}
	
	public boolean lostValue() {
		return lostValue;
	}
	
	public void setLostValue(boolean lostValue) {
		this.lostValue = lostValue;
	}

	@Override
	public String getShortDescription() {
		final String site = World.getSite(siteId).getLink();
		final String source = World.getEntity(sourceId).getLink();
		final String destination = World.getEntity(destinationId).getLink();
		
		String result = String.format("Merchants from %s visited %s at %s", source, destination, site);
		
		if (allDead) {
			result += " and were never heard from again";
		} else if (hardship) {
			result += " and suffered great hardships";
		}
		
		result += ".";
		
		if (lostValue) {
			result +=" They reported irregularities with their goods.";
		}
		
		return result;
	}

}
