import java.util.List;
import java.util.Random;
import java.awt.Color;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The probability that an animal will be created in any given grid position.
    private static final double CREATION_PROBABILITY = 0;
    // The animal's simulator color
    private static final Color color = Color.BLACK;
    

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        this.age = 0;
        setLocation(location);
    }

    public Animal(){
        alive = true;
        this.age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Return the animal's age
     * @return The animal's age
     */
    protected int getAge() 
    {
        return this.age;
    }

    /**
     * Set the animal's age
     * @param age The animal's new age
     */
    protected void setAge(int age)
    {
        this.age = age;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed
     */
    protected boolean canBreed() 
    {
        return age >= getBreedingAge();
    }

    /**
     * Return the breeding age of this animal
     * @return The breeding age of this animal
     */
    abstract protected int getBreedingAge();

    /**
     * Increases age.
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Returns the max age of this animal
     * @return The max age of this animal
     */
    abstract protected int getMaxAge();

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Returns the animal's breeding probability
     * @return The animal's breeding probability
     */
    abstract protected double getBreedingProbability();

    /**
     * Return the animal's max litter size
     * @return The animal's max litter size
     */
    abstract protected int getMaxLitterSize();

    protected void giveBirth(List<Animal> newAnimals, Class<? extends Animal> animalClass){
        Field field = getField();
        List<Location> freePlace = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && freePlace.size() > 0; b++){
            Location loc = freePlace.remove(0);
            Animal young = null;
            try {
                young = (Animal) animalClass.getConstructor(Field.class, Location.class).newInstance(field, loc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (young != null) {
                newAnimals.add(young);
            }
        }
    }

    protected double getCreationProbability(){
        return CREATION_PROBABILITY;
    }

    protected Color getColor(){
        return color;
    }

    protected void setField(Field field){
        this.field = field;
    }

    protected Random getRandomNum(){
        return rand;
    }

    
}