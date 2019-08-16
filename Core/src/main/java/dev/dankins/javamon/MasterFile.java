package dev.dankins.javamon;

import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterFile {

	private final String name;

	private final String menuJar;
	public final String attackDBPath;
	public final String monsterDBPath;
	public final String itemDBPath;
	public final String mapPath;

	public final String playerSpriteset;
	public final String startScript;

	private FileHandle gameDirectory;

	@JsonCreator
	public MasterFile(@JsonProperty("name") final String name,
			@JsonProperty("menuJar") final String menuJar,
			@JsonProperty("attackDBPath") final String attackDBPath,
			@JsonProperty("monsterDBPath") final String monsterDBPath,
			@JsonProperty("itemDBPath") final String itemDBPath,
			@JsonProperty("mapPath") final String mapPath,
			@JsonProperty("playerSpriteset") final String playerSpriteset,
			@JsonProperty("startScript") final String startScript) {
		this.name = name;
		this.menuJar = menuJar;
		this.attackDBPath = attackDBPath;
		this.monsterDBPath = monsterDBPath;
		this.itemDBPath = itemDBPath;
		this.mapPath = mapPath;
		this.playerSpriteset = playerSpriteset;
		this.startScript = startScript;
	}

	public String getName() {
		return name;
	}

	public FileHandle getGameDirectory() {
		return gameDirectory;
	}

	public FileHandle getMenuJar() {
		return gameDirectory.child(menuJar);
	}

	void setGameDirectory(final FileHandle gameDirectory) {
		this.gameDirectory = gameDirectory;
	}

}
