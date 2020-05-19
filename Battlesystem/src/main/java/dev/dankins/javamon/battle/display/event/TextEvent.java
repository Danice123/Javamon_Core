package dev.dankins.javamon.battle.display.event;

public class TextEvent extends GenericEvent {

	private final String text;

	public TextEvent(final EventType type, final String text) {
		super(type);
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
