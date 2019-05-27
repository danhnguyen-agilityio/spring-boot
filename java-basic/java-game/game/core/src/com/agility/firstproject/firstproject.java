package com.agility.firstproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.ViewPort;

// SpriteBatch
// All SpriteBatch drawing calls must be made between the begin and end methods.

// Texture
// The Texture class decodes an image file and loads it into GPU memory.
// The image file should be placed in the "assets" folder.
// The image's dimensions should be powers of two (16x16, 64x256, etc) for compatibility and performance reasons.

// Question
// Example draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2)
// Example draw(Texture texture, float[] spriteVertices, int offset, int length)

// TextureRegion
// The TextureRegion class describes a rectangle inside a texture and is useful for drawing only a portion of the texture

// Sprite
// The Sprite class describes both a texture region, the geometry where it will be drawn, and the color it will be drawn
// Sprite makes it convenient to have a single object that describes everything
// Sprite stores the geometry and only recomputes it when necessary, it is slightly more efficient if the scale,
// rotation, or other properties are unchanged between frames.

// Question:
// Sprite stores the geometry and only recomputes it when necessary, it is slightly more efficient if the scale,
// rotation, or other properties are unchanged between frames.

// Tinting
// When a texture is drawn, it can be tinted a color

public class firstproject extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	Sprite sprite;
	Viewport viewport;
	Camera camera;
	float x = 0;
	float y = 0;
	
	@Override
	public void create () {
		System.out.println("Create: order 1: Application is created");
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img = new Texture(Gdx.files.internal("badlogic.jpg"));
		region = new TextureRegion(img, 0, 0, 100, 100);
		sprite = new Sprite(img, 0, 0, 100, 100);
		sprite.setPosition(10, 10);
		sprite.setRotation(45);
//		sprite.setColor(0, 0, 1, 1);

		camera = new PerspectiveCamera();
		viewport = new FitViewport(800, 480, camera);
	}

	@Override
	public void render () {
//		System.out.println("Render: order 3: Render app, game logic update");
		Gdx.gl.glClearColor(1, 0, 0, 1);

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// FIXME:: Not know
		// Active texture unit is 0
		// Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

		batch.begin();
		// FIXME:: Not see different
		batch.disableBlending();
		// Draw the texture with specific position
		batch.setColor(1, 0, 0, 1);
		batch.draw(img, 50, 200);

		// Draws a portion of the texture
//		 batch.draw(img, 300, 150, 0, 0, 100, 200);

		// Draws a portion of a texture, stretched to the with and height,
		// batch.draw(img, 50, 150, 150, 300, 0, 0,
		//		100, 200, true, true);

		// This monster method draws a portion of a texture, stretched to the width and height, scaled and rotated
		// around an origin, and optionally flipped
//		batch.draw(img, 200, 150, 100, 200, 100, 100, 2, 1,
//				45, 50, 50, 100, 100, false, false);

		// Draw the region using the width and height of the region
//		batch.draw(region, 50, 50);

		// draws the region, stretched to the width and height
//		batch.draw(region, 200, 50 , 150, 50);

		// Draws the region, stretched to the width and height, and scaled and rotated around an origin
//		batch.draw(region, 200, 50, 0, 0, 150, 50, 2, 1, 45);

		// Draw sprite
		sprite.draw(batch);
		// Set new position of sprite
		if (x < 100) x += 1;
		if (y < 100) y += 1;
		sprite.setPosition(x, y);

		batch.enableBlending();
		batch.end();
	}

	@Override
	public void dispose () {
		System.out.println("Dispose: order 6: application is destroyed");
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("Resize: order 2: Screen is resized and the game is not in the pause state: " + width + " " + height);
		super.resize(width, height);
	}

	@Override
	public void pause() {
		System.out.println("Pause: order 4: existing the application, good place to save the game state");
		super.pause();
	}

	@Override
	public void resume() {
		System.out.println("Resume: order 5: Only called on Android");
		super.resume();
	}
}
