package dev.dankins.javamon.battle.data.attack.effect.proxy;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.effect.Effect;
import dev.dankins.javamon.battle.display.event.Event;

public class ProxyEffect implements Effect {

	public static ObjectMapper OBJECT_MAPPER;
	public static FileHandle EFFECT_DIR;

	private final String name;
	private ProxyEffectDef effectDef;

	@JsonCreator
	public ProxyEffect(@JsonProperty("name") final String name) {
		this.name = name;
		try {
			effectDef = OBJECT_MAPPER.readValue(
					EFFECT_DIR.child(name.replace(' ', '_') + ".yaml").file(),
					ProxyEffectDef.class);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		return effectDef.use(attack, target);
	}
}
