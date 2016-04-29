package com.happylittlevillage.rituals;

import java.util.ArrayList;

public class RitualNode {
	private ArrayList<RitualNode> prerequisiteRituals = new ArrayList<RitualNode>();
	private boolean activated = false;
	private Ritual ritual;

	RitualNode(Ritual ritual) {
		this.ritual = ritual;
	}

	RitualNode(Ritual ritual, ArrayList<RitualNode> prerequisiteRituals) {
		this.ritual = ritual;
		this.prerequisiteRituals = prerequisiteRituals;
	}

	public boolean activate() {
		if (prerequisiteRituals.size() == 0) {
			activated = true;
			return true;
		} else {
			for (RitualNode ritualNode : prerequisiteRituals) {
				if (!ritualNode.isActivated()) {
					activated = false;
					return false;
				}
			}
			activated = true;
			return true;
		}
	}


	public ArrayList<RitualNode> getPrerequisiteRituals() {
		return prerequisiteRituals;
	}

	public Ritual getRitual() {
		return ritual;
	}

	public void setPrerequisites(RitualNode ritualNode) {
		prerequisiteRituals.add(ritualNode);
	}

	// check if this ritual is activated or not
	public boolean isActivated() {
		return activated;
	}

	@Override
	public String toString() {
		String prerequisite = "";
		for (RitualNode node : prerequisiteRituals) {
			prerequisite += node.getRitual().getName() + " ";
		}
		return "Ritual name is " + ritual.getName() + " number of prerequisites is " + prerequisiteRituals.size() +
				" \nprerequisiteRituals:" + prerequisite +
				", \nactivated: " + activated +
				'}';
	}
}
