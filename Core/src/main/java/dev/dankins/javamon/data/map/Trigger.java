package dev.dankins.javamon.data.map;

import java.util.Map;
import java.util.Optional;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.data.script.Script;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.entity.EntityHandler;
import dev.dankins.javamon.logic.script.ScriptHandler;
import dev.dankins.javamon.logic.script.ScriptTarget;

public class Trigger implements ScriptTarget {

	private final Script script;
	private final Map<String, String> strings;
	private final Optional<String> name;

	public Trigger(final Script script, final Map<String, String> strings) {
		this.script = script;
		this.strings = strings;
		name = Optional.empty();
	}

	public Trigger(final String name, final Script script, final Map<String, String> strings) {
		this.name = Optional.ofNullable(name);
		this.script = script;
		this.strings = strings;
	}

	public void activate(final Game game) {
		if (name.isPresent() && game.getPlayer().getStrings().containsKey(name.get())) {
			return;
		}

		// TODO: Not threaded?
		new Thread(new ScriptHandler(game, script, this)).start();
		ThreadUtils.sleep(100);
	}

	@Override
	public Optional<EntityHandler> getEntityHandler() {
		return Optional.empty();
	}

	@Override
	public Map<String, String> getStrings() {
		return strings;
	}

}
