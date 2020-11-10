package dev.dankins.javamon;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.battle.data.attack.Attack;
import dev.dankins.javamon.battle.data.loader.AttackLoader;
import dev.dankins.javamon.battle.data.loader.MonsterListLoader;
import dev.dankins.javamon.battle.data.loader.MonsterLoader;
import dev.dankins.javamon.battle.data.monster.Monster;
import dev.dankins.javamon.battle.data.monster.MonsterList;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.loader.EncounterListLoader;
import dev.dankins.javamon.data.loader.ItemLoader;
import dev.dankins.javamon.data.loader.TriggerListLoader;
import dev.dankins.javamon.data.map.EncounterList;
import dev.dankins.javamon.data.map.TriggerList;
import dev.dankins.javamon.logic.map.MapData;
import dev.dankins.javamon.logic.map.MapLoader;

public class MasterFileLoader extends SynchronousAssetLoader<MasterFile, MasterFileLoader.Parameters> {

	private final ObjectMapper mapper;

	public MasterFileLoader(final ObjectMapper mapper) {
		super(MainLoader.MENU_FILE_RESOLVER);
		this.mapper = mapper;
	}

	@Override
	public MasterFile load(final AssetManager assetManager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			final MasterFile master = mapper.readValue(file.file(), MasterFile.class);
			master.setGameDirectory(file.parent());

			assetManager.setLoader(Attack.class, new AttackLoader(mapper, file.sibling(master.attackDBPath)));
			assetManager.setLoader(Monster.class, new MonsterLoader(mapper, file.sibling(master.monsterDBPath)));
			assetManager.setLoader(MonsterList.class,
					new MonsterListLoader(mapper, file.sibling(master.monsterDBPath)));
			assetManager.setLoader(Item.class, new ItemLoader(mapper, file.sibling(master.itemDBPath)));
			assetManager.setLoader(TriggerList.class, new TriggerListLoader(mapper, file.sibling(master.itemDBPath)));
			assetManager.setLoader(EncounterList.class,
					new EncounterListLoader(mapper, file.sibling(master.itemDBPath)));
			assetManager.setLoader(MapData.class, new MapLoader(file.sibling(master.itemDBPath)));

			assetManager.load("MonsterList", MonsterList.class);

			return master;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<MasterFile> {
	}

}
