package dev.dankins.javamon.battle;

import java.util.List;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.RunAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.BattlesystemHook;
import dev.dankins.javamon.battle.data.StoreVariables;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.attack.effect.CustomStatus;
import dev.dankins.javamon.battle.display.BattlesystemListener;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TargetedEvent;

public class MainLogicHandler implements Runnable {

	public boolean battleIsOver = false;

	private final BattlesystemListener listener;
	private final TrainerHandler alpha;
	private final TrainerHandler bravo;

	public MainLogicHandler(final BattlesystemListener listener, final TrainerHandler trainerOne,
			final TrainerHandler trainerTwo) {
		this.listener = listener;
		alpha = trainerOne;
		bravo = trainerTwo;
	}

	@Override
	public void run() {
		listener.sendEvent(new Event(EventType.StartBattle));
		try {
			do {
				final Action alphaAct = getActionFromHandler(alpha, bravo);
				final Action bravoAct = getActionFromHandler(bravo, alpha);

				if (RunAction.class.isInstance(alphaAct)) {
					alphaAct.execute(bravo.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
				}
				if (RunAction.class.isInstance(bravoAct)) {
					bravoAct.execute(alpha.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
				}

				handleSwitch(alphaAct, alpha);
				handleSwitch(bravoAct, bravo);

				// handleItem(alpha)
				// handleItem(bravo)

				if (AttackAction.class.isInstance(alphaAct) && AttackAction.class.isInstance(bravoAct)) {
					handleBattle((AttackAction) alphaAct, (AttackAction) bravoAct);
				} else {
					if (AttackAction.class.isInstance(alphaAct)) {
						((AttackAction) alphaAct).execute(bravo.getCurrentMonster())
								.forEach(event -> listener.sendEvent(event));
					}
					if (AttackAction.class.isInstance(bravoAct)) {
						((AttackAction) bravoAct).execute(alpha.getCurrentMonster())
								.forEach(event -> listener.sendEvent(event));
					}
				}

				handlePostTurn(alpha, bravo);
				handlePostTurn(bravo, alpha);
			} while (true);
		} catch (MonsterRan monsterRan) {
			listener.sendEvent(new TargetedEvent(EventType.EscapeSuccess, monsterRan.runner.getMonster().getName()));
		} catch (TrainerLoss trainerLoss) {
			listener.sendEvent(new TargetedEvent(EventType.TrainerLoss, trainerLoss.trainer.getKey()));

			TrainerHandler winner;
			if (trainerLoss.trainer == alpha) {
				winner = bravo;
			} else {
				winner = alpha;
			}

			for (Event event : winner.rewardMoney(trainerLoss.trainer.getWinnings())) {
				listener.sendEvent(event);
			}
		} catch (MonsterLoss monsterLoss) {

		} catch (BattleStateChange change) {
			System.out.println("Unhandled Battle State Change");
		}
		listener.sendEvent(new Event(EventType.EndBattle));
		battleIsOver = true;
	}

	private Action getActionFromHandler(final TrainerHandler handler, final TrainerHandler opponent) {
		// TODO: Setup allowing switching/items/running in some multi-turn cases
		if (handler.getCurrentMonster().isUsingMultiTurnMove()) {
			return new AttackAction(handler.getCurrentMonster(), handler.getCurrentMonster().getLastUsedMove());
		}

		final Action action = handler.getNextAction(opponent);

		// Handle canceling the choice of an attack (Disable)
		if (AttackAction.class.isInstance(action)) {
			final List<Event> events = Lists.newArrayList();
			handler.getCurrentMonster().getStore().put(StoreVariables.ChosenAttack.value,
					((AttackAction) action).attackInstance);
			for (final CustomStatus status : handler.getCurrentMonster().getTemporaryStatuses()) {
				events.addAll(status.apply(BattlesystemHook.onAttackChoice));
			}
			handler.getCurrentMonster().getStore().remove(StoreVariables.ChosenAttack.value);

			events.forEach(event -> listener.sendEvent(event));
			if (events.stream().anyMatch(event -> event.type == EventType.Cancel)) {
				return getActionFromHandler(handler, opponent);
			}
		}
		return action;
	}

	private void handleSwitch(final Action action, final TrainerHandler trainer) throws BattleStateChange {
		if (SwitchAction.class.isInstance(action)) {
			listener.sendEvent(new TargetedEvent(EventType.ReturnMonster, trainer.getKey()));
			action.execute(null);
			listener.sendEvent(new TargetedEvent(EventType.SendMonster, trainer.getKey()));
		}
	}

	private void handleBattle(final AttackAction alphaAct, final AttackAction bravoAct) {
		if (alphaAct.compareTo(bravoAct) > 0) {
			alphaAct.execute(bravo.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
			bravoAct.execute(alpha.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
		} else if (alphaAct.compareTo(bravoAct) < 0) {
			bravoAct.execute(alpha.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
			alphaAct.execute(bravo.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
		} else {
			if (RandomNumberGenerator.random.nextBoolean()) {
				alphaAct.execute(bravo.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
				bravoAct.execute(alpha.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
			} else {
				bravoAct.execute(alpha.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
				alphaAct.execute(bravo.getCurrentMonster()).forEach(event -> listener.sendEvent(event));
			}
		}
	}

	private void handlePostTurn(final TrainerHandler handler, final TrainerHandler opponent) throws BattleStateChange {
		for (final CustomStatus status : handler.getCurrentMonster().getTemporaryStatuses()) {
			status.apply(BattlesystemHook.onTurnEnd).forEach(event -> listener.sendEvent(event));
		}
		handler.getCurrentMonster().moveHitByThisTurn = null;

		if (handler.getCurrentMonster().getMonster().getCurrentHealth() == 0) {
			listener.sendEvent(new TargetedEvent(EventType.FaintMonster, handler.getKey()));
			// Handle trainer battle multiplier, lucky egg, affection?, multiple pokemon in
			// battle, exp share/all, OT vs trade, post-evolve pokemon
			for (Event event : opponent.rewardEXP(handler.getCurrentMonster().getMonster().getExpDrop())) {
				listener.sendEvent(event);
			}

			if (!handler.isTrainer()) {
				throw new MonsterLoss();
			}

			SwitchAction forcedSwitch = handler.getNextMonster();
			while (forcedSwitch.target.getMonster().getCurrentHealth() == 0) {
				listener.sendEvent(new Event(EventType.CannotSwitchToFaintedMonster));
				forcedSwitch = handler.getNextMonster();
			}
			forcedSwitch.execute(null);
			listener.sendEvent(new TargetedEvent(EventType.SendMonster, handler.getKey()));
		}
	}
}
