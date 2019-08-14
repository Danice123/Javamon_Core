package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.TrainerMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class TrainerHandler extends MenuHandler<TrainerMenu> {

	static public final Class<? extends Menu> MENU_TYPE = TrainerMenu.class;
	static public Class<? extends TrainerMenu> Menu_Class;

	public TrainerHandler(final Game game) {
		super(game, Menu_Class);
		menu.setupMenu(game.getPlayer());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
