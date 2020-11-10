package dev.dankins.javamon.logic.battlesystem;

import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.logic.Party;

public interface Trainer extends dev.dankins.javamon.logic.abstraction.Trainer, TrainerHandler {

	String getName();

	@Override
	Party getParty();

	String getTrainerLossQuip();

	int getWinnings();

}
