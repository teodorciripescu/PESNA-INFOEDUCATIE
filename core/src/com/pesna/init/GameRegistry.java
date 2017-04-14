package com.pesna.init;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.brashmonkey.spriter.SCMLReader;
import com.pesna.Main;
import com.pesna.spriter.LibGdx.LibGdxDrawer;
import com.pesna.spriter.LibGdx.LibGdxLoader;

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

		reference.assetManager.load( "menu.png", Texture.class );
		reference.assetManager.load( "buttons.png", Texture.class );

		reference.assetManager.load ( "items/arrow.png", Texture.class );

		loadSpriterInfo();
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


	public com.brashmonkey.spriter.Data werewolfData;
	public LibGdxLoader werewolfLoader;

	public com.brashmonkey.spriter.Data pesnaIdleData;
	public LibGdxLoader pesnaIdleLoader;

	public com.brashmonkey.spriter.Data pesnaMoveData;
	public LibGdxLoader pesnaMoveLoader;

	private void loadSpriterInfo()
	{
		//"harapalb/2dshit.scml"
		//"werewolf/Mircea.json.scml"
		//"martinica/martinica.scml"
		FileHandle handle = Gdx.files.internal("lucifer/NewProject.autosave.scml");
		werewolfData = new SCMLReader( handle.read() ).getData();
		werewolfLoader = new LibGdxLoader(werewolfData);
		werewolfLoader.load(handle.file());
		reference.werewolfDrawer = new LibGdxDrawer(werewolfLoader, reference.batch, reference.shapeRenderer);

		//handle = Gdx.files.internal( "harapalbb/harapalbb.scml" );
		handle = Gdx.files.internal( "resized-shot/resized-shot.scml" );
		pesnaIdleData = new SCMLReader( handle.read() ).getData();
		pesnaIdleLoader = new LibGdxLoader( pesnaIdleData );
		pesnaIdleLoader.load(handle.file());
		reference.pesnaIdleDrawer = new LibGdxDrawer( pesnaIdleLoader, reference.batch, reference.shapeRenderer );

		//handle = Gdx.files.internal( "harapalbb/harapalbb.scml" );
		handle = Gdx.files.internal( "harapalbb/harapalbb.scml" );
		pesnaMoveData = new SCMLReader( handle.read() ).getData();
		pesnaMoveLoader = new LibGdxLoader( pesnaMoveData );
		pesnaMoveLoader.load(handle.file());
		reference.pesnaMoveDrawer = new LibGdxDrawer( pesnaMoveLoader, reference.batch, reference.shapeRenderer );
	}
}
