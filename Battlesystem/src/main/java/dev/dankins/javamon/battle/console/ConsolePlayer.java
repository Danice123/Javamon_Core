package dev.dankins.javamon.battle.console;

import java.util.Scanner;

import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;

public class ConsolePlayer implements TrainerHandler {

	private final String key;
	private final MonsterHandler monster;
	private final Scanner in = new Scanner(System.in);

	public ConsolePlayer(final String key, final MonsterInstance monster) {
		this.key = key;
		this.monster = new MonsterHandler(key, monster);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public MonsterHandler getCurrentMonster() {
		return monster;
	}

	@Override
	public Action getNextAction(final TrainerHandler opponent) {
		System.out.println("---------------------------------- Player: " + key);
		System.out.println(monster.getMonster().getName() + " - "
				+ monster.getMonster().getCurrentHealth() + "/" + monster.getMonster().getHealth());
		System.out.print("What action?\n1: Attack\n2: Switch\n3: Item\n4: Run\n");
		switch (in.nextInt()) {
		case 1:
			return getAttack();
		default:
			return getNextAction(opponent);
		}
	}

	private AttackAction getAttack() {
		System.out.println("Choose an attack:");
		for (int i = 0; i < monster.getMonster().getAttacks().size(); i++) {
			System.out.println(i + 1 + ": " + monster.getMonster().getAttacks().get(i).getName());
		}

		final int choice = in.nextInt() - 1;
		if (choice < monster.getMonster().getAttacks().size() && choice >= 0) {
			return new AttackAction(monster, monster.getMonster().getAttacks().get(choice));
		}
		return getAttack();
	}

}
