package dev.dankins.javamon.data.monster.instance;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public interface Trainer {

	String getKey();

	String getName();

	Party getParty_();

	AssetDescriptor<Texture> getImage();

	MonsterInstance getCurrentMonster_();

	boolean isTrainer();

	String getTrainerLossQuip();

}
