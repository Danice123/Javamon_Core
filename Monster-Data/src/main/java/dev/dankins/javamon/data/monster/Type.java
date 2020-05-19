package dev.dankins.javamon.data.monster;

public enum Type {

	BUG(7, "Bug"),
	DARK(17, "Dark"),
	DRAGON(16, "Dragon"),
	ELECTRIC(13, "Electric"),
	FIGHTING(2, "Fighting"),
	FIRE(10, "Fire"),
	FLYING(3, "Flying"),
	GHOST(8, "Ghost"),
	GRASS(12, "Grass"),
	GROUND(5, "Ground"),
	ICE(15, "Ice"),
	NORMAL(1, "Normal"),
	POISON(4, "Poison"),
	PSYCHIC(14, "Psychic"),
	ROCK(6, "Rock"),
	STEEL(9, "Steel"),
	WATER(11, "Water");

	private int id;
	public final String name;

	Type(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int id() {
		return id;
	}

	public static float getEffectiveness(final Type target, final Type move) {
		switch (target) {
		case BUG:
			switch (move) {
			case FIGHTING:
				return 0.5f;
			case FIRE:
				return 2f;
			case FLYING:
				return 2f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 0.5f;
			case ROCK:
				return 2f;
			default:
				return 1f;
			}
		case DARK:
			switch (move) {
			case BUG:
				return 2f;
			case DARK:
				return 0.5f;
			case FIGHTING:
				return 2f;
			case GHOST:
				return 0.5f;
			case PSYCHIC:
				return 0f;
			default:
				return 1f;
			}
		case DRAGON:
			switch (move) {
			case DRAGON:
				return 2f;
			case ELECTRIC:
				return 0.5f;
			case FIRE:
				return 0.5f;
			case GRASS:
				return 0.5f;
			case ICE:
				return 2f;
			case WATER:
				return 0.5f;
			default:
				return 1f;
			}
		case ELECTRIC:
			switch (move) {
			case ELECTRIC:
				return 0.5f;
			case FLYING:
				return 0.5f;
			case GROUND:
				return 2f;
			case STEEL:
				return 0.5f;
			default:
				return 1f;
			}
		case FIGHTING:
			switch (move) {
			case BUG:
				return 0.5f;
			case DARK:
				return 0.5f;
			case FLYING:
				return 2f;
			case PSYCHIC:
				return 2f;
			case ROCK:
				return 0.5f;
			default:
				return 1f;
			}
		case FIRE:
			switch (move) {
			case BUG:
				return 0.5f;
			case FIRE:
				return 0.5f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 2f;
			case ICE:
				return 0.5f;
			case ROCK:
				return 2f;
			case STEEL:
				return 0.5f;
			case WATER:
				return 2f;
			default:
				return 1f;
			}
		case FLYING:
			switch (move) {
			case BUG:
				return 0.5f;
			case ELECTRIC:
				return 2f;
			case FIGHTING:
				return 0.5f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 0f;
			case ICE:
				return 2f;
			case ROCK:
				return 2f;
			default:
				return 1f;
			}
		case GHOST:
			switch (move) {
			case BUG:
				return 0.5f;
			case DARK:
				return 2f;
			case FIGHTING:
				return 0f;
			case GHOST:
				return 2f;
			case NORMAL:
				return 0f;
			case POISON:
				return 0.5f;
			default:
				return 1f;
			}
		case GRASS:
			switch (move) {
			case BUG:
				return 2f;
			case ELECTRIC:
				return 0.5f;
			case FIRE:
				return 2f;
			case FLYING:
				return 2f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 0.5f;
			case ICE:
				return 2f;
			case POISON:
				return 2f;
			case WATER:
				return 0.5f;
			default:
				return 1f;
			}
		case GROUND:
			switch (move) {
			case ELECTRIC:
				return 0f;
			case GRASS:
				return 2f;
			case ICE:
				return 2f;
			case POISON:
				return 0.5f;
			case ROCK:
				return 0.5f;
			case WATER:
				return 2f;
			default:
				return 1f;
			}
		case ICE:
			switch (move) {
			case FIGHTING:
				return 2f;
			case FIRE:
				return 2f;
			case ICE:
				return 0.5f;
			case ROCK:
				return 2f;
			case STEEL:
				return 2f;
			default:
				return 1f;
			}
		case NORMAL:
			switch (move) {
			case FIGHTING:
				return 2f;
			case GHOST:
				return 0f;
			default:
				return 1f;
			}
		case POISON:
			switch (move) {
			case BUG:
				return 0.5f;
			case FIGHTING:
				return 0.5f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 2f;
			case POISON:
				return 0.5f;
			case PSYCHIC:
				return 2f;
			default:
				return 1f;
			}
		case PSYCHIC:
			switch (move) {
			case BUG:
				return 2f;
			case DARK:
				return 2f;
			case FIGHTING:
				return 0.5f;
			case GHOST:
				return 2f;
			case PSYCHIC:
				return 0.5f;
			default:
				return 1f;
			}
		case ROCK:
			switch (move) {
			case FIGHTING:
				return 2f;
			case FIRE:
				return 0.5f;
			case FLYING:
				return 0.5f;
			case GRASS:
				return 2f;
			case GROUND:
				return 2f;
			case NORMAL:
				return 0.5f;
			case POISON:
				return 0.5f;
			case STEEL:
				return 2f;
			case WATER:
				return 2f;
			default:
				return 1f;
			}
		case STEEL:
			switch (move) {
			case BUG:
				return 0.5f;
			case DARK:
				return 0.5f;
			case DRAGON:
				return 0.5f;
			case FIGHTING:
				return 2f;
			case FIRE:
				return 2f;
			case FLYING:
				return 0.5f;
			case GHOST:
				return 0.5f;
			case GRASS:
				return 0.5f;
			case GROUND:
				return 2f;
			case ICE:
				return 0.5f;
			case NORMAL:
				return 0.5f;
			case POISON:
				return 0f;
			case PSYCHIC:
				return 0.5f;
			case ROCK:
				return 0.5f;
			case STEEL:
				return 0.5f;
			default:
				return 1f;
			}
		case WATER:
			switch (move) {
			case ELECTRIC:
				return 2f;
			case FIRE:
				return 0.5f;
			case GRASS:
				return 2f;
			case ICE:
				return 0.5f;
			case STEEL:
				return 0.5f;
			case WATER:
				return 0.5f;
			default:
				return 1f;
			}
		default:
			return 1f;
		}
	}

	public static Type getType(final int id) {
		switch (id) {
		case 1:
			return NORMAL;
		case 2:
			return FIGHTING;
		case 3:
			return FLYING;
		case 4:
			return POISON;
		case 5:
			return GROUND;
		case 6:
			return ROCK;
		case 7:
			return BUG;
		case 8:
			return GHOST;
		case 9:
			return STEEL;
		case 10:
			return FIRE;
		case 11:
			return WATER;
		case 12:
			return GRASS;
		case 13:
			return ELECTRIC;
		case 14:
			return PSYCHIC;
		case 15:
			return ICE;
		case 16:
			return DRAGON;
		case 17:
			return DARK;
		default:
			return null;
		}
	}
}
