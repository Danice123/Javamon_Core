package dev.dankins.javamon.logic.abstraction;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.Party;

public interface Trainer {

	Party getParty();

	AssetDescriptor<Texture> getImage();

	boolean isTrainer();

}
