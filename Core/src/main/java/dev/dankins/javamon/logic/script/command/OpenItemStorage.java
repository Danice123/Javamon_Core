package dev.dankins.javamon.logic.script.command;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.menu.ItemStorageHandler;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class OpenItemStorage extends Command {

	// !OpenItemStorage:
	public OpenItemStorage(final List<String> args) throws ScriptLoadingException {
		super(args);
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final ItemStorageHandler itemStorageHandler = new ItemStorageHandler(game);
		itemStorageHandler.waitAndHandle();

		return Optional.empty();
	}

}
