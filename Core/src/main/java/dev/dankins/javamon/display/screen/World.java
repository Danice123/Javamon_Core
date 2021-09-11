package dev.dankins.javamon.display.screen;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

import dev.dankins.javamon.Coord;
import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
import dev.dankins.javamon.data.map.Trigger;
import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Dir;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.Key;
import dev.dankins.javamon.logic.battle.WildMonster;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.entity.Player;
import dev.dankins.javamon.logic.entity.Trainer;
import dev.dankins.javamon.logic.map.MapHandler;
import dev.dankins.javamon.logic.map.WildEncounter;
import dev.dankins.javamon.logic.menu.BattleMenuHandler;
import dev.dankins.javamon.logic.menu.StartMenuHandler;

public class World extends Screen {

	private final Game game;
	private final MapHandler mapHandler;
	private OrthographicCamera camera;

	public World(final Game game, final MapHandler mapHandler) {
		this.game = game;
		this.mapHandler = mapHandler;
	}

	@Override
	protected void init(final AssetManager assets, final RenderInfo ri) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15, 10);
		camera.update();
	}

	@Override
	protected void renderScreen(final RenderHelper rh, final float delta) {
		mapHandler.render(camera);
	}

	@Override
	protected void tickSelf(final float delta) {
		mapHandler.tick();
	}

	@Override
	protected void handleKey(final Key key) {
		if (game.controlLock) {
			return;
		}
		switch (key) {
		case accept:
			playerActivate();
			break;
		case deny:
			break;
		case start:
			final StartMenuHandler startMenuHandler = new StartMenuHandler(game);
			ThreadUtils.makeAnonThread(new Runnable() {
				@Override
				public void run() {
					startMenuHandler.waitAndHandle();
				}
			});
			break;
		case up:
			playerWalk(Dir.North);
			break;
		case down:
			playerWalk(Dir.South);
			break;
		case left:
			playerWalk(Dir.East);
			break;
		case right:
			playerWalk(Dir.West);
			break;
		case select:
			break;
		}
	}

	private void playerActivate() {
		final Player player = game.getPlayer();
		if (activateEntity(
				mapHandler.getMap().getEntity(player.getCoord().inDirection(player.getFacing()), player.getLayer()))) {
			return;
		}
		if (activateEntity(mapHandler.getMap().getEntity(player.getCoord().inDirection(player.getFacing()),
				player.getLayer() + 1))) {
			return;
		}
	}

	private boolean activateEntity(final EntityHandler entity) {
		if (entity != null) {
			entity.activate(game);
			return true;
		}
		return false;
	}

	private void playerWalk(final Dir dir) {
		final Player player = game.getPlayer();

		if (player.walk(mapHandler, dir)) {
			int layer = player.getLayer();
			if (mapHandler.getMap().getAdjMapLayerChange(dir) != null) {
				layer = mapHandler.getMap().getAdjMapLayerChange(dir);
			}
			switch (dir) {
			case North:
				if (player.getY() == mapHandler.getMap().getY()) {
					player.setCoord(new Coord(player.getX() - mapHandler.getMap().getAdjMapTweak(dir), 0), layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case South:
				if (player.getY() == -1) {
					player.setCoord(new Coord(player.getX() - mapHandler.getMap().getAdjMapTweak(dir),
							mapHandler.getMapBorderBottomHeight() - 1), layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case East:
				if (player.getX() == mapHandler.getMap().getX()) {
					player.setCoord(new Coord(0, player.getY() - mapHandler.getMap().getAdjMapTweak(dir)), layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			case West:
				if (player.getX() == -1) {
					player.setCoord(new Coord(mapHandler.getMapBorderLeftWidth() - 1,
							player.getY() - mapHandler.getMap().getAdjMapTweak(dir)), layer);
					mapHandler.loadMap(mapHandler.getMap().getAdjMapName(dir));
					mapHandler.getMap().executeMapScript(game);
				}
				break;
			default:
				break;
			}
			final Optional<Trigger> trigger = mapHandler.getMap().getTrigger(player.getCoord(), player.getLayer());
			if (trigger.isPresent()) {
				trigger.get().activate(game);
				return;
			}

			final Optional<Trainer> trainer = mapHandler.getMap().getTrainerFacingPlayer(player.getCoord(),
					player.getLayer());
			if (trainer.isPresent() && !player.getStrings().containsKey(trainer.get().getEntity().getName())) {
				while (trainer.get().walk(mapHandler, trainer.get().getFacing())) {
				}
				trainer.get().activate(game);
			}

			final Optional<WildEncounter> wildPokemonEncounter = mapHandler.getMap()
					.getWildPokemonEncounter(player.getCoord(), player.getLayer());

			if (wildPokemonEncounter.isPresent()) {
				MonsterInstance monster = game.getMonsterList().generateWild(wildPokemonEncounter.get().monsterName,
						wildPokemonEncounter.get().level, player.getName(), player.getPlayerId());
				WildMonster wildEncounter = new WildMonster(monster);
				final BattleMenuHandler battleMenuHandler = new BattleMenuHandler(game, game.getPlayer(),
						wildEncounter);
				ThreadUtils.makeAnonThread(new Runnable() {
					@Override
					public void run() {
						battleMenuHandler.waitAndHandle();
					}
				});
			}
		}
	}

}
