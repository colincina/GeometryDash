
public class Gsing {

	private static Gsing instance = null; 
	
	//Cube parameters
	int cSize = 150; 
	int cParticleAmount = 30; 
	int pImpulse = 20;
	float cSpeed = 55f; 
	float cImpulse;
	
	//Platform parameters
	int platformHeight = 100; 
	int lightHeight = 10; 

	//Hole of the damned
	int holeWidthme1 = 500; 
	int holeWidthme3 = 300; 
	int holeWidthme3Big = 450; 
	int holeWidthme4 = 2000; 
	int holeHeight = 25; 
	
	//Map entity2 parameters
	int me2H  = 500;  
	
	//Map entity3 parameters
	int me3StepSize = 250; 
	boolean bigSize; 
	
	//Map Generation seed 
	long mapGenSeed; 
	
	//Seed Management Logic
	boolean seedBeingUsed = false; 
	long enteredSeed; 
	
	//Jump Detector dimensions 
	int detectorH = 600; 
	int detectorW = 25; 
	
	//Double jump box dimensions
	int boxDim = 20;  
	
	//ME4Obstacle dimensions
	int obsW1 = 150; 
	int obsW2 = 300;  
	int obsH = 50; 
	int obsOffset = 800; 
	
	//VarObstacle dimensions
	int VarObsW1 = 40; 
	int VarObsH1 = 200; 
	
	
	int totalDistance; 

	/* 
	 * Superdupermega private constructor of Henry Death
	 */
	private Gsing(){
		super(); 
	}
	
	/*
	 * incredibly awesome method to get all the variable you need from all over the game
	 */
	public final static Gsing get(){
		if(Gsing.instance == null){
			Gsing.instance = new Gsing();
		}
		return Gsing.instance; 
	}
}
