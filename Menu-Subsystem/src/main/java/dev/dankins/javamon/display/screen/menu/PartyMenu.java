package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.data.monster.instance.Party;
import dev.dankins.javamon.display.screen.Menu;

public interface PartyMenu extends Menu {

	void setupMenu(PartyMenuType menuType, Party party);

	PartyMenuAction getMenuAction();

	int getPokemonChoice();

	int getSwitchChoice();

	public enum PartyMenuAction {
		View, Switch, Exit;
	}

	public enum PartyMenuType {
		View, Switch, UseItem;
	}

}
