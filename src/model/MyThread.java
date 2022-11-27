package model;

import javafx.scene.control.Button;

/**
 * 
 * @author Viktor Szitkey
 *
 */
public class MyThread extends Thread {

	protected boolean running = false;
	Button tick;

	/**
	 * 
	 * @param name
	 */
	MyThread(String name, Button tick) {

		this.setName(name);
		this.tick = tick;
		this.setDaemon(true);
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		try {
			running = true;
			
			while(true) { 
				tick.fire();
	
				Thread.sleep(2000);
	
				while (!running) {
					synchronized (this) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
