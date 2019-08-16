package dev.dankins.javamon.display;

public class RenderInfo {

	public final int screenWidth = 240;
	public final int screenHeight = 160;

	public final int windowWidth;
	public final int windowHeight;

	public RenderInfo(final int windowWidth, final int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}

	public int getScale() {
		return windowWidth / screenWidth;
	}
}
