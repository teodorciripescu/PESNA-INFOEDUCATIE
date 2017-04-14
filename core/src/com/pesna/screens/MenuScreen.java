package com.pesna.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.pesna.Main;
import com.pesna.entities.EnemyObject;
import com.pesna.gui.GuiButton;
import com.pesna.objects.ScreenObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class MenuScreen implements IScreen, InputProcessor {
	public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	
	Texture background;
	
	Main reference;

	Texture buttonTexture;
	
	public MenuScreen( Main _reference )
	{
		reference = _reference;
		Gdx.input.setInputProcessor(this);
		
		//buttons.add( new GuiButton( _reference, 0, Gdx.graphics.getWidth()/2-300-136, Gdx.graphics.getHeight()/8 ) );
		//System.out.println( Gdx.graphics.getWidth()*1/2 + "  " + Gdx.graphics.getHeight()/3);
		//buttons.add( new GuiButton( _reference, 1, Gdx.graphics.getWidth()*3/4-136, Gdx.graphics.getHeight()/8 ) );
		//buttons.add( new GuiButton( _reference, 2, Gdx.graphics.getWidth()*2/4-136, Gdx.graphics.getHeight()/8 ) );
		//buttons.add( new GuiButton( _reference, 3, Gdx.graphics.getWidth()/2+300-136, Gdx.graphics.getHeight()/8 ) );
		
		
		background = _reference.assetManager.get( "menu.png", Texture.class );
		buttonTexture = _reference.assetManager.get( "buttons.png", Texture.class );
	}
	
	public void update( Main _reference )
	{
		for ( GuiButton object : buttons )
		{
			object.update( _reference );
		}
	}
	
	public void draw( Main _reference )
	{	
		_reference.batch.setProjectionMatrix(_reference.camera.combined);
		_reference.batch.setProjectionMatrix(_reference.camera.combined);
		_reference.batch.begin();
		//_reference.batch.draw( background, -Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2 );
		_reference.batch.draw( background, 0,0 );
		_reference.batch.draw( buttonTexture, 0, 0 );
		for ( ScreenObject object : buttons )
		{
			object.draw( _reference );
		}
		
		_reference.batch.end();
	}
	public void SpellForceAdd(ScreenObject newObject)
	{
		
	}

	@Override
	public void ObjectForceAdd(ScreenObject newObject) {

	}

	@Override
	public LinkedList<EnemyObject> GetLevelEnemy() {
		return null;
	}

	public boolean keyDown ( int keycode )
	{
		return false;
	}
	public boolean keyUp (int keycode)
	{
		return false;
	}
	public boolean keyTyped (char character)
	{
		return false;
	}
	public boolean touchDown (int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	public boolean touchUp (int screenX, int screenY, int pointer, int button)
	{
		//System.out.println( Integer.toString(screenX) );
		//System.out.println( Integer.toString( Gdx.graphics.getHeight()- screenY) );
		
		//System.out.println( "screen" + screenX + " " + screenY );

		/*
		int clickX = screenX;
		int clickY = Gdx.graphics.getHeight() - screenY;
		
		//System.out.println( "ante" + clickX + " " + clickY );
		
		clickX = clickX*1920/Gdx.graphics.getWidth();
		clickY = clickY*1080/Gdx.graphics.getHeight();
		
		//System.out.println( clickX + " " + clickY );
		
		for ( GuiButton object : buttons )
		{
			//System.out.println( clickY + " "+ object.y + " " + object.y+272);
			//System.out.println("OBJ");
			if ( clickX > object.x && clickX < object.x+272 )
			{
				//System.out.println("Cycle1");
				if ( clickY > object.y && clickY < object.y+272 )
				{
					//System.out.println("Cycle2");
					object.runCommand(reference);
					return true;
				}
			}
		}
		*/
		reference.screenManager.queueScreen( reference.screenManager.gameScreen );
		reference.orthoscale = true;
		reference.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		return false;
	}
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
		return false;
	}
	public boolean mouseMoved (int screenX, int screenY)
	{
		//System.out.println( Integer.toString(screenX) );
		//System.out.println( Integer.toString( Gdx.graphics.getHeight()- screenY) );
		
		int clickX = screenX;
		int clickY = Gdx.graphics.getHeight() - screenY;
		
		//System.out.println( "ante" + clickX + " " + clickY );
		
		clickX = clickX*1920/Gdx.graphics.getWidth();
		clickY = clickY*1080/Gdx.graphics.getHeight();
		
		
		//System.out.println("CLICK");
		
		for ( GuiButton object : buttons )
		{
			switch( object.commandType )
			{
			case 0://Play
				object.texture = reference.assetManager.get("menu/play0.png", Texture.class);
				break;
			case 1://Settings
				object.texture = reference.assetManager.get("menu/settings0.png", Texture.class);
				break;
			case 2://Credits
				object.texture = reference.assetManager.get("menu/info0.png", Texture.class);
				break;
			case 3://Exit
				object.texture = reference.assetManager.get("menu/close0.png", Texture.class);
				break;
			}
			
			//System.out.println( clickY + " "+ object.y + " " + object.y+272);
			//System.out.println("OBJ");
			if ( clickX > object.x && clickX < object.x+272 )
			{
				//System.out.println("Cycle1");
				if ( clickY > object.y && clickY < object.y+272 )
				{
					//System.out.println("Cycle2");
					switch( object.commandType )
					{
					case 0://Play
						object.texture = reference.assetManager.get("menu/play1.png", Texture.class);
						return true;
					case 1://Settings
						object.texture = reference.assetManager.get("menu/settings1.png", Texture.class);
						return true;
					case 2://Credits
						object.texture = reference.assetManager.get("menu/info1.png", Texture.class);
						return true;
					case 3://Exit
						object.texture = reference.assetManager.get("menu/close1.png", Texture.class);
						return true;
					}
					
				}
			}
		}
		return false;
	}
	public boolean scrolled (int amount)
	{
		return false;
	}
}
