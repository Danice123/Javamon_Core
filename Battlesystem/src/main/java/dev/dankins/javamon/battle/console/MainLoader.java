package dev.dankins.javamon.battle.console;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import dev.dankins.javamon.battle.data.attack.Attack;
import dev.dankins.javamon.battle.data.loader.AttackLoader;
import dev.dankins.javamon.battle.data.loader.MonsterListLoader;
import dev.dankins.javamon.battle.data.loader.MonsterLoader;
import dev.dankins.javamon.battle.data.monster.Monster;
import dev.dankins.javamon.battle.data.monster.MonsterList;

public class MainLoader extends AssetManager {

	public final ObjectMapper objectMapper = new ObjectMapper(
			new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));

	public MainLoader(final String gamepath) {
		setLoader(Attack.class, new AttackLoader(objectMapper, new FileHandle("db/attack")));
		setLoader(Monster.class, new MonsterLoader(objectMapper, new FileHandle("db/monster")));
		setLoader(MonsterList.class,
				new MonsterListLoader(objectMapper, new FileHandle("db/monster")));

		load("MonsterList", MonsterList.class);
	}

}
