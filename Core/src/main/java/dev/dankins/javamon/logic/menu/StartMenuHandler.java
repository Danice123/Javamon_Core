package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.StartMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class StartMenuHandler extends MenuHandler<StartMenu> {

	static public final Class<? extends Menu> MENU_TYPE = StartMenu.class;
	static public Class<? extends StartMenu> Menu_Class;

	public StartMenuHandler(final Game game) {
		super(game, Menu_Class);
		menu.setupMenu(game.getPlayer());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getMenuChoice()) {
		case Pokedex:
			final PokedexHandler pokedexHandler = new PokedexHandler(game);
			pokedexHandler.waitAndHandle();
			return true;
		case Pokemon:
			final PartyHandler partyHandler = new PartyHandler(game);
			partyHandler.waitAndHandle();
			return true;
		case Bag:
			final BagHandler bagHandler = new BagHandler(game);
			bagHandler.waitAndHandle();
			return true;
		case Trainer:
			final TrainerHandler trainerHandler = new TrainerHandler(game);
			trainerHandler.waitAndHandle();
			return true;
		case Save:
			final SaveHandler saveHandler = new SaveHandler(game);
			saveHandler.waitAndHandle();
			return true;
		case Options:
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
