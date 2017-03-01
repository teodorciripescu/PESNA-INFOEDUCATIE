package com.pesna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pesna.init.GameRegistry;
import com.pesna.init.ScreenManager;
import com.pesna.player.Player;
import java.io.IOException;


public class Main extends ApplicationAdapter {
	
	public SpriteBatch batch;
	public AssetManager assetManager;
	public OrthographicCamera camera;
	public ShapeRenderer shapeRenderer;
	
	public Player player;
	public ScreenManager screenManager;
	public GameRegistry gameRegistry;
	
	public BitmapFont font;
	//public Viewport viewport;
	
	public boolean orthoscale = false;

	@Override
	public void create () {

		batch = new  SpriteBatch();
		camera = new OrthographicCamera(1280,720);
		//viewport = new FitViewport(1920,1080, camera);
		camera.setToOrtho(false,1280,720);
		assetManager = new AssetManager();
		shapeRenderer = new ShapeRenderer();

		try {
			player = new Player( this );
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameRegistry = new GameRegistry(this);
		screenManager = new ScreenManager(this);
		
		font = new BitmapFont();
	}
	
	@Override
	public void resize (int width, int height) {
		//camera.setToOrtho(false,width,height);
		//viewport.update(width, height);
		
		if ( orthoscale )
			camera.setToOrtho(false,width,height);
		
	}
	
	@Override
	public void dispose () 
	{
		shapeRenderer.dispose();
		assetManager.dispose();
		batch.dispose();
	}
	
	@Override
	public void render () {
		if ( Gdx.input.isKeyJustPressed(Keys.ESCAPE) )
		{
			Gdx.app.exit();
		}
		
		Gdx.gl.glClearColor( 0.5f, 0.5f, 0.5f , 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		
		
		screenManager.update();
		screenManager.draw();
	}
}
