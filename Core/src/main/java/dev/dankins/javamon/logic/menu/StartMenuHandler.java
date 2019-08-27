package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.StartMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class StartMenuHandler extends MenuHandler<StartMenu> {

	static public final Class<? extends Menu> MENU_TYPE = StartMenu.class;
	static public Class<? extends StartMenu> Menu_Class;

	private final boolean hasPokemon;
	private final boolean hasPokedex;

	public StartMenuHandler(final Game game) {
		super(game, Menu_Class);
		hasPokemon = game.getPlayer().getParty().size() > 0;
		hasPokedex = game.getPlayer().getFlag("HasPokedex");
		menu.setupMenu(hasPokemon, true);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getMenuChoice()) {
		case Pokedex:
			if (true) {
				final PokedexHandler pokedexHandler = new PokedexHandler(game);
				pokedexHandler.waitAndHandle();
			}
			return true;
		case Pokemon:
			if (hasPokemon) {
				final PartyHandler partyHandler = new PartyHandler(game);
				partyHandler.waitAndHandle();
			}
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
