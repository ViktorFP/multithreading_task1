package by.epamlab.beans;

public class Race {
	private Car[] cars;
	private Thread[] threads;
	private int membersCount;

	public Race(Car... cars) {
		this.cars = cars;
		membersCount = cars.length;
		threads = new Thread[membersCount];
	}

	public void start() {
		for (int i = 0; i < membersCount; i++) {
			threads[i] = new Thread(cars[i]);
			threads[i].start();
			System.out.println("Car " + cars[i].getName() + " started");
		}
	}

	public Car getCar(int idx) throws IndexOutOfBoundsException {
		return cars[idx];
	}

	public Thread[] getThreads() {
		return threads;
	}
}
