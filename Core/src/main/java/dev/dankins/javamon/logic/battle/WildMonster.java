package dev.dankins.javamon.logic.battle;

import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.data.monster.instance.Party;

public class WildMonster implements TrainerHandler {

	private MonsterHandler currentMonster;

	public WildMonster(MonsterInstance monster) {
		currentMonster = new MonsterHandler(getKey(), monster);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Party getParty_() {
		return null;
	}

	@Override
	public AssetDescriptor<Texture> getImage() {
		return null;
	}

	@Override
	public boolean isTrainer() {
		return false;
	}

	@Override
	public String getTrainerLossQuip() {
		return null;
	}

	@Override
	public int getWinnings() {
		return 0;
	}

	@Override
	public String getKey() {
		return "wild";
	}

	@Override
	public MonsterHandler getCurrentMonster() {
		return currentMonster;
	}

	@Override
	public Action getNextAction(TrainerHandler opponent) {
		return new AttackAction(currentMonster, currentMonster.getMonster().attacks.get(0));
	}

	@Override
	public SwitchAction getNextMonster() throws BattleStateChange {
		return null;
	}

	@Override
	public List<Event> receiveExperience(int exp) {
		return null;
	}

	@Override
	public List<Event> giveMoney(int winnings) {
		return null;
	}

}
