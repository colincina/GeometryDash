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
	boolean isTouching = true;
	boolean isHurt = false; 
	boolean cubeDead = false; 
	boolean stopInc = false; 
	boolean checkJump = false; 
	private boolean firstTouchdown = false; 
	
	private int cubeSize = Gsing.get().cSize; 
	private int particleAmount = Gsing.get().cParticleAmount; 
	private int drawAngle;
	private int currentFrame = 0;
	private int currentRow = 0;
	private int nFrames = 9; 
	private int dir = 1; 
	private int cubeDamageCnt = 0; 
	
	float dt = 0; 
	float interval = 0; 
	float jumpCountDown = 0.2f; 
	float frameTime = 0.02f; 
	float torque;
	
	double angle = Math.PI/4; 
	Vector2 specialImpulse = new Vector2((float)(100*Math.cos(angle)), (float)(100*Math.sin(angle)));
	long creationTime; 
	Spritesheet birth; 
	
	/*
	 *  index 1 = birth, index 2 = death 
	 *  Have to fix this to make it more readable. -> hashmaps + enum maybe
	 */
	Vector<SoundSample> sounds; 
	
	LinkedList<Particle> particles;
	
	public Cube(Vector2 position, Spritesheet spriteSkin, Vector<SoundSample> pSounds){
		
		/*
		 * Why is it impossible to use the instance field cubeSize?
		 */
		super("Cube", position, Gsing.get().cSize, Gsing.get().cSize, 3f, 0, 0);
		this.sounds = pSounds;
		this.birth = spriteSkin; 
		this.getBody().setFixedRotation(true); 
		this.setCollisionGroup(-1); 
		this.drawAngle = 0;
		Gsing.get().cImpulse = this.getBodyMass()* 15; 
		torque = this.getBodyMass() * 15; 
		particles = new LinkedList<Particle>(); 
		sounds.get(0).play(); 
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == Platform.class || theOtherObject.getClass() == PhysicsStaticBox.class){
			sounds.get(1).play();
			isTouching = true; 
			firstTouchdown = true; 
		
		}
		if(theOtherObject.getClass() == VarObstacle.class){
			cubeDamageCnt++; 
			isHurt = true; 
		}
		
//		System.out.println("Collided with " + theOtherObject);
	}
	
	public void updateLogic(){
		
		/*
		 * Still have to ajust this so that the cube is not able to jump anymore exactly when is 
		 * is at the edge of the box 
		 */
		jumpCountDown = this.getBodyLinearVelocity().x * 10 / cubeSize; 
		
		/*
		 * Have to cleanup all this logic mess!! ######################################
		 */
		if(checkJump){
			checkJumpingStatus(); 
			System.out.println(interval);
		}
		
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
		
		if(!isTouching){
			checkJump = false; 
			drawAngle -=4; 
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
		if(cubeDamageCnt >= 1){
			currentRow = 2; 
			currentFrame = cubeDamageCnt - 1; 
		}
	}
	
	public void draw(GdxGraphics g) {
		
		updateLogic(); 
		updateGraphics(g); 

		Vector2 pos = this.getBodyWorldCenter(); 
		g.draw(birth.sprites[currentRow][currentFrame], pos.x - (cubeSize/2 + 10), pos.y - (cubeSize/2 + 10));
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
			if(System.nanoTime() - creationTime > Long.MAX_VALUE/2){
				
				while(!particles.isEmpty()){
					Particle p = particles.poll();
					p.destroy(); 
				}
			}
	}
	
	public void checkJumpingStatus(){
		interval += Gdx.graphics.getDeltaTime(); 
		if(interval >= jumpCountDown){
			interval = 0; 
			isTouching = false; 
		}
	}
	
}

