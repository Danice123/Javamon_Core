package dev.dankins.javamon.data.monster;

import java.util.List;

import com.google.common.collect.Lists;

public class MultiType {

	private final List<Type> types;

	public MultiType(final Type... types) {
		this.types = Lists.newArrayList(types);
		if (types.length > 2) {
			throw new RuntimeException();
		}
	}

	public MultiType(final List<Type> types) {
		this.types = types;
		if (types.size() > 2) {
			throw new RuntimeException();
		}
	}

	public Type getPrimary() {
		return types.get(0);
	}

	public Type getSecondary() {
		return types.get(1);
	}

	public boolean isDualType() {
		return types.size() > 1;
	}

	public float getEffectiveness(final Type move) {
		return types.stream().map(type -> Type.getEffectiveness(type, move)).reduce(1f, (base, val) -> base * val);
	}

	@Override
	public boolean equals(final Object obj) {
		if (MultiType.class.isInstance(obj)) {
			final MultiType other = (MultiType) obj;
			return types.equals(other.types);
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		if (types.size() > 1) {
			return types.get(0).name + "/" + types.get(1).name;
		}
		return types.get(0).name;
	}
}
