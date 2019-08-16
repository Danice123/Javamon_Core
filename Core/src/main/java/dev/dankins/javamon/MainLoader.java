package dev.dankins.javamon;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.loader.EntityLoader;
import dev.dankins.javamon.data.loader.SaveLoader;
import dev.dankins.javamon.data.loader.ScriptLoader;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.display.animation.Animation;
import dev.dankins.javamon.display.loader.AnimationLoader;
import dev.dankins.javamon.display.loader.FreeTypeFontLoader;
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

	static public final MenuFileHandleResolver MENU_FILE_RESOLVER = new MenuFileHandleResolver();
	static public final InternalFileHandleResolver FILE_RESOLVER = new InternalFileHandleResolver();

	public final ObjectMapper objectMapper = new ObjectMapper(
			new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));

	public MainLoader(final String gamepath) {
		super(MENU_FILE_RESOLVER);
		setLoader(FreeTypeFontGenerator.class, new FreeTypeFontLoader(MENU_FILE_RESOLVER));
		objectMapper.findAndRegisterModules();

		setLoader(MasterFile.class, new MasterFileLoader(objectMapper));
		setLoader(SaveFile.class, new SaveLoader(objectMapper));

		// TODO: mess with these?
		setLoader(EntityHandler.class, new EntityLoader(objectMapper));
		setLoader(Script.class, new ScriptLoader());
		setLoader(Animation.class, new AnimationLoader(objectMapper));

		load(gamepath, MasterFile.class);
	}

	static private List<Class<? extends MenuHandler<?>>> handlers = Lists.newArrayList(
			BagHandler.class, BattleMenuHandler.class, ChatboxHandler.class, ChoiceboxHandler.class,
			ChooseItemHandler.class, ChoosePokemonHandler.class, GameMenuHandler.class,
			ItemStorageHandler.class, PartyHandler.class, PartyStatusHandler.class, PCHandler.class,
			PlayerBattleHandler.class, PokedexHandler.class, PokedexPageHandler.class,
			SaveHandler.class, ShopHandler.class, StartMenuHandler.class, TextInputHandler.class,
			TrainerHandler.class);

	public void loadMenus(final MasterFile master) {
		final List<Class<?>> menuClasses = getMenuClasses(master.getMenuJar());

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
	private List<Class<?>> getMenuClasses(final FileHandle jarFile) {
		try {
			final URLClassLoader child = new URLClassLoader(
					new URL[] { jarFile.file().toURI().toURL() }, this.getClass().getClassLoader());

			final Class<LoadMenusFromHere> loaderClass = (Class<LoadMenusFromHere>) Class
					.forName("dev.dankins.javamon.MenuLoader", true, child);
			final LoadMenusFromHere loader = loaderClass.newInstance();
			for (final AssetDescriptor<?> resource : loader.loadResources()) {
				load(resource);
			}
			return loader.load();
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
				| MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
