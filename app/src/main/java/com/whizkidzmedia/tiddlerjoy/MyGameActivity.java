package com.whizkidzmedia.tiddlerjoy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import org.andengine.engine.camera.Camera;
//import org.andengine.engine.options.EngineOptions;
//import org.andengine.engine.options.ScreenOrientation;
//import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.scene.background.Background;
//import org.andengine.entity.sprite.AnimatedSprite;
//import org.andengine.opengl.texture.TextureOptions;
//import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
//import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
//import org.andengine.opengl.texture.region.TiledTextureRegion;
//import org.andengine.ui.activity.SimpleBaseGameActivity;
//import org.andengine.util.adt.color.Color;

public class MyGameActivity extends AppCompatActivity {

//    private BitmapTextureAtlas texCat;
//    private TiledTextureRegion regCat;
//    private AnimatedSprite sprCat;
//    private Scene m_Scene;
//    private Camera m_Camera;
//
//    //This represents the sprite sheet(image) rows and columns
////We have 4 Rows and 2 Columns
//    private static int   SPR_COLUMN  = 2;
//    private static int   SPR_ROWS  = 4;
//
//
//    //Set the camera Width and Height
//    private static final int CAMERA_WIDTH = 800;
//    private static final int CAMERA_HEIGHT = 480;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_game);
//    }
//
//    @Override
//    protected void onCreateResources() {
//        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//        texCat = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//        //regCat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texCat, this, "runningcat.png", 0, 0, SPR_COLUMN, SPR_ROWS);
//        texCat.load();
//    }
//
//    @Override
//    protected Scene onCreateScene() {
//        m_Scene = new Scene();
//        m_Scene.setBackground(new Background(Color.BLUE));
//
//        sprCat = new AnimatedSprite(0, 0, regCat, this.getVertexBufferObjectManager());
//        long[] frameDurration = {100, 100, 100, 100,100,100,100,100};
//        sprCat.animate(frameDurration);
//        m_Scene.attachChild(sprCat);
//
//        //sprCat.animate(100);
//        return m_Scene;
//
//    }
//
//    @Override
//    public EngineOptions onCreateEngineOptions() {
//        m_Camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
//        EngineOptions en = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
//                CAMERA_WIDTH, CAMERA_HEIGHT), m_Camera);
//        return en;
//    }
}
