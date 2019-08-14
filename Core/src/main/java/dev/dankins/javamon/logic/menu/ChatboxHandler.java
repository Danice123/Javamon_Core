package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.Chatbox;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ChatboxHandler extends MenuHandler<Chatbox> {

	static public final Class<? extends Menu> MENU_TYPE = Chatbox.class;
	static public Class<? extends Chatbox> Menu_Class;

	public ChatboxHandler(final Game game, final String text) {
		super(game, Menu_Class);
		menu.setupMenu(text);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
