package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyMenu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.Party;
import dev.dankins.javamon.logic.PartyWrapper;

public class PartyHandler extends MenuHandler<PartyMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PartyMenu.class;
	static public Class<? extends PartyMenu> Menu_Class;

	private final Party party;

	public PartyHandler(final Game game) {
		super(game, Menu_Class);
		party = game.getPlayer().getParty();
		menu.setupMenu(PartyMenuType.View, new PartyWrapper(party));
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
			menu.resetAfterSwitch();
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
