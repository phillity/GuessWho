//person class
class Person{
	//member variables
	private String name;
	private Boolean hat;
	private Boolean glasses;
	private Boolean gender;
	private Boolean facialHair;
	private Boolean bald;
	private String hairColor;
	
	//name getters and setters
	public void setName(String name){
       this.name = name;
    }
    public String getName(){
       return name;
    }
    
    //hat getters and setters
    public void setHat(String inHat){
    	if (inHat.toUpperCase().equals("NO HAT")){
    		this.hat = false;
    	}
    	else{
    		this.hat = true;
    	}
    }
    public Boolean getHat(){
        return hat;
    }
    
    //glasses getters and setters
    public void setGlasses(String inGlasses){
    	if (inGlasses.toUpperCase().equals("NO GLASSES")){
    		this.glasses = false;
    	}
    	else{
    		this.glasses = true;
    	}
    }
    public Boolean getGlasses(){
        return glasses;
    }
    
    //gender getters and setters
	public void setGender(String inGender){
		if (inGender.toUpperCase().equals("FEMALE")){
    		this.gender = false;
    	}
    	else{
    		this.gender = true;
    	}
	}
	public Boolean getGender(){
		return gender;
	}
	
	//facialHair getters and setters
	public void setFacialHair(String inFacialHair){
		if (inFacialHair.toUpperCase().equals("NO FACIAL HAIR")){
    		this.facialHair = false;
    	}
    	else{
    		this.facialHair = true;
    	}
	}
	public Boolean getFacialHair(){
		return facialHair;
	}
	
	//bald getters and setters
	public void setBald(String inBald){
		if (inBald.toUpperCase().equals("NOT BALD")){
    		this.bald = false;
    	}
    	else{
    		this.bald = true;
    	}
	}
	public Boolean getBald(){
		return bald;
	}
	
	//bald getters and setters
	public void setHairColor(String hairColor){
		this.hairColor = hairColor;
	}
	public String getHairColor(){
		return hairColor;
	}
	
	//person constructor 
	public Person(String[] arrOfString){
		//person will take an array of strings
		//each string will correspond with person object's member variables
		setName(arrOfString[0]);
		setHat(arrOfString[1]);
		setGlasses(arrOfString[2]);
		setGender(arrOfString[3]);
		setFacialHair(arrOfString[4]);
		setBald(arrOfString[5]);
		setHairColor(arrOfString[6]);
	} // end constructor
} // end class def