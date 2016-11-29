import java.util.ArrayList;
import java.util.Iterator;

public class MainControlSystem extends Thread{
	int idleTime;
	ArrayList<Elevator> availableElevator;
	ArrayList<Kiosk> availableKiosk;	
	ArrayList<Order> userOrders;
	
	public MainControlSystem(ArrayList<Kiosk> kiosks, ArrayList<Elevator> elevators, int idleTime){	
		availableElevator = elevators;
		availableKiosk = kiosks;
		userOrders = new ArrayList<Order>();
		idleTime = this.idleTime;
		start();
	}
	
	public void run(){
		while(true){
			if(userOrders.size()>0){
				for (Iterator<Order> iterator = userOrders.iterator(); iterator.hasNext();) {
				    Order processOrder = iterator.next();
				    
				    if(assignNearby(processOrder.getKioskFloor(),processOrder.getDestinationFloor())){
				        iterator.remove();
				        
				    }
				}
				/*
			    if(assignNearby(userOrders.get(0).getKioskFloor(),userOrders.get(0).getDestinationFloor())){
			    	userOrders.remove(0);
			    }
			    */
			}
			try {
				sleep(500);
			}
			catch(InterruptedException e) {
			}
		}
	}
	
	public int getAvailableElevatorCount(){
		return this.availableElevator.size();
	}
	
	public void receivedUserRequest(int kioskFloor, int destFloor){
		System.out.println("**System gets request");
		Order newOrder = new Order(kioskFloor, destFloor);
		userOrders.add(newOrder);
		System.out.println("**"+userOrders.size());

	}
	
	public void responseToKiosk(int kioskFloor, String assignedElevator){
		System.out.println("**System sends result to kiosk");
		for(int i=0;i<this.availableKiosk.size();i++){
			Kiosk selectedKiosk = this.availableKiosk.get(i);
			if(selectedKiosk.floor==kioskFloor){
				selectedKiosk.displaySystemResponse(assignedElevator);
				break;
			}
		}
	}
	
	private boolean assignNearby(int kioskFloor ,int destFloor){
		System.out.println("**System finds suitable elevator");
		boolean isUp = true;


		
		ArrayList<Elevator> sameDirectionElevator = new ArrayList<Elevator>();
		
		for(int i=0;i<availableElevator.size();i++){
			Elevator elevator = availableElevator.get(i);			
			if(isUp){
				if((elevator.getStatus().equals("up")&&elevator.getCurrentFloor()<kioskFloor)||elevator.getStatus().equals("idle")){
					sameDirectionElevator.add(elevator);
				}
			}else{
				if((elevator.getStatus().equals("down")&&elevator.getCurrentFloor()>kioskFloor)||elevator.getStatus().equals("idle")){
					sameDirectionElevator.add(elevator);
				}
			}
		}
		
		if(sameDirectionElevator.size()>0){
			int cache = availableKiosk.size();
			int selectedIndex = 0;
			for(int i=0;i<sameDirectionElevator.size();i++){
				Elevator elevator = sameDirectionElevator.get(i);
				
				if(cache>Math.abs(kioskFloor - elevator.getCurrentFloor())){
					cache = Math.abs(kioskFloor - elevator.getCurrentFloor());
					selectedIndex = i;
				}
			}
			Elevator selectedElevator = sameDirectionElevator.get(selectedIndex);

			if(selectedElevator.getStatus().equals("idle")&&kioskFloor!=selectedElevator.getCurrentFloor())
				selectedElevator.setStartFloor(kioskFloor);
			selectedElevator.isMoveToOtherFloor[destFloor]=true;
			
			responseToKiosk(kioskFloor, selectedElevator.getID());
			return true;
		}
		
		return false;
	}
}
