package dev.dankins.javamon.logic.menu;

import com.github.danice123.javamon.logic.battlesystem.Battlesystem;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.display.screen.menu.PlayerBattleMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.battlesystem.BattleAction;

public class PlayerBattleHandler extends MenuHandler<PlayerBattleMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PlayerBattleMenu.class;
	static public Class<? extends PlayerBattleMenu> Menu_Class;

	private final Battlesystem system;
	private BattleAction chosenAction;

	public PlayerBattleHandler(final Game game, final Battlesystem system) {
		super(game, Menu_Class);
		this.system = system;
		menu.setupMenu(system.getPlayerMonster());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getAction().action) {
		case Item:
			final ChooseItemHandler chooseItemHandler = new ChooseItemHandler(game,
					game.getPlayer().getInventory());
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
							game.getPlayer().getInventory().removeItem(chosenItem);
						}
					} else {
						game.getPlayer().getInventory().removeItem(chosenItem);
					}
				}
				chosenAction = new BattleAction(chosenItem);
				return false;
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"You can't use that now!");
				chatboxHandler.waitAndHandle();
			}
			return true;
		case Switch:
			final ChoosePokemonHandler choosePokemonInBattleHandler = new ChoosePokemonHandler(game,
					system.getPlayerMonster(), PartyMenuType.Switch, true);
			choosePokemonInBattleHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			if (choosePokemonInBattleHandler.getChosenPokemon() != null) {
				chosenAction = new BattleAction(choosePokemonInBattleHandler.getChosenPokemon());
				return false;
			} else {
				return true;
			}
		case Attack:
			if (system.getPlayerMonster().attacks.get(menu.getAction().info).currentUsage <= 0) {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That move doesn't have any PP!");
				chatboxHandler.waitAndHandle();
				return true;
			} else {
				chosenAction = menu.getAction();
				return false;
			}
		case Run:
			chosenAction = menu.getAction();
			return false;
		default:
			return true;
		}
	}

	public BattleAction getChosenAction() {
		return chosenAction;
	}
}
