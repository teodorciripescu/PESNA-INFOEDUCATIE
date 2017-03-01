package com.pesna.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.pesna.Main;
import com.pesna.objects.ScreenObject;

public class GuiButton implements ScreenObject {
	public Texture texture;
	public Rectangle bounds;
	public int commandType = 0;
	public int x,y;
	
	public GuiButton ( Main reference, int _commandType, int _x, int _y )
	{
		commandType = _commandType;

		/*
		reference.assetManager.load("menu/close0.png" , Texture.class);
		reference.assetManager.load("menu/play0.png" , Texture.class);
		reference.assetManager.load("menu/info0.png" , Texture.class);
		reference.assetManager.load("menu/settings0.png" , Texture.class);
		
		
		buttons.add( new GuiButton( _reference, 0, -Gdx.graphics.getWidth()*2/2, -Gdx.graphics.getHeight()*1 ) );
		buttons.add( new GuiButton( _reference, 1, -Gdx.graphics.getWidth()*1/2, -Gdx.graphics.getHeight()*1 ) );
		buttons.add( new GuiButton( _reference, 2, -Gdx.graphics.getWidth()*0/2, -Gdx.graphics.getHeight()*1 ) );
		buttons.add( new GuiButton( _reference, 3, Gdx.graphics.getWidth()*1/2, -Gdx.graphics.getHeight()*1 ) );
		
		 */
		
		switch( commandType )
		{
		case 0://Play
			texture = reference.assetManager.get("menu/play0.png", Texture.class);
			
			//bounds.x = -Gdx.graphics.getWidth()*2/2;
			//bounds.y = -Gdx.graphics.getHeight()*1;
			break;
		case 1://Settings
			texture = reference.assetManager.get("menu/settings0.png", Texture.class);
			//bounds.x = -Gdx.graphics.getWidth()*1/2;
			//bounds.y = -Gdx.graphics.getHeight()*1;
			break;
		case 2://Credits
			texture = reference.assetManager.get("menu/info0.png", Texture.class);
			//bounds.x = -Gdx.graphics.getWidth()*0/2;
			//bounds.y = -Gdx.graphics.getHeight()*1;
			break;
		case 3://Exit
			texture = reference.assetManager.get("menu/close0.png", Texture.class);
			//bounds.x = Gdx.graphics.getWidth()*1/2;
			//bounds.y = -Gdx.graphics.getHeight()*1;
			break;
		}
		
		
		//bounds.height=272;
		//bounds.width=272;
		
		
		x = _x;
		y = _y;
	}
	
	public void runCommand( Main _reference )
	{
		//System.out.println( Integer.toString(commandType) );
		switch( commandType )
		{
		case 0://Play
			_reference.screenManager.queueScreen( _reference.screenManager.gameScreen );
			_reference.orthoscale = true;
			_reference.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			break;
		case 1://Settings
			
			break;
		case 2://Credits
			
			break;
		case 3://Exit
			Gdx.app.exit();
			break;
		}
		
	}

	public void draw( Main _reference )
	{
		_reference.batch.draw(texture,x,y);
	}
	public void update( Main _reference )
	{
		
	}
}
