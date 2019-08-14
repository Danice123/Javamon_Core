package dev.dankins.javamon.logic;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.display.screen.AbstractMenu;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.Screen;

public abstract class MenuHandler<T extends Menu> {

	protected final Game game;
	protected final T menu;
	protected Screen screen;

	public MenuHandler(final Game game, final Class<? extends T> menu) {
		this.game = game;
		try {
			this.menu = menu.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| SecurityException e) {
			throw new RuntimeException("No/Bad " + menu.getName() + " class found");
		}
	}

	protected void initScreen() {
		screen = new AbstractMenu(game.getLatestScreen(), menu);
	}

	public void waitAndHandle() {
		do {
			ThreadUtils.waitOnObject(menu);
		} while (handleResponse());
		screen.disposeMe();
		ThreadUtils.sleep(50);
	}

	protected abstract boolean handleResponse();

}
