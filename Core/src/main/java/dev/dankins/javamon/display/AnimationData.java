package dev.dankins.javamon.display;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnimationData {

	public final int x;
	public final int y;

	@JsonCreator
	public AnimationData(@JsonProperty("x") final int x, @JsonProperty("y") final int y) {
		this.x = x;
		this.y = y;
	}

}
