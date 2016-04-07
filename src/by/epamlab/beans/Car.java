package by.epamlab.beans;

import java.util.logging.Logger;

import by.epamlab.Constants;

public class Car implements Runnable {
	private static final long MAX_DISTANCE = 2500;
	Logger log = Logger.getLogger(Car.class.getName());
	private static final long DISTANCE_STEP = 100;

	private long friction;
	private long distance;
	private String name;

	public Car(String name, long friction) {
		this.name = name;
		this.friction = friction;
	}

	@Override
	public void run() {
		try {
			while (distance < MAX_DISTANCE) {
				Thread.sleep(friction);
				distance += DISTANCE_STEP;
				log.info(name + " " + distance);
			}
			System.out.println(name + " finished");
		} catch (InterruptedException e) {
			System.out.println(name + Constants.INTERRUPTED_EXCEPTION);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFriction() {
		return friction;
	}

	public void setFriction(long friction) {
		this.friction = friction;
	}
}
