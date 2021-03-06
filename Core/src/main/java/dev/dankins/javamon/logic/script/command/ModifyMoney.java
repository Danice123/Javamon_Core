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

public class ModifyMoney extends Command {

	private int amount;
	private Optional<String> branchOnSuccess = Optional.empty();

	// !ModifyMoney:<Amount> (<branchOnSuccess>)
	public ModifyMoney(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			amount = Integer.parseInt(i.next());
			if (i.hasNext()) {
				branchOnSuccess = Optional.of(i.next());
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("ModifyMoney",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		} catch (final NumberFormatException e) {
			throw new ScriptLoadingException("ModifyMoney",
					SCRIPT_LOADING_ERROR_TYPE.invalidArgument);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final boolean success = game.getPlayer().modifyMoney(amount);
		if (branchOnSuccess.isPresent() && success) {
			return Optional.of(parseString(branchOnSuccess.get(), strings));
		}
		return Optional.empty();
	}

}
