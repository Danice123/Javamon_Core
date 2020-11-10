package dev.dankins.javamon.logic.battlesystem;

import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.data.monster.instance.PartyImpl;

public interface Trainer extends dev.dankins.javamon.logic.abstraction.Trainer, TrainerHandler {

	String getName();

	@Override
	PartyImpl getParty();

	String getTrainerLossQuip();

	int getWinnings();

}
