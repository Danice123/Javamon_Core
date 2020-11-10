package dev.dankins.javamon.logic.entity;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
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

	@Override
	public MonsterHandler getCurrentMonster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getNextAction(TrainerHandler opponent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SwitchAction getNextMonster() throws BattleStateChange {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
