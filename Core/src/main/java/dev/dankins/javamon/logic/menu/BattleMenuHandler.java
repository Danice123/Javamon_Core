package dev.dankins.javamon.logic.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.Lists;

import dev.dankins.javamon.Coord;
import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.MainLogicHandler;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;
import dev.dankins.javamon.battle.data.attack.Attack;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
import dev.dankins.javamon.battle.data.monster.MonsterInstance.Levelup;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.ExpEvent;
import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.instance.Party;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.BattleMenu;
import dev.dankins.javamon.display.screen.menu.PartyMenu.PartyMenuType;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.PartyWrapper;
import dev.dankins.javamon.logic.entity.Player;

public class BattleMenuHandler extends MenuHandler<BattleMenu> implements TrainerHandler {

	static public final Class<? extends Menu> MENU_TYPE = BattleMenu.class;
	static public Class<? extends BattleMenu> Menu_Class;

	private final MainLogicHandler battlesystem;
	private final Player player;
	private final TrainerHandler enemy;

	private MonsterHandler currentMonster;

	public BattleMenuHandler(final Game game, final Player player, final TrainerHandler enemy) {
		super(game, Menu_Class);
		this.player = player;
		this.enemy = enemy;
		currentMonster = new MonsterHandler(getKey(), player.getParty().firstPokemon());
		battlesystem = new MainLogicHandler(menu, this, this.enemy);
		menu.setupMenu(this, this.enemy);
		initScreen();
		ThreadUtils.makeAnonThread(battlesystem);
	}

	@Override
	public String getKey() {
		return "Player";
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public AssetDescriptor<Texture> getImage() {
		return player.getBackImage();
	}

	@Override
	public Party getParty_() {
		return new PartyWrapper(player.getParty());
	}

	@Override
	public MonsterHandler getCurrentMonster() {
		return currentMonster;
	}

	@Override
	public Action getNextAction(TrainerHandler opponent) {
		final PlayerBattleHandler playerBattleHandler = new PlayerBattleHandler(game, player, currentMonster);
		playerBattleHandler.waitAndHandle();
		ThreadUtils.sleep(100);
		return playerBattleHandler.getChosenAction();
	}

	@Override
	public SwitchAction getNextMonster() throws BattleStateChange {
		final ChoosePokemonHandler choosePokemonInBattleHandler = new ChoosePokemonHandler(game,
				currentMonster.getMonster(), PartyMenuType.Switch, false);
		choosePokemonInBattleHandler.waitAndHandle();
		ThreadUtils.sleep(10);
		return new SwitchAction(new MonsterHandler("key", choosePokemonInBattleHandler.getChosenPokemon()), (v) -> {
		});
	}

	@Override
	protected boolean handleResponse() {
		if (battlesystem.battleIsOver) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTrainer() {
		return true;
	}

	@Override
	public String getTrainerLossQuip() {
		return null;
	}

	public void print(final String string) {
		final ChatboxHandler chatboxHandler = new ChatboxHandler(game, string);
		chatboxHandler.waitAndHandle();
	}

	public boolean ask(final String string) {
		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game, string, Lists.newArrayList("Yes", "No"));
		return choiceboxHandler.waitForResponse().equals("Yes");
	}

	public int ask(final String string, final String[] args) {
		final ChoiceboxHandler choiceboxHandler = new ChoiceboxHandler(game, string, Arrays.asList(args));
		final String res = choiceboxHandler.waitForResponse();

		for (int i = 0; i < args.length; i++) {
			if (res.equals(args[i])) {
				return i;
			}
		}
		return -1;
	}

	public void respawnPlayer() {
		game.getPlayer().modifyMoney(game.getPlayer().getMoney() / -2);
		for (final MonsterInstance monster : game.getPlayer().getParty()) {
			monster.heal();
		}

		final String[] respawn = game.getPlayer().getStrings().get("respawnPoint").split(":");
		ThreadUtils.makeAnonThread(() -> {
			game.getMapHandler().loadMap(respawn[0]);
			game.getPlayer().setCoord(new Coord(Integer.parseInt(respawn[1]), Integer.parseInt(respawn[2])),
					Integer.parseInt(respawn[3]));
			game.getMapHandler().getMap().executeMapScript(game);
		});
	}

	@Override
	public List<Event> rewardEXP(int exp) {
		List<Event> events = Lists.newArrayList();
		events.add(new ExpEvent(getCurrentMonster().getMonster(), exp));
		for (Levelup lu : getCurrentMonster().getMonster().addExp(exp)) {
			Event lue = new Event(EventType.LevelUp);
			lue.parameters.put("Monster", getCurrentMonster().getMonster());
			lue.parameters.put("Level", lu.level);
			events.add(lue);
			for (Attack attack : lu.movesToLearn) {
				Event ale = new Event(EventType.LearnNewAttack);
				ale.parameters.put("Monster", getCurrentMonster().getMonster());
				ale.parameters.put("Attack", attack);
				events.add(ale);
			}
		}
		return events;
	}

	@Override
	public int getWinnings() {
		// TODO: Handle loss losing money
		return 0;
	}

	@Override
	public List<Event> rewardMoney(int winnings) {
		player.modifyMoney(winnings);

		Event e = new Event(EventType.WonMoney);
		e.parameters.put("Winnings", winnings);
		return Lists.newArrayList(e);
	}

	@Override
	public List<Event> rewardEV(Map<Stat, Integer> evs) {
		evs.forEach((Stat stat, Integer amount) -> getCurrentMonster().getMonster().changeStat(stat, amount));
		return Lists.newArrayList();
	}
}
