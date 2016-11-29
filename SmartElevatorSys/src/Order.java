
public class Order {
		private int kioskFloor;
		private int destinationFloor;
		
		public Order(int kioskFloor,int destFloor){
			this.kioskFloor = kioskFloor;
			this.destinationFloor = destFloor;
		}
		
		public int getDestinationFloor() {
			return destinationFloor;
		}
		
		public void setDestinationFloor(int destinationFloor) {
			this.destinationFloor = destinationFloor;
		}
		
		public int getKioskFloor() {
			return kioskFloor;
		}
		
		public void setKioskFloor(int kioskFloor) {
			this.kioskFloor = kioskFloor;
		}
}
