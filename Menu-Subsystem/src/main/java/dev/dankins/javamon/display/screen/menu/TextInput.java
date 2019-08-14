package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;

public interface TextInput extends Menu {

	void setupMenu(String title, boolean canCancel);

	String getInput();

	boolean cancelled();
}
