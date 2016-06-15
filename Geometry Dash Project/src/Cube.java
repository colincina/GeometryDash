import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

 
public class Cube extends PhysicsBox implements DrawableObject {
	
	Vector2 pos;
	
	boolean jump = false; 
	boolean fly = false; 
	boolean jumpMode = true;
	boolean flyMode = false; 
	boolean specialJump = false; 
	boolean isTouching = false;
	boolean isHurt = false; 
	boolean stopInc = false; 
	boolean cubeDead = false; 
	boolean itsTheEnd = false; 
	
	private boolean checkInAirRotation = false; 
	private boolean firstTouchdown = false; 
	private boolean gameMode = true; 
	
	private int particleAmount = Gsing.get().cParticleAmount; 
	private int drawAngle;
	private int currentFrame = 0;
	private int currentRow = 0;
	private int nFrames = 9; 
	private int dir = 1; 
	private int i = 0; 
	private int angleIndex; 
	
	public int CREATION_RATE = 1;
	public final int MAX_AGE = 20;
	
	int cubeDamageCnt = 0; 
	
	float dt = 0; 
	float frameTime = 0.02f; 
	
	double angle = Math.PI/4; 
	Vector2 specialImpulse = new Vector2((float)(100*Math.cos(angle)), (float)(100*Math.sin(angle)));
	long creationTime; 
	Spritesheet cubeBirth = new Spritesheet("data/Spritesheets/cubeBirthSpriteSheet.png", 170, 170); 
	
	SoundSample birth = new SoundSample("data/sounds/birth.mp3");
	SoundSample death = new SoundSample("data/sounds/death.mp3"); 
	SoundSample impact = new SoundSample("data/sounds/impact.mp3");
	
	LinkedList<Particle> particles;

	Random random; 
	
	public Cube(Vector2 position, Random rand){
		
		super("Cube", position, Gsing.get().cSize, Gsing.get().cSize, 3f, 0, 0);
		this.setCollisionGroup(-1); 
		this.drawAngle = 0;
		this.getBody().setFixedRotation(true);
		this.random = rand; 
		Gsing.get().cImpulse = this.getBodyMass() * 15;
		Gsing.get().cForce = this.getBodyMass() * 500;
		particles = new LinkedList<Particle>(); 
		impact.setVolume(0.15f); 
		death.setVolume(0.15f); 
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == PhysicsStaticBox.class || theOtherObject.getClass() == Platform.class){
			impact.play(); 
			isTouching = true; 
			firstTouchdown = true;
		}
		
		if (theOtherObject instanceof DamageSensor) {
			DamageSensor sensor = (DamageSensor) theOtherObject;
			if(!sensor.alreadyCollided)
			cubeDamageCnt++; 
			impact.play(); 
			isHurt = true; 
			sensor.alreadyCollided = true;
		}
		
		if(theOtherObject.getClass() == HoleOfTheDamned.class || theOtherObject.getClass() == AirTimeObstacle.class){
			death.play(); 
			cubeDead = true; 
			Gsing.get().totalDistance = (int)(this.getBodyWorldCenter().x); 
		}
		
		if(theOtherObject.getClass() == ModeSelector.class){
			jumpMode = !jumpMode;
			flyMode = !flyMode; 
			isTouching = true;
			checkInAirRotation = true; 
		}
		
		if(theOtherObject.getClass() == HorizontalCollisionSensor.class){
			Vector2 pos = this.getBodyWorldCenter(); 
//			this.setBodyLinearVelocity(this.getBodyLinearVelocity().x * 2, 17);
			System.out.println(this.getBodyLinearVelocity().y);
				
				if(this.getBodyLinearVelocity().y < 8){
					this.applyBodyLinearImpulse(new Vector2(0, Gsing.get().cImpulse), pos, true);
				}	
//		
				else{
					this.applyBodyLinearImpulse(new Vector2(0, Gsing.get().cImpulse/3), pos, true); 
				}
			System.out.println("Collided with " + theOtherObject);
		}
		
		if(theOtherObject.getClass() == WinSensor.class){
			gameMode = false; 
			this.setBodyLinearDamping(.7f); 
		}
	}
	public void updateLogic(){
		
		/*
		 * Have to cleanup all this logic mess!! ######################################
		 */
		
		if(gameMode){
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
//				this.setBodyLinearVelocity(this.getBodyLinearVelocity().x, 17); 
				this.applyBodyLinearImpulse(new Vector2(0, Gsing.get().cImpulse), pos, true);
			}
			
			if(flyMode){
				pos = this.getBodyWorldCenter();
				this.applyBodyForceToCenter(0, 35*this.getBodyMass(), true);
				
				if(fly){
					fly = false; 
					this.applyBodyForceToCenter(0, Gsing.get().cForce, true); 
				}
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
			
			if(cubeDamageCnt == 6){
				cubeDead = true; 
			}
			
			//check if the cube has encountered an obstacle
			if(isHurt)
			{
				emitParticle(); 
			}
			
			if(cubeDead){
				ScreenHub.s.transitionTo(2, ScreenManager.TransactionType.SLICE); 
			}
			
		}
		
		else{
			
			if(this.getBodyLinearVelocity().x < .1 && !itsTheEnd){
				if(this.getBodyWorldCenter().y <= Gdx.graphics.getHeight()*.75){
					this.applyBodyForceToCenter(0, 700f, true);
//					this.getBody().setTransform(this.getBodyPosition(), 0);
				}
				else{
					emitParticle(); 
					itsTheEnd = true; 
				}
			}
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
		
		if(!itsTheEnd){
			g.draw(cubeBirth.sprites[currentRow][currentFrame],pos.x - 85, pos.y - 85, 85, 85, 170, 170, 1, 1, drawAngle); 
		}
		
		for (Particle particle : particles) {
			particle.draw(g); 
		}
	}

	public void emitParticle(){
			
			int angle = 0; 
			isHurt = false; 
			creationTime = System.nanoTime();
			
			for(int i = 0; i < particleAmount; i++){
				int size = (int)(random.nextDouble()*20) + 5; 
				Particle aParticle = new Particle(this.getBodyWorldCenter(), size, angle, random);
				particles.add(aParticle); 
				angle += 180/Gsing.get().cParticleAmount;
		}
	}

	public void createTrailParticles() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Vector2 position = this.getBodyWorldCenter(); 
			position.x -= 40; 
			position.y -= 40; 
			TrailParticle c = new TrailParticle(position, 10, MAX_AGE + random.nextInt(MAX_AGE / 2), random);
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
		int chance = (int)(Math.random()*2); 	
		
		switch (chance) {
		case 0:
			angleIndex = 90;
			break;
			
		case 1:
			angleIndex = 180; 
			break;
	
		default:
			break;
		}
		
		drawAngle -= 9;
		
		if(drawAngle % angleIndex == 0){
			checkInAirRotation = false; 
		}
	}
}