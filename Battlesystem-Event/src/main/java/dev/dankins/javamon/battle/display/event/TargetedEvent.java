package dev.dankins.javamon.battle.display.event;

public class TargetedEvent extends GenericEvent implements Event {

	private final String target;

	public TargetedEvent(final EventType type, final String target) {
		super(type);
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

}
