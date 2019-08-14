package dev.dankins.javamon.logic;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.github.danice123.javamon.logic.map.MapHandler;

import dev.dankins.javamon.Coord;
import dev.dankins.javamon.MainLoader;
import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.monster.MonsterListImpl;
import dev.dankins.javamon.display.Display;
import dev.dankins.javamon.display.Spriteset;
import dev.dankins.javamon.display.screen.Background;
import dev.dankins.javamon.display.screen.Loading;
import dev.dankins.javamon.display.screen.Screen;
import dev.dankins.javamon.display.screen.World;
import dev.dankins.javamon.logic.entity.Player;
import dev.dankins.javamon.logic.menu.GameMenuHandler;
import dev.dankins.javamon.logic.script.ScriptHandler;

public class Game implements dev.dankins.javamon.logic.abstraction.Game, Runnable {

	private final Display display;
	private final MainLoader assets;
	private final ControlProcessor controlProcessor;
	private final World world;

	private Player player;
	private final MapHandler mapHandler;
	private MonsterListImpl monsterList;

	// hacky
	public boolean controlLock;

	public Game(final Display display) {
		this.display = display;
		assets = new MainLoader();
		mapHandler = new MapHandler(assets);
		world = new World(this, mapHandler);
		controlProcessor = new ControlProcessor(display);
	}

	@Override
	public AssetManager getAssets() {
		return assets;
	}

	public Player getPlayer() {
		return player;
	}

	public MapHandler getMapHandler() {
		return mapHandler;
	}

	public MonsterListImpl getMonsterList() {
		return monsterList;
	}

	public Screen getBaseScreen() {
		if (display.getScreen().isPresent()) {
			return display.getScreen().get();
		}
		throw new RuntimeException("No screen exists");
	}

	public Screen getLatestScreen() {
		if (display.getScreen().isPresent()) {
			Screen s = display.getScreen().get();
			while (s.hasChild()) {
				s = s.getChild();
			}
			return s;
		}
		throw new RuntimeException("No screen exists");
	}

	@Override
	public void run() {
		// Loading (uses display thread with active GDX renderer)
		final Loading load = new Loading(assets);
		display.setScreen(load);
		ThreadUtils.waitOnObject(load);
		monsterList = assets.get("Pokemon");

		// Setup input processer
		Gdx.input.setInputProcessor(controlProcessor);
		new Thread(controlProcessor).start();

		// Menu
		final Background background = new Background();
		display.setScreen(background);
		load.disposeMe();

		final GameMenuHandler gameMenuHandler = new GameMenuHandler(this);
		gameMenuHandler.waitAndHandle();

		// Create player
		final Optional<Spriteset> spriteset = Optional
				.of(new Spriteset((Texture) assets.get("assets/entity/sprites/Red.png")));
		player = new Player(spriteset);
		mapHandler.setPlayer(player);

		// Load or new game
		String mapName = null;
		switch (gameMenuHandler.getAction()) {
		case LoadGame:
			final SaveFile saveFile = assets.get("Player.yaml", SaveFile.class);
			mapName = player.load(assets, saveFile);
			break;
		case NewGame:
			new ScriptHandler(this, assets.get("assets/scripts/Start.ps")).run();
			player.setCoord(new Coord(4, 2), 0);
			player.setFacing(Dir.North);
			mapName = "Pallet_Town_Red_Home_2";
			break;
		default:
			return; // Nonsense
		}

		// Starting game
		display.setScreen(world);
		mapHandler.loadMap(mapName);
		mapHandler.getMap().executeMapScript(this);
	}

	public void saveGame() {
		final SaveFile save = player.createSave();
		save.mapName = mapHandler.getMap().getMapName();
		try {
			assets.objectMapper.writeValue(new File("Player.yaml"), save);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
