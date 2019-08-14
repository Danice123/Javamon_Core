package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.Inventory;
import dev.dankins.javamon.data.item.Item;
import dev.dankins.javamon.data.item.ItemStack;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.ItemStorageMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class ItemStorageHandler extends MenuHandler<ItemStorageMenu> {

	static public final Class<? extends Menu> MENU_TYPE = ItemStorageMenu.class;
	static public Class<? extends ItemStorageMenu> Menu_Class;

	private final Inventory playerInventory;
	private final Inventory storageInventory;

	public ItemStorageHandler(final Game game) {
		super(game, Menu_Class);
		playerInventory = game.getPlayer().getInventory();
		storageInventory = game.getPlayer().getStorage();
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		ChatboxHandler chatboxHandler;
		ChooseItemHandler chooseItemHandler;
		Item chosenItem;
		switch (menu.getMenuChoice()) {
		case Take:
			chooseItemHandler = new ChooseItemHandler(game, storageInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					storageInventory.removeItem(chosenStack);
				}
				playerInventory.addItems(chosenStack, chosenAmount);
			} else {
				storageInventory.removeItem(chosenItem);
				playerInventory.addItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, "Withdrew " + chosenItem.getName() + ".");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Store:
			chooseItemHandler = new ChooseItemHandler(game, playerInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					playerInventory.removeItem(chosenStack);
				}
				storageInventory.addItems(chosenStack, chosenAmount);
			} else {
				playerInventory.removeItem(chosenItem);
				storageInventory.addItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, chosenItem.getName() + " was stored via PC.");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Toss:
			chooseItemHandler = new ChooseItemHandler(game, storageInventory);
			chooseItemHandler.waitAndHandle();
			if (chooseItemHandler.wasCancelled()) {
				return true;
			}

			chosenItem = chooseItemHandler.getChosenItem();

			if (!chosenItem.isTossable()) {
				chatboxHandler = new ChatboxHandler(game, "That's too important to toss!");
				chatboxHandler.waitAndHandle();
				ThreadUtils.sleep(10);
				return handleResponse();
			}

			if (chosenItem instanceof ItemStack) {
				final ItemStack chosenStack = (ItemStack) chosenItem;
				final int chosenAmount = chooseItemHandler.getChosenAmount();
				chosenStack.remove(chosenAmount);
				if (chosenStack.size() <= 0) {
					storageInventory.removeItem(chosenStack);
				}
			} else {
				storageInventory.removeItem(chosenItem);
			}

			ThreadUtils.sleep(10);
			chatboxHandler = new ChatboxHandler(game, "Threw away " + chosenItem.getName() + ".");
			chatboxHandler.waitAndHandle();
			ThreadUtils.sleep(10);

			return handleResponse();
		case Exit:
			return false;
		default:
			return true;
		}
	}

}
