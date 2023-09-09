package assignment8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import edu.princeton.cs.introcs.StdDraw;
import support.cse131.NotYetImplementedException;
import support.cse131.Timing;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class ZombieSimulator {
	private static final String ZOMBIE_TOKEN_VALUE = "Zombie";

	private LinkedList <Entity> entityList;
	private int numZombies;
	private int numNonZombies;

	/**
	 * Constructs a ZombieSimulator with an empty list of Entities.
	 */
	public ZombieSimulator() {
		
			this.entityList = new LinkedList<Entity>();
		
	}

	/**
	 * @return the current list of active entities (that is: those which have not
	 *         been consumed).
	 */
	public List<Entity> getEntities() {
		
			// FIXME
		return this.entityList;
	}

	/**
	 * Reads an entire zombie simulation file from a specified ArgsProcessor, adding
	 * each Entity to the list of entities.
	 * 
	 * @param ap ArgsProcessor to read the complete zombie simulation file format.
	 */
	public void readEntities(Scanner in) {
		int size = in.nextInt();
		Entity newEntity;
		double xEntity = 0;
		double yEntity = 0;
		//can also use size in for loop condition;--> what it's supposed to be for lol
		while (in.hasNext()) {
			String isZombie = in.next();
			 xEntity = in.nextDouble();
			 yEntity = in.nextDouble();
			
			if (isZombie.equals(ZOMBIE_TOKEN_VALUE)) {
				numZombies++;
				 newEntity = new Entity(true,xEntity, yEntity);
			} else {
				numNonZombies++;
				 newEntity = new Entity(false,xEntity, yEntity);
			}
			entityList.add(newEntity);

		}
	}

	/**
	 * @return the number of zombies in entities.
	 */
	public int getZombieCount() {
		
			return numZombies;
		
	}

	/**
	 * @return the number of nonzombies in entities.
	 */
	public int getNonzombieCount() {
		
			// FIXME
			return numNonZombies;
	}

	/**
	 * Draws a frame of the simulation.
	 */
	public void draw() {
		StdDraw.clear();

		// NOTE: feel free to edit this code to support additional features
		for (Entity entity : getEntities()) {
			entity.draw();
		}

		StdDraw.show(); // commit deferred drawing as a result of enabling double buffering
	}

	/**
	 * Updates the entities for the current frame of the simulation given the amount
	 * of time passed since the previous frame.
	 * 
	 * Note: some entities may be consumed and will not remain for future frames of
	 * the simulation.
	 * 
	 * @param deltaTime the amount of time since the previous frame of the
	 *                  simulation.
	 */
	
	//if it's true we want the previous entity to make it to the next frame
	public void update(double deltaTime) {
		LinkedList <Entity> list = new LinkedList<Entity>(); //rather than removing entities from old list to stay in the simulation
//		for (Entity i : entityList) {
//			if (i.update(entityList, deltaTime)) {  //if it's true then it's in existence to the next frame 
//			list.add(i);
//		}
//	}
	//	entityList = list;
		
		for (int i = 0; i < entityList.size(); i++) {
			if (!entityList.get(i).update(entityList, deltaTime)) {
				entityList.remove(entityList.get(i));
			}
		}
		
		
		
}

	/**
	 * Runs the zombie simulation.
	 * 
	 * @param args arguments from the command line
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		StdDraw.enableDoubleBuffering(); // reduce unpleasant drawing artifacts, speed things up

		JFileChooser chooser = new JFileChooser("zombieSims");
		chooser.showOpenDialog(null);
		File f = new File(chooser.getSelectedFile().getPath());
		Scanner in = new Scanner(f); //making Scanner with a File
		
		ZombieSimulator zombieSimulator = new ZombieSimulator();
		zombieSimulator.readEntities(in);
		//Delta Timing Core Concept
		//prev time = before while loop starts
		//cur time = inside while loop
		//deltaTime = cur-prev
		//set the prev = curtime after the simulation
		double prevTime = Timing.getCurrentTimeInSeconds();
		while (zombieSimulator.getNonzombieCount() >= 0) {
			double currTime = Timing.getCurrentTimeInSeconds();
			double deltaTime = currTime - prevTime;
			if (deltaTime > 0.0) {
				zombieSimulator.update(deltaTime);
				zombieSimulator.draw();
			}
			StdDraw.pause(10);
			prevTime = currTime;
		}
	}
}
