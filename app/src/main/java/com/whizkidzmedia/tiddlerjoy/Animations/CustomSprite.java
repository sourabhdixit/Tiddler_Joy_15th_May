package com.whizkidzmedia.tiddlerjoy.Animations;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;



public class CustomSprite extends Sprite {
	
	public int tag;
	float xxx;

	public CustomSprite(float pX, float pY, TextureRegion pTextureRegion,int tag,Scene scene) {
		super(pX, pY, pTextureRegion);
		this.tag = tag;
		scene.registerTouchArea(this);
		scene.setTouchAreaBindingEnabled(true);
		if(tag==-1)
			xxx = pX;
		
	}
	
	
	
@Override
public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		float pTouchAreaLocalX, float pTouchAreaLocalY) {
	// TODO Auto-generated method stub
	System.out.println("touchhhhhhhhhhhh___taggg=="+tag);
	touch();
	return super
			.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
}


public void touch(){
	
	if(tag==0){
		
	}else if(tag==1){
		
		
		
	}
	
	
	
}
	
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		
		super.onManagedUpdate(pSecondsElapsed);
		if(tag==0 && FullScreenActivity.ismove){
			System.out.println("xxxxxxxxxxx__top==="+getX()+"lengthhh==="+FullScreenActivity.trainLength);
		}
		if(tag==-1 && getX()<-(FullScreenActivity.trainLength)){
			setPosition(xxx, getY());
			
			System.out.println("xxxxxxxxxxx==="+getX());
			
			
			
			
			
			
		}
		
		
	}

}
