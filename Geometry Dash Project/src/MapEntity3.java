import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class MapEntity3 implements DrawableObject{

	//to make the length of the platform random
	Vector2 pos; 
	
	
	PhysicsStaticBox step1; 
	PhysicsStaticBox step2;
	Platform platform; 
	
	HoleOfTheDamned hole1;
	HoleOfTheDamned hole2;
	HoleOfTheDamned hole3;
	
	boolean bigSize = true; 
	
	int platformLength; 
	int stepSize;
	int holeWidth; 
	
	int currentFrame = 0;
	int nFrames = 8; 
	int dir = 1; 
	
	float dt = 0;
	float frameTime = 0.09f; 
	
	Spritesheet bStep = new Spritesheet("data/Spritesheets/bigME3SpriteSheet.png", 300, 300); 
	Spritesheet sStep = new Spritesheet("data/Spritesheets/smallME3SpriteSheet.png", 150, 150); 
	

	Vector<SoundSample> sounds; 
	
	public MapEntity3(int platformLength, Vector2 firstStepPosition) {
		
		this.platformLength = platformLength; 
		this.stepSize = Gsing.get().me3StepSize; 
		this.holeWidth = Gsing.get().holeWidthme3; 
		this.pos = firstStepPosition; 
//		sounds.get(1).loop(); 
//		sounds.get(1).setVolume(1);
//		sounds.get(1).setPitch(0.9f); 
		
		if(!Gsing.get().bigSize){
			this.stepSize = stepSize/2;
			holeWidth = Gsing.get().holeWidthme3Big; 
			buildSmallEntity();
			bigSize = false; 
		}
		else{
			bigSize = true;
			holeWidth = Gsing.get().holeWidthme3; 
			buildBigEntity(); 
		}
	}

	public void buildBigEntity(){
		
		hole1 = new HoleOfTheDamned(holeWidth, pos);
		hole1.setSensor(true); 
		hole1.enableCollisionListener();
		pos.x += stepSize/2 + holeWidth/2; 
		pos.y += stepSize/2; 
		
		step1 = new PhysicsStaticBox("Box 1", pos, stepSize, stepSize);
		pos.x += stepSize/2 + holeWidth/2; 
		
		hole2 = new HoleOfTheDamned(holeWidth, pos);
		hole2.setSensor(true); 
		hole2.enableCollisionListener();
		pos.x += stepSize/2 + holeWidth/2; 
		pos.y += stepSize/2; 
		
		step2 = new PhysicsStaticBox("Box 2", pos, stepSize, stepSize);
		pos.x += stepSize/2 + holeWidth/2; 
		
		hole3 = new HoleOfTheDamned(holeWidth, pos);
		hole3.setSensor(true); 
		hole3.enableCollisionListener();
		pos.x += platformLength/2 + holeWidth/2; 
		pos.y += stepSize; 
		
		platform = new Platform( pos, platformLength, stepSize/2 );
	}
	
	public void buildSmallEntity(){
		
		hole1 = new HoleOfTheDamned(holeWidth, pos);
		hole1.setSensor(true); 
		hole1.enableCollisionListener();
		pos.x += stepSize/2 + holeWidth/2; 
		pos.y += stepSize*2; 
		
		step1 = new PhysicsStaticBox("Box 1", pos, stepSize, stepSize);
		pos.x += stepSize/2 + holeWidth/2; 
		
		hole2 = new HoleOfTheDamned(holeWidth, pos);
		hole2.setSensor(true); 
		hole2.enableCollisionListener();
		pos.x += stepSize/2 + holeWidth/2; 
		pos.y += stepSize*2; 
		
		step2 = new PhysicsStaticBox("Box 2", pos, stepSize, stepSize);
		pos.x += stepSize/2 + holeWidth/2; 
		
		hole3 = new HoleOfTheDamned(holeWidth, pos);
		hole3.setSensor(true); 
		hole3.enableCollisionListener();
		pos.x += platformLength/2 + holeWidth/2; 
		pos.y += stepSize*2; 
		
		platform = new Platform(pos, platformLength, stepSize/2);
	}
	
	public void updateGraphics(GdxGraphics g){
		
		dt += Gdx.graphics.getDeltaTime();

		// Do we have to display the next frame
		if (dt > frameTime) {
			dt = 0;
			
			currentFrame = (currentFrame + dir) % nFrames;
			
			if(currentFrame == 7 || currentFrame == 0){
				dir *= -1; 
			}
		}
	}
	
	public void draw(GdxGraphics g) {
		
		updateGraphics(g);
		Vector2 drawStep1 = new Vector2(step1.getBodyWorldCenter());
		Vector2 drawStep2 = new Vector2(step2.getBodyWorldCenter());
		
		if(bigSize){
			g.draw(bStep.sprites[0][currentFrame], drawStep1.x - (stepSize/2 + 25) , drawStep1.y - (stepSize/2 + 25));
			g.draw(bStep.sprites[0][currentFrame], drawStep2.x - (stepSize/2 + 25), drawStep2.y - (stepSize/2 + 25)); 
		}
		
		if(!bigSize){
			g.draw(sStep.sprites[0][currentFrame], drawStep1.x - (stepSize/2 + 12) , drawStep1.y - (stepSize/2 + 12));
			g.draw(sStep.sprites[0][currentFrame], drawStep2.x - (stepSize/2 + 12), drawStep2.y - (stepSize/2 + 12)); 
		}
		
		hole1.draw(g); 
		hole2.draw(g); 
		hole3.draw(g); 
		platform.draw(g); 
	}
}
