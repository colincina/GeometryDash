import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

 
public class Cube extends PhysicsBox implements DrawableObject {
	
	Vector2 pos;
	
	boolean jump = false; 
	boolean specialJump = false; 
	boolean isTouching = false;
	boolean isHurt = false; 
	boolean cubeDead = false; 
	boolean stopInc = false; 
	boolean checkInAirRotation = false; 
	private boolean firstTouchdown = false; 
	
	private int particleAmount = Gsing.get().cParticleAmount; 
	private int drawAngle;
	private int currentFrame = 0;
	private int currentRow = 0;
	private int nFrames = 9; 
	private int dir = 1; 
	private int i = 0; 
	int cubeDamageCnt = 0; 
	
	float dt = 0; 
	float jumpCountDown = 0.2f; 
	float frameTime = 0.02f; 
	
	double angle = Math.PI/4; 
	Vector2 specialImpulse = new Vector2((float)(100*Math.cos(angle)), (float)(100*Math.sin(angle)));
	long creationTime; 
	Spritesheet cubeBirth = new Spritesheet("data/Spritesheets/cubeBirthSpriteSheet.png", 170, 170); 
	
	SoundSample birth = new SoundSample("data/sounds/birth.mp3");
	SoundSample death = new SoundSample("data/sounds/death.mp3"); 
	SoundSample impact = new SoundSample("data/sounds/impact.mp3");
	
	LinkedList<Particle> particles;
	
	public Cube(Vector2 position){
		
		super("Cube", position, Gsing.get().cSize, Gsing.get().cSize, 3f, 0, 0);
		this.setCollisionGroup(-1); 
		this.drawAngle = 0;
		this.getBody().setFixedRotation(true); 
		Gsing.get().cImpulse = this.getBodyMass() * 15; 
		particles = new LinkedList<Particle>(); 
		impact.setVolume(0.15f); 
		death.setVolume(0.15f); 
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == Platform.class || theOtherObject.getClass() == PhysicsStaticBox.class){
			impact.play(); 
			isTouching = true; 
			firstTouchdown = true;
		
		}
		if (theOtherObject instanceof Sensor) {
			Sensor sensor = (Sensor) theOtherObject;
			if(!sensor.alreadyCollided)
			cubeDamageCnt++; 
			impact.play(); 
			isHurt = true; 
			sensor.alreadyCollided = true;
		}
		
		if(theOtherObject.getClass() == HoleOfTheDamned.class){
			death.play(); 
			cubeDead = true; 
			Gsing.get().totalDistance = (int)(this.getBodyWorldCenter().x); 
		}
		
//		System.out.println("Collided with " + theOtherObject);
	}
	
	public void updateLogic(){
		
		/*
		 * Still have to ajust this so that the cube is not able to jump anymore exactly when is 
		 * is at the edge of the box 
		 */
		
		/*
		 * Have to cleanup all this logic mess!! ######################################
		 */
		
		System.out.println(drawAngle); 
		if(firstTouchdown){
			//linear x movement of the cube 
			this.applyBodyForceToCenter(Gsing.get().cSpeed, 0, true); 
			this.setBodyLinearDamping(0.3f); 
		}
		
		if(!stopInc){
			this.applyBodyForceToCenter(0, 50*this.getBodyMass(), true);
		}
		
		if(jump && isTouching){
			jump = false; 
			isTouching = false; 
			checkInAirRotation = true; 
			pos = this.getBodyWorldCenter();
			this.applyBodyLinearImpulse(new Vector2(0, Gsing.get().cImpulse), pos, true);
		}
		
		if(specialJump && isTouching){
			angle = Math.random()*Math.PI*2; 
			specialJump = false; 
			isTouching = false; 
			pos = this.getBodyWorldCenter(); 
			this.applyBodyLinearImpulse(specialImpulse, pos, true); 
			Logger.log("jumped in a special way"); 
		}
		
		if(checkInAirRotation){
			setDrawAngle(); 
		}
		checkParticles4Destruction();
	}
	
	public void updateGraphics(GdxGraphics g){
		
		dt += Gdx.graphics.getDeltaTime();

		//Selects the right field of the SpriteSheet 
		if(!stopInc){
			if (dt > frameTime) {
				dt = 0;
				currentFrame = (currentFrame + dir) % nFrames;
				if(currentFrame == 8 && currentRow == 0){
					currentRow = 1; 
					currentFrame = 0; 
				}
				if(currentFrame == 8 && currentRow == 1){
					stopInc = true; 
				}
			}
		}
		
		//If the cube has taken some damage, we display a special SpriteSheet
		if(cubeDamageCnt >= 1 && cubeDamageCnt < 7){
			currentRow = 2; 
			currentFrame = cubeDamageCnt - 1; 
		}
	}
	
	public void draw(GdxGraphics g) {
		
		updateLogic(); 
		updateGraphics(g); 
		Vector2 pos = this.getBodyWorldCenter(); 
		g.draw(cubeBirth.sprites[currentRow][currentFrame],pos.x - 85, pos.y - 85, 85, 85, 170, 170, 1, 1, drawAngle); 
		for (Particle particle : particles) {
			particle.draw(g); 
		}
	}

	public void emitParticle(){
			
			int angle = 0; 
			long seed = (long)(Math.random() * 10000);
			
			Vector2 impulse = new Vector2(0, 0); 
			Random r = new Random(seed); 
			
			isHurt = false; 
			creationTime = System.nanoTime();
			
			for(int i = 0; i < particleAmount; i++){
				int size = (int)(r.nextDouble()*20) + 5; 
				Particle aParticle = new Particle("Particle" , Cube.this.getBodyWorldCenter(), size, (long)(Math.random()*10000));
				aParticle.setCollisionGroup(-1);
				particles.add(aParticle); 
				Vector2 pos = this.getBodyWorldCenter();
				impulse.x = (float) Math.sin(angle)*Gsing.get().pImpulse; 
				impulse.y = (float) Math.cos(angle)*Gsing.get().pImpulse; 
				aParticle.applyBodyLinearImpulse(impulse, pos, true); 
				angle += 180/Gsing.get().cParticleAmount;
		}
	}

	public void checkParticles4Destruction(){
		
	//destroys the particles after a while
			if(System.nanoTime() - creationTime > 3*Math.pow(10, 9)){
				
				while(!particles.isEmpty()){
					Particle p = particles.poll();
					p.destroy(); 
				}
			}
	}

	public void setDrawAngle(){
			
		drawAngle -= 9;
		
		if(drawAngle % 180 == 0){
			checkInAirRotation = false; 
		}
	}
}