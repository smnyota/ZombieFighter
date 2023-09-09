package assignment8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;
import support.cse131.NotYetImplementedException;

public class Entity {

	/**
	 * @param isZombie the undead state of this Entity. true if zombie, false
	 *                 otherwise.
	 * @param x        the x-coordinate of this Entity's center.
	 * @param y        the y-coordinate of this Entity's center.
	 * 
	 * 
	 */

	public boolean isZombie;
	public double x;
	public double y;
	public double speed = 0.2;
	public double radius = 0.008;

	public Entity(boolean isZombie, double x, double y) {

		// FIXME
		this.isZombie = isZombie;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return true if zombie, false otherwise
	 */
	public boolean isZombie() {
		if (isZombie) {
			return true;
		}

		return false;
	}

	/**
	 * @return the center x-coordinate
	 */
	public double getX() {

		// FIXME
		return this.x;
	}

	/**
	 * @return the center y-coordinate
	 */
	public double getY() {

		// FIXME
		return this.y;
	}

	/**
	 * @return the radius
	 */
	// going to use a radius of 0.008
	public double getRadius() {

		return 0.008;

	}

	/**
	 * Draw this Entity.
	 */
	public void draw() {

		if (this.isZombie) {
			StdDraw.setPenColor(Color.RED);
		} else {
			StdDraw.setPenColor(Color.BLACK);
		}

		StdDraw.filledCircle(x, y, this.getRadius());

	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @return distance between this Entity's center and the specified other point.
	 */
	public double distanceCenterToPoint(double xOther, double yOther) {

		double xDistance = Math.pow((this.x - xOther), 2);
		double yDistance = Math.pow((this.y - yOther), 2);
		double sum = xDistance + yDistance;
		return Math.sqrt(sum);
	}

	/**
	 * @param other the other Entity
	 * @return the distance between this Entity's center and the specified other
	 *         Entity's center.
	 */
	public double distanceCenterToCenter(Entity other) {
		return distanceCenterToPoint(other.getX(), other.getY());
	}

	/**
	 * @param xOther      the x-coordinate of another Entity's center.
	 * @param yOther      the y-coordinate of another Entity's center.
	 * @param radiusOther the radius of another Entity.
	 * @return the distance between this Entity's edge and the specified other
	 *         Entity's edge.
	 */
	public double distanceEdgeToEdge(double xOther, double yOther, double radiusOther) {

		// FIXME
		double centerDistance = this.distanceCenterToPoint(xOther, yOther);
		double sumRadii = this.getRadius() + radiusOther;
		double edgeDistance = centerDistance - sumRadii;
		return edgeDistance;
	}

	/**
	 * @param other the other Entity.
	 * @return the distance between this Entity's edge and the specified other
	 *         Entity's edge.
	 */
	public double distanceEdgeToEdge(Entity other) {
		return distanceEdgeToEdge(other.getX(), other.getY(), other.getRadius());
	}

	/**
	 * @param xOther      the x-coordinate of another Entity's center.
	 * @param yOther      the y-coordinate of another Entity's center.
	 * @param radiusOther the radius of another Entity.
	 * @return true if the bounding circle of this Entity overlaps with the bounding
	 *         circle of the specified other Entity, false otherwise.
	 */
	public boolean isTouching(double xOther, double yOther, double radiusOther) {

		//			double edgeDistance = this.distanceEdgeToEdge(xOther,yOther,radiusOther);

		// if the center radius is less than this.radius + the otherRadius then they are
		// touching
		double centerDistance = this.distanceCenterToPoint(xOther, yOther);
		if (centerDistance <= this.getRadius() + radiusOther) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @param other the other Entity
	 * @return true if the bounding circle of this Entity overlaps with the bounding
	 *         circle of the specified other Entity, false otherwise.
	 */
	public boolean isTouching(Entity other) {
		return isTouching(other.getX(), other.getY(), other.getRadius());
	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @param amount the amount to move toward the point.
	 */
	public void moveToward(double xOther, double yOther, double amount) {
		double xVector = xOther - getX();
		double yVector = yOther - getY();
		double angle = Math.atan2(yVector, xVector);
		double xAmount = amount * Math.cos(angle);
		double yAmount = amount * Math.sin(angle);

		this.x += xAmount;
		this.y += yAmount;

	}

	/**
	 * @param other  the other Entity
	 * @param amount the amount to move toward the other Entity.
	 */
	public void moveToward(Entity other, double amount) {
		moveToward(other.getX(), other.getY(), amount);
	}

	/**
	 * @param xOther x-coordinate of the other point.
	 * @param yOther y-coordinate of the other point.
	 * @param amount the amount to move away from the point.
	 */

	// a little stuck with this logic
	public void moveAwayFrom(double xOther, double yOther, double amount) {
		moveToward(xOther, yOther, -amount);

	}

	/**
	 * @param other  the other Entity
	 * @param amount the amount to move away from the other Entity.
	 */
	public void moveAwayFrom(Entity other, double amount) {
		moveAwayFrom(other.getX(), other.getY(), amount);
	}

	/**
	 * @param entities          this list of entities to search.
	 * @param includeZombies    whether to include zombies in this search or not.
	 * @param includeNonzombies whether to include nonzombies in this search or not.
	 * @return the closest Entity to this Entity in entities (which is not this).
	 */
	private Entity findClosest(List<Entity> entities, boolean includeZombies, boolean includeNonzombies) {
		Entity closest = null;
		double closestDist = Double.MAX_VALUE;
		for (Entity other : entities) {
			// NOTE:
			// We almost always want to use the equals method when comparing Objects.
			// In this case, we check if the two entities are the exact same instance.
			if (this != other) {
				if ((other.isZombie() && includeZombies) || (!other.isZombie() && includeNonzombies)) {
					double dist = distanceEdgeToEdge(other);
					if (dist < closestDist) {
						closest = other;
						closestDist = dist;
					}
				}
			}
		}
		return closest;
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest nonzombie to this Entity in entities (which is not this).
	 */
	public Entity findClosestNonzombie(List<Entity> entities) {
		return findClosest(entities, false, true);
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest zombie to this Entity in entities (which is not this).
	 */
	public Entity findClosestZombie(List<Entity> entities) {
		return findClosest(entities, true, false);
	}

	/**
	 * @param entities this list of entities to search.
	 * @return the closest Entity to this Entity in entities (which is not this).
	 */
	public Entity findClosestEntity(List<Entity> entities) {
		return findClosest(entities, true, true);
	}

	/**
	 * Updates the appropriate state (instance variables) of this Entity for the
	 * current frame of the simulation.
	 * 
	 * @param entities  list of remaining entities in the simulation. It is likely
	 *                  that this Entity will be one of the entities in the list.
	 *                  Care should be taken to not overreact to oneself.
	 * @param deltaTime the change in time since the previous frame
	 * 
	 * @return true if this Entity remains in existence past the current frame,
	 *         false if consumed
	 */

	// 1) check if it s zombie is true or not
	//We need to systematically move to the closest zombie
	//Note: consumed 20% of the time and turn into zombies 80% of the time
	//Only case in which we return False is when the Entity is consumed and we don't want it to make it to 
	//the next frame

	public boolean update(List<Entity> entities, double deltaTime) {
		// for each entity in the entities array

		//making sure we don't run off the page 
		//Should have all edge cases 100% covered
		if (this.x >= 1) {
			this.x = 1;
		}

		if (this.x <= 0) {
			this.x = 0;
		}

		if (this.y >= 1) {
			this.y = 1;
		}

		if (this.y <= 0) {
			this.y = 0;
		}

		//if it is a zombie we want to move towards the closest non zombie
		if (this.isZombie) {
			if (this.findClosestNonzombie(entities) == null) {
				
				return true;
			} else {
				moveToward(findClosestNonzombie(entities), deltaTime * speed);
				return true;
			}
		} else {
			
			//if it is not a zombie 
			//first make sure it's not null
			if (findClosestZombie(entities) == null) {
				return true;
			} 
			
			//Below: this is refereing to a none zombie
			//find the closest Zombie and if it is touching then it has an 80% chance of being turned into zombies and we want to
			//move toward it
			//Should turn into zombies 80% of the time and about 20% of the time should be consumed
			else if (this.isTouching(findClosestZombie(entities))) {
					double chance = Math.random();
					if (chance < 0.8) {
						this.isZombie = true;
						if (this.findClosestNonzombie(entities) != null) {
							moveToward(findClosestNonzombie(entities), deltaTime * speed);
						}
						return true;
					} 
					else {
						//the other 20% chance
					if (findClosestZombie(entities).radius < 0.2) {
							findClosestZombie(entities).radius += this.radius * 1000; // increasing it by 20%
							if (findClosestNonzombie(entities) != null) {
								moveToward(findClosestNonzombie(entities), deltaTime * speed);
							}
							return false;
						}
						return true;
					}
				} 
				else {
					moveAwayFrom(findClosestZombie(entities), deltaTime * speed);
					return true;
				}

			}

		}
	}

