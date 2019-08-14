package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.monster.instance.PartyImpl;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PartyMenu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ChoosePokemonHandler extends MenuHandler<PartyMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PartyMenu.class;
	static public Class<? extends PartyMenu> Menu_Class;

	private final PartyImpl party;
	private MonsterInstance chosenPokemon = null;
	private final MonsterInstance currentPokemon;
	private final boolean canCancel;

	public ChoosePokemonHandler(final Game game, final MonsterInstance currentPokemon,
			final PartyMenuType type, final boolean canCancel) {
		super(game, Menu_Class);
		this.currentPokemon = currentPokemon;
		this.canCancel = canCancel;
		party = game.getPlayer().getParty();
		menu.setupMenu(type, party);
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
			final MonsterInstance chosenPokemon = party.get(menu.getPokemonChoice());
			if (chosenPokemon.equals(currentPokemon)) {
				final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
						"That Pokemon is already fighting!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
				return true;
			}
			if (chosenPokemon.getCurrentHealth() > 0) {
				this.chosenPokemon = chosenPokemon;
				return false;
			}
			final ChatboxHandler chatboxHandler = new ChatboxHandler(game,
					"That Pokemon has no will to fight!");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);
			return true;
		case Exit:
			if (canCancel) {
				return false;
			}
			return true;
		default:
			return true;
		}
	}

	public MonsterInstance getChosenPokemon() {
		return chosenPokemon;
	}
}
