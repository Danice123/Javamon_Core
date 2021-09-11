package dev.dankins.javamon.battle.display.event;

public class TextEvent extends Event {

	public TextEvent(final EventType type, final String text) {
		super(type);
		this.parameters.put("Text", text);
	}

}
