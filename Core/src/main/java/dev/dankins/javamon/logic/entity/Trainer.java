package dev.dankins.javamon.logic.entity;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.display.Spriteset;
import dev.dankins.javamon.logic.Party;

public class Trainer extends WalkableHandler {

	private final String trainerName;
	private final AssetDescriptor<Texture> trainerImage;
	private final String trainerLossQuip;
	private final Party party;
	private final int winnings;
	private int range;

	public Trainer(final String name, final Optional<Spriteset> sprites, final String trainerName,
			final String trainerImage, final String trainerLossQuip, final int winnings, final Party party) {
		super(name, sprites);
		this.trainerName = trainerName;
		this.trainerImage = new AssetDescriptor<Texture>(trainerImage, Texture.class);
		this.trainerLossQuip = trainerLossQuip;
		this.party = party;
		this.winnings = winnings;
		range = 0;
	}

	public String getName() {
		return trainerName;
	}

	public Party getParty() {
		return party;
	}

	public String getTrainerLossQuip() {
		return trainerLossQuip;
	}

	public int getWinnings() {
		return winnings;
	}

	public AssetDescriptor<Texture> getImage() {
		return trainerImage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(final int range) {
		this.range = range;
	}

}
