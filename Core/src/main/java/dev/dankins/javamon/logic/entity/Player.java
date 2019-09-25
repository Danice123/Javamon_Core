package dev.dankins.javamon.logic.entity;

import java.util.Optional;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import dev.dankins.javamon.Coord;
import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.CollectionLibraryImpl;
import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.SaveFile;
import dev.dankins.javamon.data.monster.instance.PartyImpl;
import dev.dankins.javamon.display.Spriteset;
import dev.dankins.javamon.logic.battlesystem.Trainer;

public class Player extends WalkableHandler
		implements dev.dankins.javamon.logic.abstraction.Player, Trainer {

	private CollectionLibraryImpl pokeData;
	private PartyImpl party;
	private Inventory inventory;
	private Inventory itemStorage;
	private int money;
	private long id;

	public Player(final Optional<Spriteset> sprites) {
		super("Player", sprites);
		pokeData = new CollectionLibraryImpl();
		party = new PartyImpl();
		inventory = new Inventory();
		itemStorage = new Inventory();
		money = 0;
		id = RandomNumberGenerator.random.nextInt(1000000);
	}

	public long getPlayerId() {
		return id;
	}

	@Override
	public CollectionLibraryImpl getPokeData() {
		return pokeData;
	}

	@Override
	public PartyImpl getParty() {
		return party;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public Inventory getStorage() {
		return itemStorage;
	}

	@Override
	public int getMoney() {
		return money;
	}

	public boolean modifyMoney(final int mod) {
		if (money + mod < 0) {
			return false;
		}
		money += mod;
		return true;
	}

	public SaveFile createSave() {
		final SaveFile s = new SaveFile();
		s.money = money;
		s.id = id;
		s.strings = strings;
		s.pokeData = pokeData;
		s.party = party.serialize();
		s.inventory = inventory.serializeInventory();
		s.itemStorage = itemStorage.serializeInventory();
		s.facing = entity.getFacing();
		s.layer = getLayer();
		s.x = getX();
		s.y = getY();
		return s;
	}

	public String load(final AssetManager assetManager, final SaveFile s) {
		money = s.money;
		id = s.id;
		strings = s.strings;
		pokeData = s.pokeData;
		party = new PartyImpl(assetManager, s.party);
		inventory = new Inventory(assetManager, s.inventory);
		itemStorage = new Inventory(assetManager, s.itemStorage);
		entity.setFacing(s.facing);
		setCoord(new Coord(s.x, s.y), s.layer);
		return s.mapName;
	}

	@Override
	public String getName() {
		return strings.get("playerName");
	}

	@Override
	public AssetDescriptor<Texture> getImage() {
		return new AssetDescriptor<Texture>("entity/player.png", Texture.class);
	}

	@Override
	public AssetDescriptor<Texture> getBackImage() {
		return new AssetDescriptor<Texture>("playerBack.png", Texture.class);
	}

	@Override
	public boolean isTrainer() {
		return true;
	}

	@Override
	public String getTrainerLossQuip() {
		return "...";
	}

	@Override
	public int getWinnings() {
		return 0;
	}

}
