package dev.dankins.javamon.logic;

import java.io.IOException;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.MainLoader;
import dev.dankins.javamon.MasterFile;
import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.monster.MonsterListImpl;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.display.Display;
import dev.dankins.javamon.display.Spriteset;
import dev.dankins.javamon.display.screen.Background;
import dev.dankins.javamon.display.screen.Loading;
import dev.dankins.javamon.display.screen.Screen;
import dev.dankins.javamon.display.screen.World;
import dev.dankins.javamon.logic.entity.Player;
import dev.dankins.javamon.logic.map.MapHandler;
import dev.dankins.javamon.logic.menu.GameMenuHandler;
import dev.dankins.javamon.logic.script.ScriptHandler;

public class Game implements Runnable {

	private final Display display;
	private final ControlProcessor controlProcessor;
	private final AssetDescriptor<MasterFile> masterFile;

	private final MainLoader assets;
	private Player player;
	private final MapHandler mapHandler;
	private MonsterListImpl monsterList;

	// hacky
	public boolean controlLock;

	public Game(final Display display, final String gamepath) {
		this.display = display;
		assets = new MainLoader(gamepath);
		controlProcessor = new ControlProcessor(display);
		masterFile = new AssetDescriptor<MasterFile>(gamepath, MasterFile.class);
		mapHandler = new MapHandler(assets);
	}

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

		// Load Game
		final MasterFile master = assets.get(masterFile);
		assets.loadMenus(master);
		assets.load("MonsterList", MonsterListImpl.class);
		assets.load(master.playerSpriteset, Texture.class);
		assets.load(master.startScript, Script.class);

		final boolean hasSave = Gdx.files.external("Player.yaml").exists();
		if (hasSave) {
			assets.load("Player.yaml", SaveFile.class);
		}
		ThreadUtils.waitOnObject(load);
		monsterList = assets.get("MonsterList");

		// Setup input processer
		Gdx.input.setInputProcessor(controlProcessor);
		new Thread(controlProcessor).start();

		// Menu
		final Background background = new Background();
		display.setScreen(background);
		load.disposeMe();

		final GameMenuHandler gameMenuHandler = new GameMenuHandler(this, master, hasSave);
		gameMenuHandler.waitAndHandle();

		// Create player
		final Spriteset spriteset = new Spriteset(
				assets.get(master.playerSpriteset, Texture.class));
		player = new Player(Optional.of(spriteset));
		mapHandler.setPlayer(player);

		// Load or new game
		final World world = new World(this, mapHandler);
		display.setScreen(world);
		switch (gameMenuHandler.getAction()) {
		case LoadGame:
			final SaveFile saveFile = assets.get("Player.yaml", SaveFile.class);
			mapHandler.loadMap(player.load(assets, saveFile));
			mapHandler.getMap().executeMapScript(this);
			break;
		case NewGame:
			new ScriptHandler(this, assets.get(master.startScript, Script.class)).run();
			break;
		default:
			// Nonsense
		}
	}

	public void saveGame() {
		final ExternalFileHandleResolver saveHandler = new ExternalFileHandleResolver();
		final SaveFile save = player.createSave();
		save.mapName = mapHandler.getMap().getMapName();
		try {
			assets.objectMapper.writeValue(saveHandler.resolve("Player.yaml").file(), save);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
