
public class Kiosk {
	public int floor;
	MainControlSystem mainSys;
	
	public void setFloor(int floorNumber){
		this.floor = floorNumber;
	}
	
	public void sendOrder(int inputNumber){
		System.out.println("**Kiosk sends order to System");
		if(inputNumber==this.floor){
			System.out.println("Please enter your destination floor again.");
			return;
		}
		
		mainSys.receivedUserRequest(this.floor, inputNumber);
	}
	
	public void displaySystemResponse(String elevatorID){
		System.out.println("Please wait the Elevator "+ elevatorID);
	}
	
	public void setSystem(MainControlSystem s){
		mainSys = s;
	}
	

}
