import java.util.LinkedList;
import java.util.Random;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.math.Vector2;

 
public class Cube extends PhysicsBox implements DrawableObject {
	
	Vector2 pos;
	
	boolean jump = false; 
	boolean specialJump = false; 
	boolean isTouching = true;
	boolean isHurt = false; 
	boolean cubeDead = false; 
	
	float speed = 40f;
	float impulse; 
	
	int size; 
	int particleamount = 50;
	double angle = Math.PI/4; 
	Vector2 specialImpulse = new Vector2((float)(100*Math.cos(angle)), (float)(100*Math.sin(angle)));
	
	
	long creationTime; 
	
	BitmapImage cubeSkin; 
	LinkedList<Particle> particles;
	
	public Cube(Vector2 position, int size, BitmapImage skin){
		
		super("Cube", position, size, size, 15f, 0, 0);
		
		this.size = size;
		this.cubeSkin = skin; 
		this.getBody().setFixedRotation(true); 
		this.setCollisionGroup(-1); 
		this.getBody().getFixtureList().get(0).setRestitution(0);
		impulse = this.getBodyMass()*15; 
		particles = new LinkedList<Particle>(); 
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == PhysicsStaticBox.class){
			isTouching = true; 
		}
		if(theOtherObject.getClass() == Obstacle.class){
			isHurt = true; 
			cubeDead = true; 
		}
		
		System.out.println("Collided with " + theOtherObject);
	}
	
	public void update(){
		
		//linear x movement of the cube 
		this.applyBodyForceToCenter(speed, 0, true); 
		this.setBodyLinearDamping(0.4f); 
		
		if(jump && isTouching){
			jump = false; 
			isTouching = false; 
			pos = this.getBodyWorldCenter(); 
			this.applyBodyLinearImpulse(new Vector2(0, impulse), pos, true); 
		}
		
		if(specialJump && isTouching){
			specialJump = false; 
			isTouching = false; 
			pos = this.getBodyWorldCenter(); 
			this.applyBodyLinearImpulse(specialImpulse, pos, true); 
			Logger.log("jumped in a special way"); 
		}
		//destroys the particles after a while
		if(System.nanoTime() - creationTime > 1000000000){
			
			while(!particles.isEmpty()){
				Particle p = particles.poll();
				p.destroy(); 
			}
		}
	}
	
	public void draw(GdxGraphics g) {
		
		update(); 
		Vector2 centerPos = this.getBodyWorldCenter(); 
			g.drawTransformedPicture(centerPos.x, centerPos.y, 0, size/2, size/2, cubeSkin);
		
		for (Particle particle : particles) {
			particle.draw(g); 
		}
	}

	public void emitParticle(){
		
			
			int impulsIntensity = 10;  
			int angle = 0; 
			long seed = (long)(Math.random() * 10000);
			
			Vector2 impulse = new Vector2(0, 0); 
			Random r = new Random(seed); 
			
			isHurt = false; 
			creationTime = System.nanoTime();
			
			for(int i = 0; i < particleamount; i++){
				int size = (int)(r.nextDouble()*20) + 5; 
				Particle aParticle = new Particle("Particle" , Cube.this.getBodyWorldCenter(), size, (long)(Math.random()*10000));
				aParticle.setCollisionGroup(-1);
				particles.add(aParticle); 
				Vector2 pos = this.getBodyWorldCenter();
				impulse.x = (float) Math.sin(angle)*impulsIntensity; 
				impulse.y = (float) Math.cos(angle)*impulsIntensity; 
				aParticle.applyBodyLinearImpulse(impulse, pos, true); 
				angle += 180/particleamount;
		}
}
}

