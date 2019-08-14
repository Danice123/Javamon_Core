package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyStatusMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class PartyStatusHandler extends MenuHandler<PartyStatusMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PartyStatusMenu.class;
	static public Class<? extends PartyStatusMenu> Menu_Class;

	public PartyStatusHandler(final Game game, final MonsterInstance pokemon) {
		super(game, Menu_Class);
		menu.setupMenu(pokemon);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
