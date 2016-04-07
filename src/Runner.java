import by.epamlab.Constants;
import by.epamlab.beans.Car;

public class Runner {
	private final static long DISQUALIFY_TIME = 5000;
	private static long timeSleep = Long.MAX_VALUE;
	private static String winner;

	private static void start(Car... cars) {
		for (Car car : cars) {
			(new Thread(car)).start();
			System.out.println("Car " + car.getName() + " started");
		}
		correctMinStep(cars);
	}

	private static boolean hasFinished(Car... cars) {
		for (Car car : cars) {
			Thread thread = car.getThread();
			if (!thread.isAlive()) {
				winner = car.getName();
				return true;
			}
		}
		return false;
	}

	private static void correctMinStep(Car... cars) {
		for (int i = 0; i < cars.length - 1; i++) {
			long difference = Math.abs(cars[i].getFriction()
					- cars[i + 1].getFriction());
			if (difference < timeSleep) {
				timeSleep = difference;
			}
		}
	}

	private static void disqualify(Car... cars) {
		for (Car car : cars) {
			Thread thread = car.getThread();
			if (thread.isAlive()) {
				thread.interrupt();
			}
		}
	}

	public static void main(String[] args) {
		// 1. Create cars with different parameters.
		Car[] cars = { new Car("volvo_KolyaTeam", 200),
				new Car("vas_VasyaTeam", 300), new Car("belas_CrashTeam", 500) };
		// Start cars one by one.
		start(cars);
		try {// waiting threads' ends
			for (Car car : cars) {
				Thread thread = car.getThread();
				thread.join();
			}
			// Observe which finishes first.

			while (!hasFinished(cars)) {
				Thread.sleep(timeSleep);
			}
			// 2. Disqualify another cars
			Thread.sleep(DISQUALIFY_TIME);
			disqualify(cars);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()
					+ Constants.INTERRUPTED_EXCEPTION);
		}
		// 3. output the winner message
		System.out.println("Winner is " + winner + "!");
	}
}
