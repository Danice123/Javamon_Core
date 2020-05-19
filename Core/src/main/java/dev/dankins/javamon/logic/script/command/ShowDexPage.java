package dev.dankins.javamon.logic.script.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import dev.dankins.javamon.battle.data.monster.MonsterImpl;
import dev.dankins.javamon.data.script.ScriptLoadingException;
import dev.dankins.javamon.data.script.ScriptLoadingException.SCRIPT_LOADING_ERROR_TYPE;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.menu.PokedexPageHandler;
import dev.dankins.javamon.logic.script.Command;
import dev.dankins.javamon.logic.script.ScriptException;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class ShowDexPage extends Command {

	private String monster;
	private boolean withInfo;

	// !ShowDexPage:<Monster> <withInfo>
	public ShowDexPage(final List<String> args) throws ScriptLoadingException {
		super(args);
		try {
			final Iterator<String> i = args.iterator();
			monster = i.next();
			if (i.hasNext()) {
				withInfo = Boolean.parseBoolean(i.next());
			} else {
				withInfo = false;
			}
		} catch (final NoSuchElementException e) {
			throw new ScriptLoadingException("ShowDexPage",
					SCRIPT_LOADING_ERROR_TYPE.invalidNumberOfArguments);
		}
	}

	@Override
	public Optional<String> execute(final Game game, final Map<String, String> strings,
			final Optional<ScriptTarget> target) throws ScriptException {
		final MonsterImpl pokemon = game.getMonsterList().getMonster(parseString(monster, strings));
		final PokedexPageHandler handler = new PokedexPageHandler(game, pokemon, withInfo);
		handler.waitAndHandle();
		game.getPlayer().getPokeData().seen(pokemon.number);

		return Optional.empty();
	}

}
