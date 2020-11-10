package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.battle.display.BattlesystemListener;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Player;
import dev.dankins.javamon.logic.abstraction.Trainer;

public interface BattleMenu extends Menu, BattlesystemListener {

	void setupMenu(Player player, Trainer enemy);

	void startBattle();

	void moveEnemyFromWindow();

	void movePlayerFromWindow();

	void enemyMonster(MonsterInstance monster);

	void playerMonster(final MonsterInstance monster);

	void setMessageBoxContents(String message);

	void updateHealth(MonsterInstance player, MonsterInstance enemy);

}
