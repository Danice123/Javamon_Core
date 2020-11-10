package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.action.RunAction;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.display.screen.menu.PlayerBattleMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.entity.Player;

public class PlayerBattleHandler extends MenuHandler<PlayerBattleMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PlayerBattleMenu.class;
	static public Class<? extends PlayerBattleMenu> Menu_Class;

	private final Player player;
	private final MonsterHandler monsterHandler;

	private Action chosenAction;

	public PlayerBattleHandler(final Game game, final Player player, MonsterHandler monsterHandler) {
		super(game, Menu_Class);
		this.player = player;
		this.monsterHandler = monsterHandler;
		menu.setupMenu(monsterHandler.getMonster());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getAction()) {
		case Item:
			final ChooseItemHandler chooseItemHandler = new ChooseItemHandler(game, player.getInventory());
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}
			final Item chosenItem = chooseItemHandler.getChosenItem();
			if (chosenItem.isUsableInBattle()) {
				if (chosenItem.isConsumedOnUse()) {
					if (chosenItem instanceof ItemStack) {
						final ItemStack stack = (ItemStack) chosenItem;
						stack.remove(1);
						if (stack.size() <= 0) {
							player.getInventory().removeItem(chosenItem);
						}
					} else {
						player.getInventory().removeItem(chosenItem);
					}
				}
				// chosenAction = new BattleAction(chosenItem);
				return false;
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "You can't use that now!");
				chatboxHandler.waitAndHandle();
			}
			return true;
		case Switch:
			final ChoosePokemonHandler choosePokemonInBattleHandler = new ChoosePokemonHandler(game,
					monsterHandler.getMonster(), PartyMenuType.Switch, true);
			choosePokemonInBattleHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			if (choosePokemonInBattleHandler.getChosenPokemon() != null) {
				chosenAction = new SwitchAction(
						new MonsterHandler("key", choosePokemonInBattleHandler.getChosenPokemon()), (v) -> {
						});
				return false;
			} else {
				return true;
			}
		case Attack:
			chosenAction = new AttackAction(monsterHandler,
					monsterHandler.getMonster().getAttacks().get(menu.getActionChoice()));
			return false;
		case Run:
			chosenAction = new RunAction(monsterHandler, 0); // TODO: Track run attempts
			return false;
		default:
			return true;
		}
	}

	public Action getChosenAction() {
		return chosenAction;
	}
}
