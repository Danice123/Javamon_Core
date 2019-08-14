package dev.dankins.javamon.logic.menu;

import com.google.common.collect.Lists;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.ShopMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ShopHandler extends MenuHandler<ShopMenu> {

	static public final Class<? extends Menu> MENU_TYPE = ShopMenu.class;
	static public Class<? extends ShopMenu> Menu_Class;

	private final Inventory shopInv;

	public ShopHandler(final Game game, final Inventory shopInv) {
		super(game, Menu_Class);
		this.shopInv = shopInv;
		menu.setupMenu(game.getPlayer(), shopInv);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		Item item;
		int cost;
		ChoiceboxHandler choiceboxHandler;
		switch (menu.getMenuChoice()) {
		case Buy:
			item = shopInv.getItems().get(menu.getMenuIndex());
			cost = item.getCost() * menu.getMenuAmount();

			choiceboxHandler = new ChoiceboxHandler(game,
					item.getName() + "? That will be $" + cost + ". OK?",
					Lists.newArrayList("Yes", "No"));
			if (choiceboxHandler.waitForResponse().equals("Yes")) {
				if (game.getPlayer().modifyMoney(-cost)) {
					game.getPlayer().getInventory().addItems(item, menu.getMenuAmount());
				}
				menu.updateMenu();

				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"Here you are! Thank you!");
				chatboxHandler.waitAndHandle();
			}
			return true;
		case Sell:
			item = game.getPlayer().getInventory().getItems().get(menu.getMenuIndex());

			if (item.isTossable()) {
				cost = item.getCost() / 2 * menu.getMenuAmount();

				choiceboxHandler = new ChoiceboxHandler(game,
						"I can pay you $" + cost + " for that.", Lists.newArrayList("Yes", "No"));
				if (choiceboxHandler.waitForResponse().equals("Yes")) {
					game.getPlayer().modifyMoney(cost);
					game.getPlayer().getInventory().removeItems(item, menu.getMenuAmount());
					menu.updateMenu();
				}
			}
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
