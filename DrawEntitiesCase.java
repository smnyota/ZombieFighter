package assignment8.debug;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import assignment8.Entity;
import assignment8.ZombieSimulator;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class DrawEntitiesCase {
	private final boolean[] areZombies;
	private final double[][] positions;
	private final String text;

	public DrawEntitiesCase(boolean[] areZombies, double[][] positions, String text) {
		this.areZombies = areZombies;
		this.positions = positions;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<>(areZombies.length);
		for (int i = 0; i < areZombies.length; ++i) {
			entities.add(new Entity(areZombies[i], positions[i][0], positions[i][1]));
		}
		return entities;
	}

	public ZombieSimulator createZombieSimulator() {
		List<String> args = new LinkedList<>();
		args.add(Integer.toString(areZombies.length));
		for (int i = 0; i < areZombies.length; ++i) {
			args.add(areZombies[i] ? "Zombie" : "Nonzombie");
			args.add(Double.toString(positions[i][0]));
			args.add(Double.toString(positions[i][1]));
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		 for (String line : args) {
			 try {
		   baos.write(line.getBytes());
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }

		 byte[] bytes = baos.toByteArray();
		Scanner in = new Scanner(new ByteArrayInputStream(bytes));
		ZombieSimulator zombieSimulator = new ZombieSimulator();
		zombieSimulator.readEntities(in);
		return zombieSimulator;
	}
}
