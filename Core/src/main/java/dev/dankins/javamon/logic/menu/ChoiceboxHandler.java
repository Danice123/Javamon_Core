package dev.dankins.javamon.logic.menu;

import java.util.List;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.Choicebox;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ChoiceboxHandler extends MenuHandler<Choicebox> {

	static public final Class<? extends Menu> MENU_TYPE = Choicebox.class;
	static public Class<? extends Choicebox> Menu_Class;

	private final List<String> variables;
	private String output;

	public ChoiceboxHandler(final Game game, final String text, final List<String> variables) {
		super(game, Menu_Class);
		this.variables = variables;
		menu.setupMenu(text, variables);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		output = variables.get(menu.getChoiceIndex());
		return false;
	}

	public String waitForResponse() {
		waitAndHandle();
		return output;
	}
}
