package legends.model.events;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.SiteRelatedEvent;
import legends.model.events.basic.StructureRelatedEvent;

import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("hf carouse")
public class HfCarouse extends Event
	implements SiteRelatedEvent, StructureRelatedEvent {
	@Xml("site_id")
	private int siteId = -1;
	@Xml("structure_id")
	private int structureId = -1;
	@Xml("group_hfid")
	private int groupHfId = -1;
	@Xml("subregion_id")
	private int subRegionId = -1;
	@Xml("feature_layer_id")
	private int featureLayerId = -1;

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public int getStructureId() {
		return structureId;
	}

	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public String getShortDescription() {
		final String group = "Unknown Group "+groupHfId;
		
		String location = World.getSite(siteId).getLink();
		if (structureId != -1) {
			location = World.getStructure(structureId, siteId).getLink() + " in " + World.getSite(siteId).getLink();;
		}
		return String.format("%s caroused in %s.", group, location);
	}
	
	@Override
	public boolean isRelatedToStructure(int structureId, int siteId) {
		return (this.structureId == structureId && this.siteId == siteId);
	}
}