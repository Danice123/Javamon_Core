package dev.dankins.javamon.logic.battlesystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.data.monster.instance.PartyImpl;

public class WildTrainer implements Trainer {

	private final PartyImpl party;
	private final MonsterInstanceImpl wildPokemon;

	public WildTrainer(final MonsterInstanceImpl wildPokemon) {
		this.wildPokemon = wildPokemon;
		party = new PartyImpl();
		party.add(wildPokemon);
	}

	@Override
	public PartyImpl getParty() {
		return party;
	}

	@Override
	public String getName() {
		return wildPokemon.getName();
	}

	@Override
	public Texture getImage(final AssetManager assets) {
		return null;
	}

	@Override
	public Texture getBackImage(final AssetManager assets) {
		return null;
	}

	@Override
	public boolean isTrainerBattle() {
		return false;
	}

	@Override
	public String getTrainerLossQuip() {
		return "";
	}

	@Override
	public int getWinnings() {
		return 0;
	}

	@Override
	public boolean modifyMoney(final int winnings) {
		return false;
	}

}
