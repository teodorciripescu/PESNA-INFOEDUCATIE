package com.pesna.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.pesna.Main;
import com.pesna.dialog.DialogBuilder;
import com.pesna.entities.EnemyObject;
import com.pesna.objects.ScreenObject;
import com.pesna.objects.SimpleLabel;
import com.pesna.objects.SimpleTimer;
import java.io.IOException;
import com.pesna.talentInterface.TalentsTree;

public class Player implements ScreenObject {

	public SimpleTimer blockTimer ,empowerTimer , slowTimer , ultimateTimer,autoattackTimer,regenTimer;

	public int x,y,range,SkillPoints = 10;
	private float delta , counter = 0.0f;
	private SimpleLabel hpLabel , lvlLabel;
	private boolean flip , attack_ready = true;
	public Animation animation;
	public TalentsTree talentsTree;
	public boolean doRegen = true , doBlock, isAttacked,doEmpower,doSlow , doUltimate ,isEnemyClose = false,leveledUP = false;
	private Main reference;

	public int Level = 1 , Experience =  0 , LevelCapExperience = 1800;

	public float ARMOR = 0 , DAMAGE = 100 , SPEED = 300 , ATTACK_SPEED = 0.75f , HP , maxHP = 150;

	protected final float gravity = 0.9f;
	protected float vspeed = 0;

	private DialogBuilder dialogBuilder;
	public Player( Main _reference ) throws IOException {
		blockTimer = new SimpleTimer(5.0f);
		empowerTimer = new SimpleTimer(30.0f);
		slowTimer = new SimpleTimer(12.0f);
		ultimateTimer = new SimpleTimer(60.0f);
		autoattackTimer = new SimpleTimer((ATTACK_SPEED));
		regenTimer = new SimpleTimer(1.0f);

		reference = _reference;

		x = 0;
		y = 0;
		delta = 0;
		flip = false;
		HP = 128;
		range = 800;

		talentsTree = new TalentsTree(1,_reference);
		dialogBuilder = new DialogBuilder(reference);
		dialogBuilder.SetDialog("Pesna" ,"Chapo");
		SetStats();
		hpLabel = new SimpleLabel(_reference);
		hpLabel.SetColor(Color.YELLOW);
		lvlLabel = new SimpleLabel(_reference);
		lvlLabel.SetColor(Color.YELLOW);

	}

	@Override
	public void draw( Main _reference )
	{
    SpriteBatch batch = _reference.batch;
    drawPlayerObjects();
		delta+= Gdx.graphics.getDeltaTime();
		TextureRegion keyFrame = animation.getKeyFrame(delta,true);
		batch.begin();

		if ( flip )
			batch.draw( keyFrame, x+keyFrame.getRegionWidth()/2, y, -keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
		else
			batch.draw( keyFrame, x-keyFrame.getRegionWidth()/2, y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
		//batch.draw( region, x, y);
		batch.end();
	}

	@Override
	public void update( Main _reference )
	{


		//Control
    CheckDead();
    Regen();
		control();
		Timer(ATTACK_SPEED);
		useBlock();
		useEmpower();
		useSlow();
		ultimate();
		levelManagement();
		moveCamera( _reference );

	}

	private boolean auxiliat = true;

	private void control()
	{
		animation = reference.gameRegistry.animationManager.hstay;
		dialogBuilder.Start();
		if ( Gdx.input.isKeyPressed(Keys.A))
		{
			x -= SPEED * Gdx.graphics.getDeltaTime();
			flip = true;
			animation = reference.gameRegistry.animationManager.hwalk;
		}

		else if ( Gdx.input.isKeyPressed(Keys.D))
		{
			x += SPEED * Gdx.graphics.getDeltaTime();
			flip = false;
			animation = reference.gameRegistry.animationManager.hwalk;
		}

		if ( Gdx.input.isKeyJustPressed(Keys.SPACE) && y == 0 )
		{
			vspeed = +15;
		}

		if(Gdx.input.isKeyJustPressed(Keys.P))
        {
           if(!talentsTree.IS_Added)
           {
              reference.screenManager.gameScreen.ObjectForceAdd(talentsTree);
              talentsTree.IS_Added = true;
           }

           if(!talentsTree.StartDraw)
           {
              talentsTree.StartDraw = true;
           }
        }
        if(Gdx.input.isKeyPressed(Keys.Q))
				{
					if(autoattackTimer.TimeElapsed())
					{
            auxiliat = true;
					}
					if(auxiliat) {
						animation = reference.gameRegistry.animationManager.hattack;
						autoattackTimer.setNewTime(animation.getAnimationDuration() +1.1f);
						if(autoattackTimer.TimeElapsed())
						{
							for(EnemyObject enemy : reference.screenManager.gameScreen.GetLevelEnemy())
							{
								if(enemy.IS_IN_RANGE(this, 30))
								{
									enemy.TakeDamage(DAMAGE);
									if (ultimateUsed) {
										DAMAGE -= 100;
										ultimateUsed = false;
									}
								}
							}
							auxiliat = false;
							autoattackTimer.setNewTime(ATTACK_SPEED);
						}
					}
				}

		if ( y+vspeed >= 0 )
		{
			y+=vspeed;
			vspeed = vspeed-gravity;
		}
		else
		{
			y=0;
		}
	}

	private void moveCamera( Main _reference )
	{
		int height = _reference.gameRegistry.levelManager.platform.getHeight();

		Vector3 pos = _reference.camera.position;
		_reference.camera.translate( x-pos.x, -pos.y+(-height+Gdx.graphics.getHeight()/2) );
		_reference.camera.update();
		_reference.batch.setProjectionMatrix(_reference.camera.combined);
		_reference.shapeRenderer.setProjectionMatrix(_reference.camera.combined);
	}

	public void TakeDamage( int damage )
	{
		if(HP >  0) {
			HP -= damage;
		}
		if(HP <= 0)
		{
			HP = 0;
		}
	}

	private void CheckDead()
	{
		if(HP <= 0)
		{
      System.exit(0);
		}
	}

	public void DealDamage(EnemyObject NPC)
	{
	  NPC.TakeDamage(DAMAGE);
	}

	private void Timer(float cap)
	{
		counter += Gdx.graphics.getDeltaTime();
		if(counter >= cap)
		{
			attack_ready = true;
		}
	}

  private void useBlock()
	{
		if(doBlock && blockTimer.TimeElapsed() && isAttacked)
		{
			HP += 30;
		}
	}

	private boolean usable = true;
	private boolean isON = false, aux = false;

	private void useEmpower()
	{
		if(doEmpower && Gdx.input.isKeyJustPressed(Keys.NUM_1) &&usable)
		{
			ATTACK_SPEED -= 1.0f;
			DAMAGE += 20;
			isON = true;
			usable = false;
		}
		if(isON)
		{
			if(isAttacked)
			{
				TakeDamage(10);
			}
			if(empowerTimer.TimeElapsed())
			{
				DAMAGE -= 20;
				ATTACK_SPEED += 1.0f;
				empowerTimer.setNewTime(30);
				aux = true;
				isON = false;
			}
		}
		if(aux)
		{
			if(empowerTimer.TimeElapsed())
			{
				empowerTimer.setNewTime(10);
				usable = true;
				aux = false;
			}
		}
	}

	private boolean _usable = true;
	public boolean used;

	private void useSlow()
	{
		if(_usable)
		{
			if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
				used = true;
				_usable = false;
			}
		}
		else
		{

			if(slowTimer.TimeElapsed())
			{
				_usable = true;
			}
		}
	}

	private boolean ultimateReady = true;
	public boolean ultimateUsed = false;

	private void ultimate()
	{
		if(doUltimate && Gdx.input.isKeyJustPressed(Keys.NUM_2) && ultimateReady)
		{
			DAMAGE += 100;
			ultimateUsed = true;
			ultimateReady = false;
		}
		if(!ultimateReady) {
			if (ultimateTimer.TimeElapsed())
			{
				ultimateReady = true;
			}
		}
	}

	private int waveIndex = 1;
	private void levelManagement()
	{
    if(Level <= 10) {
    	if(Level > 1) {
        LevelCapExperience = waveIndex * 150 + 950;
      }
      else {
        LevelCapExperience = 1800;
      }
    }
		if(Experience >= LevelCapExperience)
		{
      Experience = 0;
      leveledUP = true;
      if(Level % 2 != 0) {
      if(Level == 9)
      {
        SkillPoints+= 2;
      }
      else
      {
        SkillPoints++;
      }
      }
			waveIndex++;			Level++;
      SetStats();
		}
		if(Level == 10)
		{
			Level = Integer.MAX_VALUE;
			Level = Integer.MAX_VALUE;
			Experience = 0;
		}
	}

	private float regenRatio = 6;
	private void Regen()
  {
    if(doRegen)
    {
      regenRatio = 8;
    }
    if(HP > maxHP)
    {
      HP =maxHP;
    }
    if(regenTimer.TimeElapsed() && !isEnemyClose)
    {
      if(HP < maxHP) {
        HP += (regenRatio / 100) * maxHP;
        System.out.println(String.valueOf(maxHP));
      }
    }
  }

  private Texture charTexture = new Texture(Gdx.files.internal("ItemsSprites/cf.png"));
	private BitmapFont experienceFont = new BitmapFont();
	private void drawPlayerObjects()
  {
    //draw experience bar
    int procentage = (Experience * 100) / LevelCapExperience;



    reference.shapeRenderer.begin(ShapeType.Line);
    reference.shapeRenderer.setColor(Color.GOLD);
    reference.shapeRenderer.rect(reference.camera.position.x - 200, reference.camera.position.y - 370,400,21);
    reference.shapeRenderer.end();

    reference.shapeRenderer.begin(ShapeType.Filled);
    reference.shapeRenderer.setColor(Color.ORANGE);
    reference.shapeRenderer.rect(reference.camera.position.x - 200, reference.camera.position.y - 370,
        procentage *4,20);
    reference.shapeRenderer.end();

    reference.batch.begin();
    experienceFont.draw(reference.batch,
        procentage + "%",reference.camera.position.x - 5,reference.camera.position.y - 355);
    reference.batch.end();

    //draw char Icon and HealthBar
    reference.batch.begin();
    reference.batch.draw(charTexture,reference.camera.position.x - 680 , reference.camera.position.y + 190, 175,175);
    reference.batch.end();

    reference.shapeRenderer.begin(ShapeType.Filled);
    reference.shapeRenderer.setColor(Color.FIREBRICK);
    reference.shapeRenderer.rect(reference.camera.position.x - 656, reference.camera.position.y + 250,(HP * 130) / maxHP,20);
    reference.shapeRenderer.end();
    hpLabel.SetPosition(reference.camera.position.x - 610 ,reference.camera.position.y + 265 );
    hpLabel.Draw(String.valueOf(HP));
    lvlLabel.SetPosition(reference.camera.position.x - 595 ,reference.camera.position.y + 225);
    lvlLabel.Draw(String.valueOf(Level));
  }

  private void SetStats()
  {
    switch (Level)
    {
      case 1:
         maxHP =150;
        DAMAGE = 100;
        break;
      case 2:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 3:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 4:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 5:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 6:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 7:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 8:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 9:
        maxHP += 40;
        DAMAGE += 15;
        break;
      case 10:
        maxHP += 40;
        DAMAGE += 15;
        break;
    }
      HP = maxHP;
  }

}