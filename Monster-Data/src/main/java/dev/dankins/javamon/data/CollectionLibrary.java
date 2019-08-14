package dev.dankins.javamon.data;

public interface CollectionLibrary {

	boolean isSeen(int i);

	boolean isCaught(int i);

	int amountSeen();

	int amountCaught();

}
