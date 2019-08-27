package dev.dankins.javamon.logic.battlesystem;

import dev.dankins.javamon.data.monster.instance.PartyImpl;

public interface Trainer extends dev.dankins.javamon.logic.abstraction.Trainer {

	String getName();

	@Override
	PartyImpl getParty();

	String getTrainerLossQuip();

	int getWinnings();

}
