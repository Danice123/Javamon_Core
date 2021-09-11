package dev.dankins.javamon.battle.console;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import dev.dankins.javamon.battle.MainLogicHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
import dev.dankins.javamon.battle.data.monster.MonsterList;
import dev.dankins.javamon.battle.display.BattlesystemListener;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.attack.UpdateHealthEvent;
import dev.dankins.javamon.data.monster.MonsterSerialized;
import dev.dankins.javamon.data.monster.MultiType;
import dev.dankins.javamon.data.monster.attack.Attack;

public class ConsoleBattler {

	private final MainLoader assets;
	private final MonsterList monsterList;

	private MonsterSerialized monsterA = null;

	public ConsoleBattler() {
		assets = new MainLoader("../../Pokemon_Red");
		while (!assets.update()) {
			System.out.println(assets.getProgress());
		}
		monsterList = assets.get("MonsterList");

		try {
			monsterA = assets.objectMapper.readValue(new File("monsterA.yaml"), MonsterSerialized.class);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		final MonsterInstance mA = new MonsterInstance(monsterList.getMonster(monsterA.monster), monsterA);
		mA.heal();
		final ConsolePlayer A = new ConsolePlayer("A", mA,
				new MonsterInstance(monsterList.getMonster("Bulbasaur"), 5, "Terry", 55555));

		final ConsolePlayer B = new ConsolePlayer("B",
				new MonsterInstance(monsterList.getMonster("Charmander"), 1, "Doug", 55555),
				new MonsterInstance(monsterList.getMonster("Squirtle"), 1, "Doug", 55555));

		final HashMap<String, TrainerHandler> store = Maps.newHashMap();
		store.put("A", A);
		store.put("B", B);

		final MainLogicHandler bs = new MainLogicHandler(new Listener(store), A, B);

		new Thread(bs).start();
	}

	private class Listener implements BattlesystemListener {

		private final Map<String, TrainerHandler> store;

		public Listener(final Map<String, TrainerHandler> store) {
			this.store = store;
		}

		@Override
		public void sendEvent(final Event event) {
			switch (event.type) {
			case StartBattle:
				System.out.println("Battle Start!");
				break;
			case EndBattle:
				System.out.println("Battle End...");
				break;
			case Attack:
				Attack attack = (Attack) event.parameters.get("Attack");
				System.out.println(store.get(event.parameters.get("Key")).getCurrentMonster().getMonster().getName()
						+ " Used " + attack.getName());
				break;
			case AttackFailed:
				System.out.println("The attack failed!");
				break;
			case AttackMissed:
				System.out.println("The attack missed!");
				break;
			case AttackDisplay:
				System.out.println(event.parameters.get("Text"));
				break;
			case UpdateHealth:
				final UpdateHealthEvent uh = (UpdateHealthEvent) event;
				System.out.println(store.get(event.parameters.get("Key")).getCurrentMonster().getMonster().getName()
						+ "'s health changed from " + event.parameters.get("PreviousHealth") + " to "
						+ event.parameters.get("CurrentHealth"));
				break;
			case CriticalHit:
				System.out.println("It was a critical hit!");
				break;
			case TypeEffectiveness:
				if (event.parameters.get("Text") != null) {
					System.out.println(event.parameters.get("Text"));
				}
				break;
			case TypeChange: {
				System.out.println(store.get(event.parameters.get("Key")).getCurrentMonster().getMonster().getName()
						+ " transformed into the " + ((MultiType) event.parameters.get("Type")).toString() + " type!");
				break;
			}
			case FaintMonster: {
				System.out.println(event.parameters.get("Target") + "'s "
						+ store.get(event.parameters.get("Target")).getCurrentMonster().getMonster().getName()
						+ " fainted!");
				break;
			}
			case CannotSwitchToFaintedMonster: {
				System.out.println("That monster has no will to fight!");
				break;
			}
			case SendMonster: {
				System.out.println(event.parameters.get("Target") + " sent out "
						+ store.get(event.parameters.get("Target")).getCurrentMonster().getMonster().getName());
				break;
			}
			case ReturnMonster: {
				System.out.println(event.parameters.get("Target") + " returned "
						+ store.get(event.parameters.get("Target")).getCurrentMonster().getMonster().getName());
				break;
			}
			case TrainerLoss: {
				System.out.println(event.parameters.get("Target") + " lost the battle!");
				break;
			}
			case EscapeSuccess:
				System.out.println(event.parameters.get("Target") + " got away!");
				break;
			case EscapeFailed:
				System.out.println(event.parameters.get("Target") + " failed to escape!");
				break;
			case Cancel:
				break;
			default:
				System.out.println("Unhandled event " + event.type);
			}
		}
	}

	public static void main(final String[] args) {
		new ConsoleBattler().run();
	}

}
