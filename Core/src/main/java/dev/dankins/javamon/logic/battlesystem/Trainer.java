package dev.dankins.javamon.logic.battlesystem;

import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.logic.Party;
import dev.dankins.javamon.logic.PartyWrapper;

public interface Trainer extends dev.dankins.javamon.logic.abstraction.Trainer, TrainerHandler {

	String getName();

	Party getParty();

	@Override
	default dev.dankins.javamon.data.monster.instance.Party getParty_() {
		return new PartyWrapper(getParty());
	}

	String getTrainerLossQuip();

	int getWinnings();

}
