package legends.model.collections;

import java.util.ArrayList;
import java.util.List;

import legends.helper.EventHelper;
import legends.model.collections.basic.EventCollection;
import legends.model.events.basic.EventLocation;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlComponent;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("battle")
public class BattleCollection extends EventCollection {
	@Xml("name")
	private String name;
	@Xml("war_eventcol")
	private int warEventCol = -1;
	@XmlComponent
	private EventLocation location = new EventLocation();
	@Xml(value = "attacking_hfid", elementClass = Integer.class, multiple = true)
	private List<Integer> attackingHfIds = new ArrayList<>();
	@Xml(value = "defending_hfid", elementClass = Integer.class, multiple = true)
	private List<Integer> defendingHfIds = new ArrayList<>();
	@Xml(value = "noncom_hfid", elementClass = Integer.class, multiple = true)
	private List<Integer> nocomHfIds = new ArrayList<>();

	@XmlComponent(prefix = "attacking_squad_", elementClass = BattleCollectionSquad.class, multiple = true, consumeOn = "site")
	private List<BattleCollectionSquad> attackingSquads = new ArrayList<>();
	@XmlComponent(prefix = "defending_squad_", elementClass = BattleCollectionSquad.class, multiple = true, consumeOn = "site")
	private List<BattleCollectionSquad> defendingSquads = new ArrayList<>();

	@Xml("outcome")
	private String outcome;
	
	@Xml("individual_merc")
	private boolean individualMercenary; // Unsure
	@Xml("company_merc")
	private boolean companyMercenary; // Unsure
	@Xml("attacking_merc_enid")
	private int attackingMercenaryEntityId = -1; // Used for hiring attackers
	@Xml("defending_merc_enid")
	private int defendingMercenaryEntityId = -1; // Used for hiring defenders
	@Xml("a_support_merc_enid")
	private int supportingMercenaryEntityId = -1; //Used for hiring scouts.
	@Xml("a_support_merc_hfid")
	private int supportingMercenaryHfId = -1; // Unsure
	@Xml("attacking_squad_animated")
	private boolean attackingSquadAnimated; // Whether the attackers were undead.
	@Xml("defending_squad_animated")
	private boolean defendingSquadAnimated; // Whether the defenders were undead.

	public String getName() {
		return EventHelper.name(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWarEventCol() {
		return warEventCol;
	}

	public void setWarEventCol(int warEventCol) {
		this.warEventCol = warEventCol;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public EventLocation getLocation() {
		return location;
	}

	public List<Integer> getAttackingHfIds() {
		return attackingHfIds;
	}

	public List<Integer> getDefendingHfIds() {
		return defendingHfIds;
	}

	public List<Integer> getNocomHfIds() {
		return nocomHfIds;
	}

	public List<BattleCollectionSquad> getAttackingSquads() {
		return attackingSquads;
	}

	public List<BattleCollectionSquad> getDefendingSquads() {
		return defendingSquads;
	}

	@Override
	public String getShortDescription() {
		return getLink() + " occurred";
	}

	@Override
	public String getLink() {
		return "<a href=\"" + getUrl() + "\" class=\"collection battle\">" + getName() + "</a>";
	}

}
