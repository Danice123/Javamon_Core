package dev.dankins.javamon.data.monster;

public enum Status {

	BURN("Burned"),
	FREEZE("Frozen"),
	PARALYSIS("Paralysed"),
	POISON("Poisoned"),
	POISON_TOXIC("Poisoned"),
	SLEEP("Sleeping"),
	FAINTED("Fainted"),
	NONE("OK");

	public final String name;

	private Status(final String name) {
		this.name = name;
	}

	public static Status isStatus(final String value) {
		for (final Status status : Status.values()) {
			if (status.name.equals(value)) {
				return status;
			}
		}
		return null;
	}
}
