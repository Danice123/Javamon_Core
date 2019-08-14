package dev.dankins.javamon.display.screen.menu;

import java.util.List;

import dev.dankins.javamon.data.abstraction.Item;
import dev.dankins.javamon.display.screen.Menu;

public interface BagMenu extends Menu {

	void setupMenu(BagMenuType type, List<? extends Item> list);

	int getMenuChoice();

	BagMenuAction getMenuAction();

	int getAmountChoice();

	public enum BagMenuAction {
		Use, Toss, Exit;
	}

	public enum BagMenuType {
		View, Choose;
	}

}
