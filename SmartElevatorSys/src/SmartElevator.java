import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.ArrayList;

public class SmartElevator extends Thread{
	public static void main(String args[]){
		String configFileName = "resources/config.properties";
		Properties configFile = null;
		ArrayList<Elevator> elevators;
		ArrayList<Kiosk> kiosks;
		MainControlSystem system;
		
		//Get configuration file--elevators, no of floors and idle time
		try {
			configFile = new Properties();
		    FileInputStream in = new FileInputStream(configFileName);
		    configFile.load(in);
		    in.close();
		    System.out.println("Success to open config file ("+configFileName+").");
		} catch (FileNotFoundException e) {
		    System.out.println("Failed to open config file ("+configFileName+").");
		    System.exit(-1);
		} catch (IOException e) {
		    System.out.println("Error reading config file ("+configFileName+").");
		    System.exit(-1);
		}
		
		int noOfElevators, noOfFloors, idleTime;
		noOfFloors=Integer.parseInt(configFile.getProperty("numOfFloors"));
		noOfElevators=Integer.parseInt(configFile.getProperty("numOfElevator"));
		idleTime=Integer.parseInt(configFile.getProperty("idleTime"));
		
		//Create objects
		elevators = new ArrayList<Elevator>();
		for(int i=0; i<noOfElevators; i++){
          Elevator elevator = new Elevator(noOfFloors,idleTime);
          elevator.setID(i);
          elevators.add(elevator);
		}
		System.out.println("**Success to create elevators");
		kiosks = new ArrayList<Kiosk>();
		for(int i=0;i<noOfFloors;i++){
			Kiosk kiosk = new Kiosk();
			kiosk.setFloor(i);
			kiosks.add(kiosk);
		}
		System.out.println("**Success to create kiosks");
		system = new MainControlSystem(kiosks, elevators, idleTime);		
		System.out.println("**Success to create main system");
		
		//Identify between objects
		for(int i=0; i<noOfElevators; i++){
			elevators.get(i).setSystem(system);
		}
		for(int i=0;i<noOfFloors;i++){
			kiosks.get(i).setSystem(system);
		}
		System.out.println("**Success to idendity between objects");
		
		//Test case
		/*
		Testcase t = new Testcase();
		System.out.println("**Start testing data...");
		
		for(int i=0;i<t.orders[0].length;i++){
			System.out.println("-----------------------------");
			for(int j=0;j<elevators.size();j++){
				System.out.println("**Elevator "+j+" current floor: "+elevators.get(j).getCurrentFloor());
			}
			System.out.println("New order: "+t.orders[0][i]+"-->"+t.orders[1][i]);
			kiosks.get(t.orders[0][i]).sendOrder(t.orders[1][i]);
			
			try {
				sleep(3000);
			}
			catch(InterruptedException e) {
			}
		}
		*/
		System.out.println("**Start random test...");
		Random r = new Random();
		while(true){
			System.out.println("-----------------------------");
			for(int j=0;j<elevators.size();j++){
				System.out.println("**Elevator "+j+" current floor: "+elevators.get(j).getCurrentFloor());
			}
			int requestFloor = r.nextInt(noOfFloors);
			int destFloor = r.nextInt(noOfFloors);
			System.out.println("New order: "+requestFloor+"-->"+destFloor);
			kiosks.get(requestFloor).sendOrder(destFloor);
			
			try {
				sleep(3000);
			}
			catch(InterruptedException e) {
			}
		}
	}
}