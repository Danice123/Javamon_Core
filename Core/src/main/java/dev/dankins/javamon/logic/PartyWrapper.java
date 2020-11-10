package dev.dankins.javamon.logic;

import java.util.Iterator;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class PartyWrapper implements dev.dankins.javamon.data.monster.instance.Party {

	private final Party party;

	public PartyWrapper(Party party) {
		this.party = party;
	}

	@Override
	public Iterator<MonsterInstance> iterator() {
		return party.stream().map(monster -> (MonsterInstance) monster).iterator();
	}

}
