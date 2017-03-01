package com.pesna.init;

import com.badlogic.gdx.graphics.Texture;
import com.pesna.Main;

public class GameRegistry {
	private final Main reference;
	public ItemManager itemManager;
	public AnimationManager animationManager;
	public LevelManager levelManager;
	
	public GameRegistry( Main _reference )
	{
		reference = _reference;
		itemManager = new ItemManager( reference );
		animationManager = new AnimationManager( reference );
		levelManager = new LevelManager( reference );
		reference.assetManager.load("ItemsSprites/rock.png", Texture.class );
		reference.assetManager.load("ItemsSprites/wall.png", Texture.class );
		reference.assetManager.load("ItemsSprites/tree.PNG" , Texture.class);
		reference.assetManager.load("ItemsSprites/grass.png" , Texture.class);

		reference.assetManager.load("ItemsSprites/talentsBack.png" , Texture.class);
		reference.assetManager.load("menu/close0.png" , Texture.class);
		reference.assetManager.load("menu/play0.png" , Texture.class);
		reference.assetManager.load("menu/info0.png" , Texture.class);
		reference.assetManager.load("menu/settings0.png" , Texture.class);
		reference.assetManager.load("menu/close1.png" , Texture.class);
		reference.assetManager.load("menu/play1.png" , Texture.class);
		reference.assetManager.load("menu/info1.png" , Texture.class);
		reference.assetManager.load("menu/settings1.png" , Texture.class);
		reference.assetManager.load("menu/backgr.png" , Texture.class);

	}
	public void onAssetsLoaded( Main reference )
	{
		//Replace those assignTextures with the constructor.. for some cases TODO
		itemManager.assignTextures( reference.assetManager ); // assign textures to each item
		animationManager.assignTextures( reference.assetManager );
		levelManager.assignTextures( reference.assetManager );
		
		//At last
		reference.screenManager.onAssetsLoaded( reference );
	}
	
	
}
