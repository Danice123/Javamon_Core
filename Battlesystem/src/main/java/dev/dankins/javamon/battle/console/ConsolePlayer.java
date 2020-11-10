package dev.dankins.javamon.battle.console;

import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.TrainerLoss;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.RunAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;

public class ConsolePlayer implements TrainerHandler {

	private final String key;
	private final MonsterHandler[] monsters;
	private final Scanner in = new Scanner(System.in);

	private int monsterIndex = 0;
	private int escapeAttempts = 0;

	public ConsolePlayer(final String key, final MonsterInstance... monsters) {
		this.key = key;
		this.monsters = Lists.newArrayList(monsters).stream().map(monster -> new MonsterHandler(key, monster))
				.collect(Collectors.toList()).toArray(new MonsterHandler[0]);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getName() {
		return key;
	}

	@Override
	public MonsterHandler getCurrentMonster() {
		return monsters[monsterIndex];
	}

	@Override
	public Action getNextAction(final TrainerHandler opponent) {
		System.out.println("---------------------------------- Player: " + key);
		System.out.println(
				getCurrentMonster().getMonster().getName() + " - " + getCurrentMonster().getMonster().getCurrentHealth()
						+ "/" + getCurrentMonster().getMonster().getHealth());
		System.out.print("What action?\n1: Attack\n2: Switch\n3: Item\n4: Run\n");
		switch (in.nextInt()) {
		case 1:
			return getAttack();
		case 2:
			return getSwitch();
		case 4:
			escapeAttempts++;
			return new RunAction(getCurrentMonster(), escapeAttempts);
		default:
			return getNextAction(opponent);
		}
	}

	@Override
	public SwitchAction getNextMonster() throws BattleStateChange {
		boolean hasMonsterLeft = false;
		for (MonsterHandler monster : monsters) {
			if (monster.getMonster().getCurrentHealth() > 0) {
				hasMonsterLeft = true;
			}
		}
		if (!hasMonsterLeft) {
			throw new TrainerLoss(this);
		}

		return getSwitch();
	}

	private AttackAction getAttack() {
		System.out.println("Choose an attack:");
		for (int i = 0; i < getCurrentMonster().getMonster().getAttacks().size(); i++) {
			System.out.println(i + 1 + ": " + getCurrentMonster().getMonster().getAttacks().get(i).getName());
		}

		final int choice = in.nextInt() - 1;
		if (choice < getCurrentMonster().getMonster().getAttacks().size() && choice >= 0) {
			return new AttackAction(getCurrentMonster(), getCurrentMonster().getMonster().getAttacks().get(choice));
		}
		return getAttack();
	}

	private SwitchAction getSwitch() {
		System.out.println("Choose a monster:");
		for (int i = 0; i < monsters.length; i++) {
			System.out.println(i + 1 + ": " + monsters[i].getMonster().getName());
		}

		final int choice = in.nextInt() - 1;
		if (choice < monsters.length && choice >= 0) {
			return new SwitchAction(monsters[choice], (v) -> monsterIndex = choice);
		}

		return getSwitch();
	}
}
