package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.battle.data.monster.MonsterList;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PokedexMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class PokedexHandler extends MenuHandler<PokedexMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PokedexMenu.class;
	static public Class<? extends PokedexMenu> Menu_Class;

	private final MonsterList monsterList;

	public PokedexHandler(final Game game) {
		super(game, Menu_Class);
		monsterList = game.getMonsterList();
		menu.setupMenu(monsterList, game.getPlayer().getPokeData());
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		switch (menu.getMenuAction()) {
		case View:
			final PokedexPageHandler pokedexPageHandler = new PokedexPageHandler(game,
					monsterList.getMonster(menu.getPokemonChoice()),
					game.getPlayer().getPokeData().isCaught(menu.getPokemonChoice()));
			pokedexPageHandler.waitAndHandle();
			return true;
		case Cry:
			return true;
		case Area:
			return true;
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
