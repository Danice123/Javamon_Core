package dev.dankins.javamon.data.monster;

public interface Monster {

	String getName();

	int getNumber();

	String getFormattedNumber();

	Type getType(int typeSlot);

	boolean isDualType();

	int getHeight();

	int getWeight();

	String getSpecies();

	String getDescription();

}
