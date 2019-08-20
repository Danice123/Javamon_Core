package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.display.ShapeRendererRender;
import dev.dankins.javamon.display.SpriteBatchRenderer;

public class RenderHelper {

	private final SpriteBatch spriteBatch;
	private final ShapeHelper shapeHelper;

	public final RenderInfo ri;

	public RenderHelper(final RenderInfo ri, final SpriteBatch spriteBatch,
			final ShapeRenderer shapeRenderer) {
		this.ri = ri;
		this.spriteBatch = spriteBatch;
		shapeHelper = new ShapeHelper(shapeRenderer, ri);
	}

	public void withSpriteBatch(final SpriteBatchRenderer renderer) {
		spriteBatch.begin();
		renderer.render(spriteBatch);
		spriteBatch.end();
	}

	public void withShapeRenderer(final ShapeRendererRender renderer) {
		renderer.render(shapeHelper);
	}

	public void sprite(final TextureRegion tex, final int x, final int y, final int width,
			final int height) {
		spriteBatch.draw(tex, x * ri.getScale(), y * ri.getScale(), width * ri.getScale(),
				height * ri.getScale());
	}

	public void text(final BitmapFont font, final String text, final int x, final int y) {
		font.draw(spriteBatch, text, x * ri.getScale(), y * ri.getScale());
	}

	public void textWrapping(final BitmapFont font, final String text, final int x, final int y,
			final int width) {
		font.draw(spriteBatch, text, x * ri.getScale(), y * ri.getScale(), width * ri.getScale(),
				Align.left, true);
	}

	public void text(final BitmapFont font, final GlyphLayout layout, final int x, final int y) {
		font.draw(spriteBatch, layout, x * ri.getScale(), y * ri.getScale());
	}

	public void text(final BitmapFontCache cache) {
		cache.draw(spriteBatch);
	}

	public void text(final BitmapFontCache cache, final int start, final int end) {
		cache.draw(spriteBatch, start, end);
	}
}
