package soso;

public class Scene {
	private String type;
	private int data;
	private String discription;
	
	public Scene(String type, int data, String discription){
		this.type = type;
		this.data = data;
		this.discription = discription;
	}
	
	public String getDiscription(){return discription;}
	public void setDiscription(String discription){this.discription = discription;}
	
	public String getType() {return type;}
	public void setType(String type){this.type = type;}
	
	public int getData(){return data;}
	public void setData(int data){this.data = data;}
	
}
