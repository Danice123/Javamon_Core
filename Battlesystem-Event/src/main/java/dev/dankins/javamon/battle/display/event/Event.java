package dev.dankins.javamon.battle.display.event;

import java.util.Map;

import com.google.common.collect.Maps;

public class Event {

	public final EventType type;
	public final Map<String, Object> parameters;

	public Event(EventType type) {
		this.type = type;
		parameters = Maps.newHashMap();
	}
}
