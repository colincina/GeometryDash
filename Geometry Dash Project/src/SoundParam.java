
public final class SoundParam {

	private static SoundParam instance = null; 
	
	float musicLevel = 1; 
	float fxLevel = 1; 
	boolean soundActive = true; 
	
	private SoundParam (){
		super(); 
	}
	
	public final static SoundParam getinstance(){
		if(SoundParam.instance == null){
			SoundParam.instance = new SoundParam();
		}
		return SoundParam.instance; 
	}
}
