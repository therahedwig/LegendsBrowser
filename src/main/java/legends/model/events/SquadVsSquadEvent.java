
package legends.model.events;

import legends.model.EntitySquadLink;
import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.EventLocation;
import legends.model.events.basic.HfRelatedEvent;
import legends.model.events.basic.LocalEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlComponent;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("squad vs squad")
public class SquadVsSquadEvent extends Event implements HfRelatedEvent, LocalEvent {
	@Xml("a_hfid")
	private int aHfId = -1;
	@Xml("a_leader_hfid")
	private int aLeaderHfId = -1;
	@Xml("a_leadership_roll")
	private int aLeadershipRoll = -1;
	@Xml("a_squad_id")
	private int aSquadId = -1;
	@Xml("d_squad_id")
	private int dSquadId = -1; // other dwarf fort related?
	@Xml("d_interaction")
	private int dInteraction = -1; // lieutenant related?
	@Xml("d_effect")
	private int dEffect = -1; // lieutenant related?
	@Xml("d_race")
	private int dRace = -1;
	@Xml("d_number")
	private int dNumber = -1;
	@Xml("d_slain")
	private int dSlain = -1;
	@XmlComponent
	private EventLocation location = new EventLocation();

	@Override
	public EventLocation getLocation() {
		return location;
	}

	@Override
	public boolean isRelatedToHf(int hfId) {
		return aHfId == hfId || aLeaderHfId == hfId;
	}

	@Override
	public String getShortDescription() {
		String slay = "";
		if (dSlain > 0)
			if (dNumber == dSlain)
				slay = ", slaying them";
			else
				slay = String.format(", slaying %d", dSlain);
		String attackers = World.getHistoricalFigure(aHfId).getLink();
		
		if (aSquadId>0) {
			String entity = "";
			for (EntitySquadLink squad : World.getHistoricalFigure(aHfId).getEntitySquadLinks()) {
				if (squad.getSquadId() == aSquadId) {
					entity = World.getEntity(squad.getEntityId()).getLink();
				}
			}
			attackers = String.format("Squad %s", aSquadId);
			if (!entity.isEmpty()) {
				attackers = String.format("%s of %s", attackers, entity);
			}
		}
		
		if (aLeaderHfId>0) {
			String leaderShipQuality = " led";
			if (aLeadershipRoll < 50) {
				leaderShipQuality = " poorly led";
			} else if (aLeadershipRoll < 100){
				leaderShipQuality = " led";
			} else if (aLeadershipRoll < 150){
				leaderShipQuality = " ably led";
			} else if (aLeadershipRoll < 200){
				leaderShipQuality = " inspiringly led";
			} else {
				leaderShipQuality = " brilliantly led";
			}
			attackers += leaderShipQuality + " by " + World.getHistoricalFigure(aLeaderHfId).getLink();
		}
		
		String defenders = "UNKNOWN_RACE_" + dRace;
		
		return String.format("%s clashed with %d %s%s%s", attackers, dNumber,
				defenders, location.getLink("in"), slay);
	}

}
