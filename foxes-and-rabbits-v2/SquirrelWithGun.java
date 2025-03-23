import java.util.List;
import java.util.Random;
import java.awt.Color;

/**
 * A simple model of a squirrel.
 * squirrels age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class SquirrelWithGun extends Animal
{
    // Characteristics shared by all squirrel with guns (class variables).

    // The age at which a squirrel with gun can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a squirrel with gun can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a squirrel with gun breeding.
    private static final double BREEDING_PROBABILITY = 0.10;
    // The creation probability
    private static final double CREATION_PROBABILITY = 0.05;
    // The color of the squirrel with gun
    private static final Color COLOR = Color.GRAY;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // The amount of ammo in the gun of the squirrel with gun.
    private int ammo = 50;

    /**
     * Create a new squirrel with gun. A squirrel with gun may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the squirrel with gun will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public SquirrelWithGun(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    public SquirrelWithGun(){
        super();
    }
    
    /**
     * This is what the squirrel with gun does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newSquirrelWithGuns A list to return newly born squirrel with guns.
     */
    public void act(List<Animal> newSquirrelWithGuns)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newSquirrelWithGuns);  
            // Kill random adjacent animal
            if (ammo > 0) {
                Location shootLocation = getField().randomAdjacentLocation(getLocation());
                if (shootLocation != getLocation()) {
                    Animal target = (Animal)getField().getObjectAt(shootLocation);
                    if (target != null) {
                        target.setDead();
                        decrementAmmo();
                    }
                }
            }
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else if (ammo > getField().adjacentLocations(getLocation()).size()){ 
                // Shoot all adjacent animals
                List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
                for (Location loc : adjacentLocations) {
                    Animal target = (Animal)getField().getObjectAt(loc);
                    if (target != null) {
                        target.setDead();
                        decrementAmmo();;
                    }
                }
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void decrementAmmo()
    {
        ammo--;

    }

    /**
     * Check whether or not this squirrel with gun is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSquirrelWithGuns A list to return newly born squirrel with guns.
     */
    private void giveBirth(List<Animal> newSquirrelWithGuns)
    {
        // New squirrel with guns are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            SquirrelWithGun young = new SquirrelWithGun(false, field, loc);
            newSquirrelWithGuns.add(young);
        }
    }
        
    /**
    * Return the breeding age of this animal . 
    * @return The breeding age of this animal. 
    */ 
    protected int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * Returns the max age of this animal
     * @return The max age of this animal
     */
    protected int getMaxAge() 
    {
        return MAX_AGE;
    }

    /**
     * Returns the animal's breeding probability
     * @return The animal's breeding probability
     */
    protected double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the animal's max litter size
     * @return The animal's max litter size
     */
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }

    protected double getCreationProbability(){
        return CREATION_PROBABILITY;
    }

    protected Color getColor(){
        return COLOR;
    }
}
