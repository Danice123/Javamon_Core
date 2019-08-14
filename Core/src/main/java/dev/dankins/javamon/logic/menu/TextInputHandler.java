package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.TextInput;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class TextInputHandler extends MenuHandler<TextInput> {

	static public final Class<? extends Menu> MENU_TYPE = TextInput.class;
	static public Class<? extends TextInput> Menu_Class;

	private String input;
	private boolean wasCancelled;

	public TextInputHandler(final Game game, final String title, final boolean canCancel) {
		super(game, Menu_Class);
		menu.setupMenu(title, canCancel);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		input = menu.getInput();
		wasCancelled = menu.cancelled();
		return false;
	}

	public String getInput() {
		return input;
	}

	public boolean isCancelled() {
		return wasCancelled;
	}

}
