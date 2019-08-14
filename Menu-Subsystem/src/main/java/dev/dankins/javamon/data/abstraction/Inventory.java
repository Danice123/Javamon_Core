package dev.dankins.javamon.data.abstraction;

import java.util.List;

public interface Inventory {

	List<? extends Item> getItems();

}
