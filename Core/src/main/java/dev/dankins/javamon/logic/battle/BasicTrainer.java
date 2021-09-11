package dev.dankins.javamon.logic.battle;

import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.TrainerLoss;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.data.monster.instance.Party;
import dev.dankins.javamon.logic.PartyWrapper;
import dev.dankins.javamon.logic.entity.Trainer;

public class BasicTrainer implements TrainerHandler {

	private final Trainer trainer;

	private MonsterHandler currentMonster;

	public BasicTrainer(Trainer trainer) {
		this.trainer = trainer;
		currentMonster = new MonsterHandler(getKey(), trainer.getParty().firstPokemon());
	}

	@Override
	public String getKey() {
		return trainer.getName();
	}

	@Override
	public String getName() {
		return trainer.getName();
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
		if (trainer.getParty().hasMonsterLeft()) {
			MonsterHandler newMonster = new MonsterHandler(getKey(), trainer.getParty().firstPokemon());
			return new SwitchAction(newMonster, (v) -> currentMonster = newMonster);
		}
		throw new TrainerLoss(this);
	}

	@Override
	public Party getParty_() {
		return new PartyWrapper(trainer.getParty());
	}

	@Override
	public AssetDescriptor<Texture> getImage() {
		return trainer.getImage();
	}

	@Override
	public boolean isTrainer() {
		return true;
	}

	@Override
	public String getTrainerLossQuip() {
		return trainer.getTrainerLossQuip();
	}

	@Override
	public List<Event> receiveExperience(int exp) {
		return Lists.newArrayList();
	}

	@Override
	public int getWinnings() {
		return trainer.getWinnings();
	}

	@Override
	public List<Event> giveMoney(int winnings) {
		return Lists.newArrayList();
	}

}
