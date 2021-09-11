package dev.dankins.javamon.battle.display.event;

public class TargetedEvent extends Event {

	public TargetedEvent(final EventType type, final String target) {
		super(type);
		this.parameters.put("Target", target);
	}

}
