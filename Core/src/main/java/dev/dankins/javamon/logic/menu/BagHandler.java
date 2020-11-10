package dev.dankins.javamon.logic.menu;

import java.util.List;
import java.util.Optional;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.battle.data.attack.effect.Effect;
import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.BagMenu;
import dev.dankins.javamon.display.screen.menu.BagMenu.BagMenuType;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;
import dev.dankins.javamon.logic.script.ScriptHandler;

public class BagHandler extends MenuHandler<BagMenu> implements EffectHandler {

	static public final Class<? extends Menu> MENU_TYPE = BagMenu.class;
	static public Class<? extends BagMenu> Menu_Class;

	private final Inventory playerInventory;

	public BagHandler(final Game game) {
		super(game, Menu_Class);
		playerInventory = game.getPlayer().getInventory();
		menu.setupMenu(BagMenuType.View, playerInventory.getItems());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		final Item item;
		switch (menu.getMenuAction()) {
		case Use:
			item = playerInventory.getItems().get(menu.getMenuChoice());
			// Use item
			if (item.isUsableInField()) {
				// Item with Script
				final Optional<Script> script = item.getScript(game.getAssets());
				if (script.isPresent()) {
					new ScriptHandler(game, script.get()).run();
					return true;
				}
				// Item with effect
				final Optional<List<Effect>> effect = item.getEffects();
				if (effect.isPresent()) {
					if (game.getPlayer().getParty().size() == 0) {
						final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "You can't use that now!");
						chatboxHandler.waitAndHandle();
						return true;
					}

					final ChoosePokemonHandler choosePokemonHandler = new ChoosePokemonHandler(game, null,
							PartyMenuType.UseItem, true);
					choosePokemonHandler.waitAndHandle();
					if (choosePokemonHandler.getChosenPokemon() == null) {
						return true;
					}

					final MonsterInstance target = choosePokemonHandler.getChosenPokemon();
					// effect.get().use(this, target, target, new ItemMove());

					if (item.isConsumedOnUse()) {
						if (item instanceof ItemStack) {
							final ItemStack stack = (ItemStack) item;
							stack.remove(menu.getAmountChoice());
							if (stack.size() <= 0) {
								playerInventory.removeItem(item);
							}
						} else {
							playerInventory.removeItem(item);
						}
					}
				}
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "You can't use that now!");
				chatboxHandler.waitAndHandle();
			}

			return true;
		case Toss:
			item = playerInventory.getItems().get(menu.getMenuChoice());
			if (item.isTossable()) {
				if (item instanceof ItemStack) {
					final ItemStack stack = (ItemStack) item;
					stack.remove(menu.getAmountChoice());
					if (stack.size() <= 0) {
						playerInventory.removeItem(item);
					}
				} else {
					playerInventory.removeItem(item);
				}
			} else {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game, "That's too important to toss!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
			}
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void print(final String text) {
		final ChatboxHandler chatboxHandler = new ChatboxHandler(game, text);
		chatboxHandler.waitAndHandle();
	}

	@Override
	public void updateHealth() {
	}

}
