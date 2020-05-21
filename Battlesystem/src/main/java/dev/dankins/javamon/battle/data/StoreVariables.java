package dev.dankins.javamon.battle.data;

public enum StoreVariables {

	ChosenAttack("ChosenAttack"), DamageTaken("DamageTaken");

	public final String value;

	private StoreVariables(final String value) {
		this.value = value;
	}
}
