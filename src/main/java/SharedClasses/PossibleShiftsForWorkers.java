package SharedClasses;

public class PossibleShiftsForWorkers {
	
	private Worker worker;
	private Shift shift; //TODO: gal | ofir : What is this field?
	
	public PossibleShiftsForWorkers(Worker worker, Shift shift) {
		this.worker = worker;
		this.shift = shift;
	}
	
	public Worker getWorker() {
		return worker;
	}
	
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
	public Shift getShift() {
		return shift;
	}
	
	public void setShift(Shift shift) {
		this.shift = shift;
	}

}
