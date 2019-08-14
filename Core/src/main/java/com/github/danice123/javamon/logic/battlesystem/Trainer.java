package com.github.danice123.javamon.logic.battlesystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.PartyImpl;

public interface Trainer extends dev.dankins.javamon.logic.abstraction.Trainer {

	String getName();

	PartyImpl getParty();

	Texture getImage(AssetManager assets);

	Texture getBackImage(AssetManager assets);

	boolean isTrainerBattle();

	String getTrainerLossQuip();

	int getWinnings();

	boolean modifyMoney(int winnings);

}
