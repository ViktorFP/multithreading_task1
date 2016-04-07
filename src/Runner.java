import by.epamlab.Constants;
import by.epamlab.beans.Car;
import by.epamlab.beans.Race;

public class Runner {
	private final static long DISQUALIFY_TIME = 5000;
	private static long timeSleep = Long.MAX_VALUE;
	private static String winner;

	private static boolean hasFinished(Race race) {
		Thread[] threads = race.getThreads();
		for (int i = 0; i < threads.length; i++) {
			if (!threads[i].isAlive()) {
				winner = (race.getCar(i)).getName();
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

	private static void disqualify(Race race) {
		for (Thread thread : race.getThreads()) {
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
		Race race = new Race(cars);
		correctMinStep(cars);
		race.start();
		System.out.println();
		try {
			// Observe which finishes first.
			while (!hasFinished(race)) {
				Thread.sleep(timeSleep);
			}
			// 2. output the winner message
			System.out.println("\nWinner is " + winner + "!\n");
			// 3. Disqualify another cars
			Thread.sleep(DISQUALIFY_TIME);
			disqualify(race);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()
					+ Constants.INTERRUPTED_EXCEPTION);
		}
	}
}
