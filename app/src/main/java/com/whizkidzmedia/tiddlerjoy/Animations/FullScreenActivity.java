package com.whizkidzmedia.tiddlerjoy.Animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.whizkidzmedia.tiddlerjoy.R;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import java.util.ArrayList;

public class FullScreenActivity extends BaseGameActivity {

    Camera mCamera;
    private int CAMERA_WIDTH = 800, CAMERA_HEIGHT = 480;
    private BitmapTextureAtlas backgroundTexture, train_front_texture,
            train_mid_texture, train_back_texture, backgroundTextureSec;
    private TextureRegion backgroundTextureRegion, train_front_textureRegion,
            train_mid_textureRegion, train_back_textureRegion,
            backgroundTextureRegionSec;

    private TextureRegion characterTextureRegion[];


    private BitmapTextureAtlas trackTexture;
    private TextureRegion trackTextureRegion;
    private Button pausebuton;
    private int butoncount=1;
    private ArrayList<Sprite> arrayList = new ArrayList<>();
    public static int trainLength;
    public static boolean ismove =  true;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(pSavedInstanceState);
        initalizeid();
    }

    public void initalizeid(){
        pausebuton=(Button)findViewById(R.id.pausebuton);
        pausebuton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("<<action onclick");
                if(butoncount==1)
                {
                    ismove = false;
                    butoncount++;

                    pausebuton.setBackgroundResource(R.drawable.playchange);
                    physicsHandler.setVelocityX(0);

                }
                else if(butoncount==2)
                {
                    ismove = true;
                    butoncount--;
                    pausebuton.setBackgroundResource(R.drawable.pausechange);
                    physicsHandler.setVelocityX(Constants.VELOCITY_X);
                }
            }
        });

    }


    @Override
    public Engine onLoadEngine() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        int mHeight = dm.heightPixels;
        if (mWidth < 850) {
            mCamera = new Camera(0, 0, mWidth / dm.density, mHeight
                    / dm.density);
        }
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        if (mWidth < 850) {
            RatioResolutionPolicy ratioPolicy = new RatioResolutionPolicy(
                    mWidth, mHeight);
            final Engine engine = new Engine(new EngineOptions(true,
                    EngineOptions.ScreenOrientation.LANDSCAPE, ratioPolicy, mCamera)
                    .setNeedsMusic(true).setNeedsSound(true));

            return engine;
        } else {
            RatioResolutionPolicy ratioPolicy = new RatioResolutionPolicy(
                    mWidth, mHeight);

            final Engine engine = new Engine(new EngineOptions(true,
                    EngineOptions.ScreenOrientation.LANDSCAPE, ratioPolicy, mCamera)
                    .setNeedsMusic(true).setNeedsSound(true));

            return engine;
        }

    }

    protected void onSetContentView() {
        // if(android.engine.Config.getFTYPE().equals("1")){
        final RelativeLayout relativeLayout = new RelativeLayout(this);
        final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.mRenderSurfaceView = new org.anddev.andengine.opengl.view.RenderSurfaceView(this);
        this.mRenderSurfaceView.setRenderer(this.mEngine);

        final LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());

        ((android.widget.RelativeLayout.LayoutParams) surfaceViewLayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT);
// ADD MY NEW VIEW ABOVE
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = vi.inflate(R.layout.game, null); //THIS IS MY CUSTOM VIEW


        vv.bringToFront();

// ADD THE VIEWS TO THE LAYOUT
        relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams); //ANDENGINE VIEW
        relativeLayout.addView(vv, createAdViewLayoutParams());  //MYVIEW
        this.setContentView(relativeLayout, relativeLayoutLayoutParams);
        // FIND THE VIEWS AND WORK WITH THEM


// }
    }

    private LayoutParams createAdViewLayoutParams() {
        final LayoutParams adViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return adViewLayoutParams;
    }

    @Override
    public void onLoadResources() {
        backgroundTexture = new BitmapTextureAtlas(1024, 512,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(backgroundTexture, this, "gfx/bg.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(backgroundTexture);

        train_front_texture = new BitmapTextureAtlas(512, 512,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        train_front_textureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(train_front_texture, this,
                        "gfx/train_front.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(train_front_texture);

        train_mid_texture = new BitmapTextureAtlas(512, 512,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        train_mid_textureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(train_mid_texture, this, "gfx/train_mid.png",
                        0, 0);
        this.mEngine.getTextureManager().loadTexture(train_mid_texture);

        train_back_texture = new BitmapTextureAtlas(512, 256,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        train_back_textureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(train_back_texture, this,
                        "gfx/train_back.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(train_back_texture);

        backgroundTextureSec = new BitmapTextureAtlas(1024, 128,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        backgroundTextureRegionSec = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(backgroundTextureSec, this, "gfx/bg_2.png", 0,
                        0);
        this.mEngine.getTextureManager().loadTexture(backgroundTextureSec);

        trackTexture = new BitmapTextureAtlas(1024, 32,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        trackTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(trackTexture, this, "gfx/track.png", 0, 0);
        this.mEngine.getTextureManager().loadTexture(trackTexture);


        BitmapTextureAtlas characterTexture[] = new BitmapTextureAtlas[6];

        characterTextureRegion =  new TextureRegion[characterTexture.length];

        for(int i=0;i<characterTexture.length;i++){

            characterTexture[i] =  new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            characterTextureRegion[i] = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(characterTexture[i], this, "gfx/characters/"+(i+1)+".png", 0,
                            0);

            this.mEngine.getTextureManager().loadTexture(characterTexture[i]);

        }













    }

    public TextureRegion frogTextureRegion[];

	/*public void initializeFrogSprites(){

		frogTextureRegion = new TextureRegion[17];

		for(int i=0;i<17;i++){

			BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(,,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			frogTextureRegion[i] = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(bitmapTextureAtlas, this, "gfx/characters/frog"+(i+1)+".png", 0,
							0);

			this.mEngine.getTextureManager().loadTexture(bitmapTextureAtlas);




		}





	}*/


    public void generateCharacterSprites(Sprite train_frontSprite){

        float cords[] =  train_frontSprite.convertSceneToLocalCoordinates(train_frontSprite.getX()+train_frontSprite.getWidth()/2+30, train_frontSprite.getY()+characterTextureRegion[0].getHeight()/2-21);

        CharacterSprite elephantSprite = new CharacterSprite(cords[0],cords[1], characterTextureRegion[0]);

        train_frontSprite.attachChild(elephantSprite);

        for(int i=0;i<5;i++){
            float yyy = 0;
            //=  arrayList.get(i).getY()-characterTextureRegion[i+1].getHeight();

            if(i==0)
                yyy = -characterTextureRegion[i+1].getHeight()+54;
            else if (i==1)
                yyy = -characterTextureRegion[i+1].getHeight()+80;
            else if (i==2)
                yyy = -characterTextureRegion[i+1].getHeight()+84;
            else if (i==3)
                yyy = -characterTextureRegion[i+1].getHeight()+60;
            else if (i==4)
                yyy = -characterTextureRegion[i+1].getHeight()+52;
            float cords1[] = {arrayList.get(i).getWidth()/2-characterTextureRegion[i+1].getWidth()/2,yyy};

            //float cords1[] = arrayList.get(i).convertSceneToLocalCoordinates(arrayList.get(i).getX()+arrayList.get(i).getWidth()/2-characterTextureRegion[i+1].getWidth()/2, yyy);
            //float cords1[] = new float[]{arrayList.get(i).getX()+arrayList.get(i).getWidth()/2-characterTextureRegion[i+1].getWidth()/2, yyy};


            CharacterSprite characterSprite = new CharacterSprite(cords1[0],cords1[1], characterTextureRegion[i+1]);
            arrayList.get(i).attachChild(characterSprite);

        }



    }

    @Override
    public Scene onLoadScene() {
        Scene scene = new Scene();
        scene.attachChild(new Sprite(0, 0, backgroundTextureRegion), 0);

        Sprite backgroundSpriteSec = new Sprite(0, CAMERA_HEIGHT
                - backgroundTextureRegionSec.getHeight(),
                backgroundTextureRegionSec);
        scene.attachChild(backgroundSpriteSec, 1);

        Sprite trackSprite = new Sprite(0, backgroundSpriteSec.getY()
                + trackTextureRegion.getHeight(), trackTextureRegion);
        scene.attachChild(trackSprite, 1);

		/*Sprite train_frontSprite = new Sprite(600, trackSprite.getY()
				- train_front_textureRegion.getHeight()+10,
				train_front_textureRegion);
		scene.attachChild(train_frontSprite, 2);*/

		/*System.out.println("texture_heighttt==="
				+ train_front_texture.getHeight()
				+ "textureRegion___heighttt=="
				+ train_front_textureRegion.getHeight() + "sprite__heightt=="
				+ train_frontSprite.getHeight());*/

        Sprite train_frontSprite = generateTrainSprites(scene, trackSprite);

        generateCharacterSprites(train_frontSprite);

	/*	Sprite train_midSprite = new Sprite(train_frontSprite.getWidth(),
				CAMERA_HEIGHT - train_front_textureRegion.getHeight(),
				train_mid_textureRegion);

	float train_midSpriteCords[] = train_frontSprite.convertSceneToLocalCoordinates(train_frontSprite.getX()+train_frontSprite.getWidth()-13, trackSprite.getY()-train_mid_textureRegion.getHeight()+10);
    train_midSprite.setPosition(train_midSpriteCords[0], train_midSpriteCords[1]);

	 train_midSprite.setPosition(307, 145);
		// train_frontSprite.convertSceneToLocalCoordinates(pX, pY);

		// train_midSprite.setPosition(307, 145);
		train_frontSprite.attachChild(train_midSprite);*/


		/* PhysicsHandler physicsHandler = new PhysicsHandler(train_frontSprite);


		  train_frontSprite.registerUpdateHandler(physicsHandler);

		  physicsHandler.setVelocityX(-20);*/





        return scene;
    }


    public Sprite generateTrainSprites(Scene scene , Sprite trackSprite){

        CustomSprite train_frontSprite = new CustomSprite(CAMERA_WIDTH+10, trackSprite.getY()
                - train_front_textureRegion.getHeight()+10,
                train_front_textureRegion,-1,scene);
        trainLength+=train_frontSprite.getWidth();


        scene.attachChild(train_frontSprite, 2);


        float y_cord = trackSprite.getY()-train_mid_textureRegion.getHeight()+10;
        float train_midSpriteCords[] = null;


        for(int i=0;i<Constants.TRAIN_BOXES;i++){
            if(i==0)
                train_midSpriteCords = train_frontSprite.convertSceneToLocalCoordinates(train_frontSprite.getX()+train_frontSprite.getWidth()-13, y_cord);
            else{
                //train_midSpriteCords = train_frontSprite.convertSceneToLocalCoordinates(arrayList.get(i-1).getX()+arrayList.get(i-1).getWidth()+15, y_cord);

                train_midSpriteCords =new float[]{arrayList.get(i-1).getX()+arrayList.get(i-1).getWidth()-15, y_cord};

            }

            CustomSprite train_midSprite = new CustomSprite(train_midSpriteCords[0],train_midSpriteCords[1],train_mid_textureRegion,i,scene);
            arrayList.add(train_midSprite);
            trainLength+=train_midSprite.getWidth();
            train_frontSprite.attachChild(train_midSprite);

            System.out.println("trainnnn_xxxxx");






        }

        CustomSprite train_backSprite = new CustomSprite(arrayList.get(arrayList.size()-1).getX()+arrayList.get(arrayList.size()-1).getWidth()-18, trackSprite.getY()-train_back_textureRegion.getHeight()+20, train_back_textureRegion,arrayList.size(),scene);
        train_backSprite.tag = 1;
        trainLength+=train_backSprite.getWidth();
        train_frontSprite.attachChild(train_backSprite);
        System.out.println("backkkk_____xxxxxxxxxxx==="+train_backSprite.getX());


        physicsHandler = new PhysicsHandler(train_frontSprite);
        train_frontSprite.registerUpdateHandler(physicsHandler);
        physicsHandler.setVelocityX(Constants.VELOCITY_X);



        return train_frontSprite;


    }

    private PhysicsHandler physicsHandler;

    @Override
    public void onLoadComplete() {
        // TODO Auto-generated method stub

    }

}
