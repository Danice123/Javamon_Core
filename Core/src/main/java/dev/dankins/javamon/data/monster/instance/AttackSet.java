package dev.dankins.javamon.data.monster.instance;

import java.util.AbstractList;
import java.util.Collection;

public class AttackSet extends AbstractList<AttackInstanceImpl> {

	private static final int NUMBER_OF_ATTACKS = 4;

	private final AttackInstanceImpl[] attacks = new AttackInstanceImpl[NUMBER_OF_ATTACKS];

	public AttackSet() {
	}

	public AttackSet(final Collection<AttackInstanceImpl> attacks) {
		int i = 0;
		for (final AttackInstanceImpl attack : attacks) {
			this.attacks[i++] = attack;
			if (i == NUMBER_OF_ATTACKS) {
				break;
			}
		}
	}

	@Override
	public AttackInstanceImpl get(final int index) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (attacks[index] == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return attacks[index];
	}

	@Override
	public boolean add(final AttackInstanceImpl attack) {
		if (size() == NUMBER_OF_ATTACKS) {
			return false;
		}
		set(size(), attack);
		return true;
	}

	@Override
	public AttackInstanceImpl set(final int index, final AttackInstanceImpl monster) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final AttackInstanceImpl removed = attacks[index];
		attacks[index] = monster;
		return removed;
	}

	@Override
	public AttackInstanceImpl remove(final int index) {
		if (index >= NUMBER_OF_ATTACKS) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		final AttackInstanceImpl removed = attacks[index];
		for (int i = index; i < NUMBER_OF_ATTACKS - 1; i++) {
			if (attacks[i + 1] != null) {
				attacks[i] = attacks[i + 1];
				attacks[i + 1] = null;
			}
		}
		return removed;
	}

	@Override
	public int size() {
		for (int i = 0; i < NUMBER_OF_ATTACKS; i++) {
			if (attacks[i] == null) {
				return i;
			}
		}
		return NUMBER_OF_ATTACKS;
	}

}
