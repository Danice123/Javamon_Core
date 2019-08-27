package dev.dankins.javamon.logic.abstraction;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.abstraction.Inventory;

public interface Player extends Trainer {

	String getName();

	AssetDescriptor<Texture> getBackImage();

	int getMoney();

	CollectionLibrary getPokeData();

	Inventory getInventory();

}
