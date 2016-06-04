
public class Gsing {

	private static Gsing instance = null; 
	
	//Cube parameters
	int cSize = 75; 
	int cParticleAmount; 
	int pImpulse = 20;
	float cSpeed = 80f; 
	float cImpulse;

	//Hole of the damned
	int holeWidthme1 = 500; 
	int holeWidthme3 = 300;
	int holeWidthme4 = 2000; 
	int holeHeight = 25; 
	
	//Map entity2 parameters
	int me2H  = 500;  
	
	//Map entity3 parameters
	int me3StepSize = 250; 
	
	
	private Gsing(){
		super(); 
	}
	
	public final static Gsing get(){
		if(Gsing.instance == null){
			Gsing.instance = new Gsing();
		}
		return Gsing.instance; 
	}
}
