package dev.dankins.javamon;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.github.danice123.javamon.logic.map.MapData;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.loader.AttackLoader;
import dev.dankins.javamon.data.loader.EncounterListLoader;
import dev.dankins.javamon.data.loader.EntityLoader;
import dev.dankins.javamon.data.loader.ItemLoader;
import dev.dankins.javamon.data.loader.MonsterListLoader;
import dev.dankins.javamon.data.loader.MonsterLoader;
import dev.dankins.javamon.data.loader.SaveLoader;
import dev.dankins.javamon.data.loader.ScriptLoader;
import dev.dankins.javamon.data.loader.TriggerListLoader;
import dev.dankins.javamon.data.map.EncounterList;
import dev.dankins.javamon.data.map.TriggerList;
import dev.dankins.javamon.data.monster.MonsterImpl;
import dev.dankins.javamon.data.monster.MonsterListImpl;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.display.animation.Animation;
import dev.dankins.javamon.display.loader.AnimationLoader;
import dev.dankins.javamon.logic.MenuHandler;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.menu.BagHandler;
import dev.dankins.javamon.logic.menu.BattleMenuHandler;
import dev.dankins.javamon.logic.menu.ChatboxHandler;
import dev.dankins.javamon.logic.menu.ChoiceboxHandler;
import dev.dankins.javamon.logic.menu.ChooseItemHandler;
import dev.dankins.javamon.logic.menu.ChoosePokemonHandler;
import dev.dankins.javamon.logic.menu.GameMenuHandler;
import dev.dankins.javamon.logic.menu.ItemStorageHandler;
import dev.dankins.javamon.logic.menu.PCHandler;
import dev.dankins.javamon.logic.menu.PartyHandler;
import dev.dankins.javamon.logic.menu.PartyStatusHandler;
import dev.dankins.javamon.logic.menu.PlayerBattleHandler;
import dev.dankins.javamon.logic.menu.PokedexHandler;
import dev.dankins.javamon.logic.menu.PokedexPageHandler;
import dev.dankins.javamon.logic.menu.SaveHandler;
import dev.dankins.javamon.logic.menu.ShopHandler;
import dev.dankins.javamon.logic.menu.StartMenuHandler;
import dev.dankins.javamon.logic.menu.TextInputHandler;
import dev.dankins.javamon.logic.menu.TrainerHandler;

public class MainLoader extends AssetManager {

	public final ObjectMapper objectMapper;

	public MainLoader() {
		super();
		objectMapper = new ObjectMapper(new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));
		objectMapper.findAndRegisterModules();

		setLoader(AttackBase.class, new AttackLoader(objectMapper));
		setLoader(MonsterImpl.class, new MonsterLoader(objectMapper));
		setLoader(MonsterListImpl.class, new MonsterListLoader(objectMapper));
		setLoader(Item.class, new ItemLoader(objectMapper));

		setLoader(TriggerList.class, new TriggerListLoader(objectMapper));
		setLoader(EncounterList.class, new EncounterListLoader(objectMapper));
		setLoader(EntityHandler.class, new EntityLoader(objectMapper));
		setLoader(MapData.class, new MapLoader());
		setLoader(Script.class, new ScriptLoader(new InternalFileHandleResolver()));
		setLoader(Animation.class, new AnimationLoader(objectMapper));
		setLoader(SaveFile.class, new SaveLoader(objectMapper));

		loadGUI();
		loadMenus();
		load("Pokemon", MonsterListImpl.class);
		load("assets/entity/sprites/Red.png", Texture.class);
		load("Player.yaml", SaveFile.class);
		load("assets/scripts/Start.ps", Script.class);
	}

	private void loadGUI() {
		load("assets/gui/border.png", Texture.class);
		load("assets/gui/arrow.png", Texture.class);
	}

	static List<Class<? extends MenuHandler<?>>> handlers = Lists.newArrayList(BagHandler.class,
			BattleMenuHandler.class, ChatboxHandler.class, ChoiceboxHandler.class,
			ChooseItemHandler.class, ChoosePokemonHandler.class, GameMenuHandler.class,
			ItemStorageHandler.class, PartyHandler.class, PartyStatusHandler.class, PCHandler.class,
			PlayerBattleHandler.class, PokedexHandler.class, PokedexPageHandler.class,
			SaveHandler.class, ShopHandler.class, StartMenuHandler.class, TextInputHandler.class,
			TrainerHandler.class);

	private void loadMenus() {
		final List<Class<?>> menuClasses = getMenuClasses();

		try {
			for (final Class<? extends MenuHandler<?>> handler : handlers) {
				final Field typeField = handler.getDeclaredField("MENU_TYPE");
				final Class<?> type = (Class<?>) typeField.get(null);

				for (final Class<?> menu : menuClasses) {
					if (type.isAssignableFrom(menu)) {
						final Field classField = handler.getDeclaredField("Menu_Class");
						classField.set(null, menu);
					}
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Class<?>> getMenuClasses() {
		try {
			final URLClassLoader child = new URLClassLoader(
					new URL[] { new File("assets/menu.jar").toURI().toURL() },
					this.getClass().getClassLoader());

			final Class<LoadMenusFromHere> loader = (Class<LoadMenusFromHere>) Class
					.forName("dev.dankins.javamon.MenuLoader", true, child);
			return loader.newInstance().load();
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
				| MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
