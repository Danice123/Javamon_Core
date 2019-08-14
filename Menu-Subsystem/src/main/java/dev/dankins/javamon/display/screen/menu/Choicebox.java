package dev.dankins.javamon.display.screen.menu;

import java.util.List;

import dev.dankins.javamon.display.screen.Menu;

public interface Choicebox extends Menu {

	void setupMenu(String text, List<String> variables);

	int getChoiceIndex();

}
