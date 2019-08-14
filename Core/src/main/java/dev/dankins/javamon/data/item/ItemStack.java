package dev.dankins.javamon.data.item;

public class ItemStack extends Item implements dev.dankins.javamon.data.abstraction.ItemStack {

	private int amount;

	public ItemStack(final Item baseItem, final int amount) {
		super(baseItem);
		this.amount = amount;
	}

	@Override
	public int size() {
		return amount;
	}

	public void add(final int add) {
		amount += add;
	}

	public void remove(final int remove) {
		amount -= remove;
	}

	public void set(final int set) {
		amount = set;
	}

}
