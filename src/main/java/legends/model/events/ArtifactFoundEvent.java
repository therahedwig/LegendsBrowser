package legends.model.events;

import legends.model.World;
import legends.model.events.basic.ArtifactRelatedEvent;
import legends.model.events.basic.HfEvent;
import legends.model.events.basic.SiteRelatedEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("artifact found")
public class ArtifactFoundEvent extends HfEvent implements SiteRelatedEvent, ArtifactRelatedEvent {
	@Xml("artifact_id")
	private int artifactId = -1;
	@Xml("unit_id")
	private int unitId = -1;
	@Xml("site_id,site")
	private int siteId = -1;
	@Xml("site_property_id")
	private int sitePropertyId = -1;

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	@Override
	public boolean isRelatedToArtifact(int artifactId) {
		return this.artifactId == artifactId;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public String getShortDescription() {
		String artifact = World.getArtifact(artifactId).getLink();
		String hf = World.getHistoricalFigure(hfId).getLink();
		String site = "";
		if (siteId != -1)
			site = " in " + World.getSite(siteId).getLink();
		if (sitePropertyId != -1)
			site = " in " + World.getSiteProperty(siteId, sitePropertyId).getType() + site;
		return artifact + " was found" + site + " by " + hf;
	}

}
