package legends.model.events;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.HfRelatedEvent;
import legends.model.events.basic.SiteRelatedEvent;
import legends.model.events.basic.ArtifactRelatedEvent;
import legends.model.events.basic.StructureRelatedEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("hf asked about artifact")
public class HfAskedAboutArtifact extends Event
	implements ArtifactRelatedEvent, SiteRelatedEvent, HfRelatedEvent, StructureRelatedEvent {
	@Xml("hist_fig_id")
	private int hfId = -1;
	@Xml("site_id")
	private int siteId = -1;
	@Xml("artifact_id")
	private int artifactId = -1;
	@Xml("structure_id")
	private int structureId = -1;

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
	
	public int getStructureId() {
		return structureId;
	}

	public void setStructureId(int structureId) {
		this.structureId = structureId;
	}
	
	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public String getShortDescription() {
		final String hf = World.getHistoricalFigure(hfId).getLink();
		final String artifact = World.getArtifact(artifactId).getLink();
		
		String location = World.getSite(siteId).getLink();
		if (structureId != -1) {
			location = World.getStructure(structureId, siteId).getLink() + " in " + World.getSite(siteId).getLink();;
		}
		
		return String.format("%s asked about %s in %s.", hf, artifact, location);
	}

	@Override
	public boolean isRelatedToHf(int hfId) {
		return this.hfId == hfId;
	}

	@Override
	public boolean isRelatedToArtifact(int artifactId) {
		return this.artifactId == artifactId;
	}

	@Override
	public boolean isRelatedToStructure(int structureId, int siteId) {
		return (this.structureId == structureId && this.siteId == siteId);
	}

}