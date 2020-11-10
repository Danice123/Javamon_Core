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
import dev.dankins.javamon.battle.display.event.FaintedMonsterEvent;
import dev.dankins.javamon.battle.display.event.ReturnMonsterEvent;
import dev.dankins.javamon.battle.display.event.SendMonsterEvent;
import dev.dankins.javamon.battle.display.event.TargetedEvent;
import dev.dankins.javamon.battle.display.event.TextEvent;
import dev.dankins.javamon.battle.display.event.TrainerEvent;
import dev.dankins.javamon.battle.display.event.attack.AttackEvent;
import dev.dankins.javamon.battle.display.event.attack.TypeChangeEvent;
import dev.dankins.javamon.battle.display.event.attack.TypeEffectivenessEvent;
import dev.dankins.javamon.battle.display.event.attack.UpdateHealthEvent;
import dev.dankins.javamon.data.monster.MonsterSerialized;

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
			switch (event.getType()) {
			case StartBattle:
				System.out.println("Battle Start!");
				break;
			case EndBattle:
				System.out.println("Battle End...");
				break;
			case Attack:
				final AttackEvent ae = (AttackEvent) event;
				System.out.println(
						store.get(ae.key).getCurrentMonster().getMonster().getName() + " Used " + ae.attack.getName());
				break;
			case AttackFailed:
				System.out.println("The attack failed!");
				break;
			case AttackMissed:
				System.out.println("The attack missed!");
				break;
			case AttackDisplay:
				final TextEvent xe = (TextEvent) event;
				System.out.println(xe.getText());
				break;
			case UpdateHealth:
				final UpdateHealthEvent uh = (UpdateHealthEvent) event;
				System.out.println(store.get(uh.key).getCurrentMonster().getMonster().getName()
						+ "'s health changed from " + uh.previousHealth + " to " + uh.currentHealth);
				break;
			case CriticalHit:
				System.out.println("It was a critical hit!");
				break;
			case TypeEffectiveness:
				final TypeEffectivenessEvent te = (TypeEffectivenessEvent) event;
				if (te.effectM() != null) {
					System.out.println(te.effectM());
				}
				break;
			case TypeChange:
				final TypeChangeEvent tc = (TypeChangeEvent) event;
				System.out.println(
						tc.monster.getMonster().getName() + " transformed into the " + tc.type.toString() + " type!");
				break;
			case FaintMonster: {
				final FaintedMonsterEvent e = (FaintedMonsterEvent) event;
				System.out.println(e.trainer.getName() + "'s " + e.monster.getMonster().getName() + " fainted!");
				break;
			}
			case CannotSwitchToFaintedMonster: {
				System.out.println("That monster has no will to fight!");
				break;
			}
			case SendMonster: {
				final SendMonsterEvent e = (SendMonsterEvent) event;
				System.out.println(e.trainer.getName() + " sent out " + e.monster.getMonster().getName());
				break;
			}
			case ReturnMonster: {
				final ReturnMonsterEvent e = (ReturnMonsterEvent) event;
				System.out.println(e.trainer.getName() + " returned " + e.monster.getMonster().getName());
				break;
			}
			case TrainerLoss: {
				final TrainerEvent e = (TrainerEvent) event;
				System.out.println(e.trainer.getName() + " lost the battle!");
				break;
			}
			case EscapeSuccess:
				System.out.println(((TargetedEvent) event).getTarget() + " got away!");
				break;
			case EscapeFailed:
				System.out.println(((TargetedEvent) event).getTarget() + " failed to escape!");
				break;
			case Cancel:
				break;
			default:
				System.out.println("Unhandled event " + event.getType());
			}
		}
	}

	public static void main(final String[] args) {
		new ConsoleBattler().run();
	}

}
