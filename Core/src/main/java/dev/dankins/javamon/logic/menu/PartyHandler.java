package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.data.monster.instance.PartyImpl;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyMenu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class PartyHandler extends MenuHandler<PartyMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PartyMenu.class;
	static public Class<? extends PartyMenu> Menu_Class;

	private final PartyImpl party;

	public PartyHandler(final Game game) {
		super(game, Menu_Class);
		party = game.getPlayer().getParty();
		menu.setupMenu(PartyMenuType.View, party);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getMenuAction()) {
		case View:
			final PartyStatusHandler partyStatusHandler = new PartyStatusHandler(game,
					party.get(menu.getPokemonChoice()));
			partyStatusHandler.waitAndHandle();
			return true;
		case Switch:
			party.swap(menu.getSwitchChoice(), menu.getPokemonChoice());
			menu.setupMenu(PartyMenuType.View, party);
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
