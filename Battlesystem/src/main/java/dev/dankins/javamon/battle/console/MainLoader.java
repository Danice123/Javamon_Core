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

	public final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().enable(Feature.MINIMIZE_QUOTES));

	public MainLoader(final String gamepath) {
		FileHandle baseDir = new FileHandle(gamepath);

		setLoader(Attack.class, new AttackLoader(objectMapper, baseDir.child("db").child("attack")));
		setLoader(Monster.class, new MonsterLoader(objectMapper, baseDir.child("db").child("monster")));
		setLoader(MonsterList.class, new MonsterListLoader(objectMapper, baseDir.child("db").child("monster")));

		load("MonsterList", MonsterList.class);
	}

}
