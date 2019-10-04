package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.menu.ChoiceboxHandler;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Choose extends Command {

	private String textReference;
	private String output;
	private List<String> options;

	// !Choose:<Display text> <Cache Output> <options>...
	public Choose(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			textReference = i.next();
			output = i.next();
			options = Lists.newArrayList();
			while (i.hasNext()) {
				options.add(i.next());
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("Choose",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final String parsedText = parseString(strings.get(parseString(textReference, strings)),
				strings);
		final List<String> parsedOptions = options.stream()
				.map(option -> parseString(option, strings)).collect(Collectors.toList());

		final ChoiceboxHandler box = new ChoiceboxHandler(game, parsedText, parsedOptions);
		strings.put(parseString(output, strings), box.waitForResponse());

		return Optional.empty();
	}

}
