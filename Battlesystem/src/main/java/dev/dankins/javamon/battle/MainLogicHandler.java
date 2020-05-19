package dev.dankins.javamon.battle;

import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.BattlesystemHook;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.attack.effect.CustomStatus;
import dev.dankins.javamon.battle.display.BattlesystemListener;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.GenericEvent;
import dev.dankins.javamon.battle.display.event.TargetedEvent;

public class MainLogicHandler implements Runnable {

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
		listener.sendEvent(new GenericEvent(EventType.StartBattle));
		do {
			final Action alphaAct = getActionFromHandler(alpha, bravo);
			final Action bravoAct = getActionFromHandler(bravo, alpha);

			// handleRun(alpha)
			// handleRun(bravo)

			handleSwitch(alphaAct, alpha.getKey());
			handleSwitch(bravoAct, bravo.getKey());

			// handleItem(alpha)
			// handleItem(bravo)

			if (AttackAction.class.isInstance(alphaAct)
					&& AttackAction.class.isInstance(bravoAct)) {
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

			handlePostTurn(alpha);
			handlePostTurn(bravo);
		} while (true);
	}

	private Action getActionFromHandler(final TrainerHandler handler,
			final TrainerHandler opponent) {
		// TODO: Setup allowing switching/items/running in some multi-turn cases
		if (handler.getCurrentMonster().isUsingMultiTurnMove()) {
			return new AttackAction(handler.getCurrentMonster(),
					handler.getCurrentMonster().getLastUsedMove());
		}
		return handler.getNextAction(opponent);
	}

	private void handleSwitch(final Action action, final String key) {
		if (SwitchAction.class.isInstance(action)) {
			listener.sendEvent(new TargetedEvent(EventType.ReturnMonster, key));
			// action.execute();
			listener.sendEvent(new TargetedEvent(EventType.SendMonster, key));
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
				alphaAct.execute(bravo.getCurrentMonster())
						.forEach(event -> listener.sendEvent(event));
				bravoAct.execute(alpha.getCurrentMonster())
						.forEach(event -> listener.sendEvent(event));
			} else {
				bravoAct.execute(alpha.getCurrentMonster())
						.forEach(event -> listener.sendEvent(event));
				alphaAct.execute(bravo.getCurrentMonster())
						.forEach(event -> listener.sendEvent(event));
			}
		}
	}

	private void handlePostTurn(final TrainerHandler handler) {
		for (final CustomStatus status : handler.getCurrentMonster().getTemporaryStatuses()) {
			status.apply(BattlesystemHook.onTurnEnd).forEach(event -> listener.sendEvent(event));
		}
		handler.getCurrentMonster().moveHitByThisTurn = null;
	}
}