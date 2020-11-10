package dev.dankins.javamon.logic.battlesystem;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.logic.Party;

public class WildTrainer implements Trainer {

	private final Party party;
	private final MonsterInstance wildPokemon;

	public WildTrainer(final MonsterInstance wildPokemon) {
		this.wildPokemon = wildPokemon;
		party = new Party();
		party.add(wildPokemon);
	}

	@Override
	public Party getParty() {
		return party;
	}

	@Override
	public String getName() {
		return wildPokemon.getName();
	}

	@Override
	public AssetDescriptor<Texture> getImage() {
		return new AssetDescriptor<Texture>("pokemon/" + wildPokemon.getBaseMonster().number + ".png", Texture.class);
	}

	@Override
	public boolean isTrainer() {
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

}
