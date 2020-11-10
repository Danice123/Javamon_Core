package dev.dankins.javamon.battle;

import dev.dankins.javamon.battle.data.TrainerHandler;

public class TrainerLoss extends BattleStateChange {

	public final TrainerHandler trainer;

	public TrainerLoss(TrainerHandler trainer) {
		this.trainer = trainer;
	}

	private static final long serialVersionUID = 3235740655906188208L;

}
