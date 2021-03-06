package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;

public interface GameMenu extends Menu {

	void setupMenu(String gameName, boolean hasSave);

	GameMenuAction getMenuAction();

	public enum GameMenuAction {
		LoadGame, NewGame;
	}
}
