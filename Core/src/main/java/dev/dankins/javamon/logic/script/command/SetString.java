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

public class SetString extends Command {

	private String stringName;
	private String stringValue;

	public SetString(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			stringName = i.next();
			stringValue = i.next();
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Goto",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {

		game.getPlayer().getStrings().put(parseString(stringName, strings),
				parse(parseString(stringValue, strings), game, target));
		strings.put(parseString(stringName, strings),
				parse(parseString(stringValue, strings), game, target));

		return Optional.empty();
	}

	private String parse(final String parseString, final Game game,
			final Optional<ScriptTarget> target) {
		switch (parseString.toLowerCase()) {
		case "ydiff":
			return Integer.toString(
					game.getPlayer().getY() - target.get().getEntityHandler().get().getY());
		}
		return parseString;
	}

}
