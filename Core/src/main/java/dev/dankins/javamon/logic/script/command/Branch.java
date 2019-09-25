package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Branch extends Command {

	private final String branch;
	private final Query query;

	// !Branch:<Branch> <StringName>
	// !Branch:<Branch> <StringName> <StringValue>
	// !Branch:<Branch> Item <ItemToCheck>
	public Branch(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			branch = i.next();

			final String queryType = i.next();
			switch (queryType) {
			case "Item":
				query = new PlayerItem(i.next());
				break;
			default:
				if (i.hasNext()) {
					String value = i.next();
					while (i.hasNext()) {
						value = value + " " + i.next();
					}
					query = new StringQuery(queryType, value);
				} else {
					query = new StringQuery(queryType);
				}
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Branch",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		if (query.resolve(game, strings, target)) {
			return Optional.of(parseString(branch, strings));
		}
		return Optional.empty();
	}

	private interface Query {
		boolean resolve(Game game, Map<String, String> strings, Optional<ScriptTarget> target);
	}

	private class StringQuery implements Query {

		private final String string;
		private final Optional<String> value;

		public StringQuery(final String string) {
			this.string = string;
			value = Optional.empty();
		}

		public StringQuery(final String string, final String value) {
			this.string = string;
			this.value = Optional.of(value);
		}

		@Override
		public boolean resolve(final Game game, final Map<String, String> strings,
				final Optional<ScriptTarget> target) {
			if (value.isPresent()) {
				return strings.get(parseString(string, strings))
						.equals(parseString(value.get(), strings));
			}
			return strings.containsKey(parseString(string, strings));
		}

	}

	private class PlayerItem implements Query {

		private final String item;

		public PlayerItem(final String item) {
			this.item = item;
		}

		@Override
		public boolean resolve(final Game game, final Map<String, String> strings,
				final Optional<ScriptTarget> target) {
			return game.getPlayer().getInventory().hasItem(parseString(item, strings));
		}

	}

}
