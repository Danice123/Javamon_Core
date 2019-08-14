package dev.dankins.javamon.logic.abstraction;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.abstraction.Inventory;

public interface Player {

	String getName();

	Texture getImage(AssetManager assets);

	int getMoney();

	CollectionLibrary getPokeData();

	Inventory getInventory();

}
