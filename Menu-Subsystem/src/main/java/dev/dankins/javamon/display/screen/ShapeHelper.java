package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import dev.dankins.javamon.display.RenderInfo;

public class ShapeHelper {
	
	private final ShapeRenderer shapeRenderer;
	private final ShapeWrapper wrapper;
	
	public ShapeHelper(ShapeRenderer shapeRenderer, RenderInfo ri) {
		this.shapeRenderer = shapeRenderer;
		wrapper = new ShapeWrapper(ri.getScale());
	}
	
	public void filled(ShapeCommands commands) {
		shapeRenderer.begin(ShapeType.Filled);
		commands.render(wrapper);
		shapeRenderer.end();
	}

	public interface ShapeCommands {
		void render(ShapeWrapper wrapper);
	}
	
	public class ShapeWrapper {

		private final float scale;

		public ShapeWrapper(float scale) {
			this.scale = scale;
		}
		
		public void rect(Color color, float x, float y, float width, float height) {
			shapeRenderer.setColor(color);
			shapeRenderer.rect(x * scale, y * scale, width * scale, height * scale);
		}
		
		public void percentBar(float x, float y, float width, float height, float currentHealthPercent) {
			rect(Color.BLACK, x, y + 1, width, height - 2);
			rect(Color.BLACK, x + 1, y, width - 2, height);
			rect(Color.WHITE, x + 1, y + 1, width - 2, height - 2);
			
			rect(Color.GREEN, x + 1, y + 1, currentHealthPercent * (width - 2), height - 2);
		}
		
	}
}


