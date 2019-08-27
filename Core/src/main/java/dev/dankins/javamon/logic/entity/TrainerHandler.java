package dev.dankins.javamon.logic.entity;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.data.monster.instance.PartyImpl;
import dev.dankins.javamon.display.Spriteset;
import dev.dankins.javamon.logic.battlesystem.Trainer;

public class TrainerHandler extends WalkableHandler implements Trainer {

	private final String trainerName;
	private final AssetDescriptor<Texture> trainerImage;
	private final String trainerLossQuip;
	private final PartyImpl party;
	private final int winnings;
	private int range;

	public TrainerHandler(final String name, final Optional<Spriteset> sprites,
			final String trainerName, final String trainerImage, final String trainerLossQuip,
			final int winnings, final PartyImpl party) {
		super(name, sprites);
		this.trainerName = trainerName;
		this.trainerImage = new AssetDescriptor<Texture>(trainerImage, Texture.class);
		this.trainerLossQuip = trainerLossQuip;
		this.party = party;
		this.winnings = winnings;
		range = 0;
	}

	@Override
	public String getName() {
		return trainerName;
	}

	@Override
	public PartyImpl getParty() {
		return party;
	}

	@Override
	public boolean isTrainer() {
		return true;
	}

	@Override
	public String getTrainerLossQuip() {
		return trainerLossQuip;
	}

	@Override
	public int getWinnings() {
		return winnings;
	}

	@Override
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
