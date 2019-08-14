package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.SaveMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class SaveHandler extends MenuHandler<SaveMenu> {

	static public final Class<? extends Menu> MENU_TYPE = SaveMenu.class;
	static public Class<? extends SaveMenu> Menu_Class;

	public SaveHandler(final Game game) {
		super(game, Menu_Class);
		menu.setupMenu(game.getPlayer());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		if (menu.shouldSave()) {
			game.saveGame();
		}
		return false;
	}

}
