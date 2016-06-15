import java.util.Random;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;


public class TrailParticle extends PhysicsBox implements DrawableObject{

	protected final int maxAge;
	// Resources MUST not be static
	protected BitmapImage img = new BitmapImage("data/images/cubetrailTexture.png");
	protected int age = 0;
	private boolean init = false; 
	Random random; 

	public TrailParticle(Vector2 position, int radius, int maxAge, Random rand) {
		super(null, position, radius, radius, 0.005f, 0, 1f);
		this.maxAge = maxAge;
		this.random = rand; 

		// Particles should not collide together
		Filter filter = new Filter();
		filter.groupIndex = -1;
		f.setFilterData(filter);
		
		// Apply a vertical force with some random horizontal component
		Vector2 force = new Vector2();
		force.x = random.nextFloat() * -0.00095f;
		force.y = random.nextFloat() * 0.0002f * (random.nextBoolean() == true ? -1 : 1);
		this.applyBodyLinearImpulse(force, position, true);
		this.getBody().setGravityScale(0); 
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		img.dispose();
	}

	public boolean shouldbeDestroyed() {
		return age > maxAge ? true : false;
	}

	public void step() {
		age++;
	}

	public void draw(GdxGraphics g) {
		
		final Color col = g.sbGetColor();
		final Vector2 pos = getBodyPosition();
		
		// Make the particle disappear with time
		g.sbSetColor(col.r, col.g, col.b, 1.0f - age / (float) (maxAge + 5));


		// Draw the particle
		g.draw(img.getRegion(), pos.x - img.getImage().getWidth() / 2, pos.y - img.getImage().getHeight() / 2);
		g.sbSetColor(col);
		
	}
}
