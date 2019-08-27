package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import dev.dankins.javamon.display.RenderInfo;

public class ShapeHelper {

	private final ShapeRenderer shapeRenderer;
	private final ShapeWrapper wrapper;

	public ShapeHelper(final ShapeRenderer shapeRenderer, final RenderInfo ri) {
		this.shapeRenderer = shapeRenderer;
		wrapper = new ShapeWrapper(ri.getScale());
	}

	public void filled(final ShapeCommands commands) {
		shapeRenderer.begin(ShapeType.Filled);
		commands.render(wrapper);
		shapeRenderer.end();
	}

	public interface ShapeCommands {
		void render(ShapeWrapper wrapper);
	}

	public class ShapeWrapper {

		private final float scale;

		public ShapeWrapper(final float scale) {
			this.scale = scale;
		}

		public void rect(final Color color, final float x, final float y, final float width,
				final float height) {
			shapeRenderer.setColor(color);
			shapeRenderer.rect(x * scale, y * scale, width * scale, height * scale);
		}

		public void percentBar(final float x, final float y, final float width, final float height,
				final float currentHealthPercent) {
			rect(Color.BLACK, x, y + 1, width, height - 2);
			rect(Color.BLACK, x + 1, y, width - 2, height);
			rect(Color.WHITE, x + 1, y + 1, width - 2, height - 2);

			rect(Color.GREEN, x + 1, y + 1, currentHealthPercent * (width - 2), height - 2);
		}

		public void arrowCorner(final int cornerX, final int cornerY, final int width,
				final int height) {
			final Color black = new Color(0f, 0f, 0f, 0f);
			// Line Up
			rect(black, cornerX, cornerY, 2, height);

			// Line Over
			rect(black, cornerX - width, cornerY, width, 2);
			rect(black, cornerX - (width + 2), cornerY, 2, 1);
			rect(black, cornerX - (width - 2), cornerY, 4, 3);
			rect(black, cornerX - (width - 4), cornerY, 2, 4);
		}

		public void arrowCornerRight(final int cornerX, final int cornerY, final int width,
				final int height) {
			final Color black = new Color(0f, 0f, 0f, 0f);
			// Line Up
			rect(black, cornerX, cornerY, 2, height);

			// Line Over
			rect(black, cornerX, cornerY, width, 2);
			rect(black, cornerX + width, cornerY, 2, 1);
			rect(black, cornerX + width - 4, cornerY, 2, 3);
			rect(black, cornerX + width - 6, cornerY, 2, 4);
		}

	}
}
