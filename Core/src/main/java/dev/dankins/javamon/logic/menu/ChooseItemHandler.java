package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.BagMenu;
import dev.dankins.javamon.display.screen.menu.BagMenu.BagMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ChooseItemHandler extends MenuHandler<BagMenu> {

	static public final Class<? extends Menu> MENU_TYPE = BagMenu.class;
	static public Class<? extends BagMenu> Menu_Class;

	private final Inventory inventory;
	private boolean isCancelled = true;

	public ChooseItemHandler(final Game game, final Inventory inventory) {
		super(game, Menu_Class);
		this.inventory = inventory;
		menu.setupMenu(BagMenuType.Choose, inventory.getItems());
		initScreen();
	}

	public boolean wasCancelled() {
		return isCancelled;
	}

	public Item getChosenItem() {
		return inventory.getItems().get(menu.getMenuChoice());
	}

	public int getChosenAmount() {
		return menu.getAmountChoice();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getMenuAction()) {
		case Use:
			isCancelled = false;
			return false;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}