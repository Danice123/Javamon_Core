package dev.dankins.javamon.logic.abstraction;

import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.abstraction.Inventory;
import dev.dankins.javamon.data.monster.instance.Party;

public interface Player {

	String getName();

	AssetDescriptor<Texture> getImage();

	int getMoney();

	Party getParty_();

	CollectionLibrary getPokeData();

	Inventory getInventory();

	Map<String, String> getStrings();

}
