package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PCMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class PCHandler extends MenuHandler<PCMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PCMenu.class;
	static public Class<? extends PCMenu> Menu_Class;

	public PCHandler(final Game game) {
		super(game, Menu_Class);
		menu.setupMenu(game.getPlayer());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		ChatboxHandler chatboxHandler;
		switch (menu.getMenuChoice()) {
		case Pokemon:
			chatboxHandler = new ChatboxHandler(game,
					"Accessed someone's PC./nAccessed Pokemon Storage System.");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			return true;
		case Item:
			chatboxHandler = new ChatboxHandler(game,
					"Accessed my PC./nAccessed Item Storage System.");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			final ItemStorageHandler itemStorageHandler = new ItemStorageHandler(game);
			itemStorageHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
