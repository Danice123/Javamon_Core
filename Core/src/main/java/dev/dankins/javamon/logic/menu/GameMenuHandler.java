package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.MasterFile;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.GameMenu;
import dev.dankins.javamon.display.screen.menu.GameMenu.GameMenuAction;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class GameMenuHandler extends MenuHandler<GameMenu> {

	static public final Class<? extends Menu> MENU_TYPE = GameMenu.class;
	static public Class<? extends GameMenu> Menu_Class;

	private GameMenuAction action;

	public GameMenuHandler(final Game game, final MasterFile master, final boolean hasSave) {
		super(game, Menu_Class);
		menu.setupMenu(master.getName(), hasSave);
		initScreen();
	}

	public GameMenuAction getAction() {
		return action;
	}

	@Override
	protected boolean handleResponse() {
		action = menu.getMenuAction();
		return false;
	}

}
