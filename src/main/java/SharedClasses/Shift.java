package SharedClasses;

//if worker is possibly can work in this shift so idOfManager will remain empty string
// else (if worker was set to this shift than idOfManager must not remain empty!(must be a shift manager
public class Shift {

	private int code;
	private String date;
	private String day;
	private String time; // evening/morning/night
	private int WorkPlace;
	private int shiftManagerID; 
	
	public Shift(int code, String date, String day, String time, int WorkPlace, int shiftManagerID) {
		
		this.code = code;
		this.date = date;
		this.day = day;
		this.time = time;
		this.shiftManagerID = shiftManagerID;
		this.WorkPlace = WorkPlace;
	}

	public int getCode(){
		return code;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDay(){
		return day;
	}
	
	public void setDay(String day){
		this.day = day;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public int getManager(){
		return shiftManagerID;
	}
	
	public void setManager(int Manager){
		this.shiftManagerID = Manager;
	}
	
	public void setCode(int code){
		  this.code = code;
	}

	public int getWorkPlace(){ return WorkPlace; }

	public void setWorkPlace(int WorkPlace){ this.WorkPlace = WorkPlace; }
}
