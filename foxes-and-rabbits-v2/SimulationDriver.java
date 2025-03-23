import java.util.ArrayList;
import java.util.Scanner;


public class SimulationDriver {
    public static ArrayList<Class<? extends Animal>> animalClasses;
    public static void main(String[] args) {

        animalClasses = new ArrayList<Class<? extends Animal>>();
        animalClasses.add(Fox.class);
        animalClasses.add(Rabbit.class);
        animalClasses.add(SquirrelWithGun.class);

        Simulator mySim = new Simulator();

        char choice;
        Scanner sc = new Scanner(System.in);
        do {
            choice = getOption(sc);
            takeAction(choice, mySim);
        } while (choice != 'X');
    }

    private static char getOption(Scanner sc) {
        System.out.println("Enter a menu option");
        System.out.println(" R. Reset the simulation");
        System.out.println(" 1. Simulate one step");
        System.out.println(" 2. Simulate two steps");
        System.out.println(" 3. Simulate three steps");
        System.out.println(" 4. Simulate forty steps");
        System.out.println(" 5. Simulate fifty steps");
        System.out.println(" 0. Simulate 100 steps");
        System.out.println(" L. Run long simulation (4000 steps)");
        System.out.println(" X. Exit simulator");
        return sc.next().toUpperCase().charAt(0);
    }

    private static void takeAction(char choice, Simulator mySim) {
        switch (choice) {
        case ('R'): 
            mySim.reset();
            break;
        case('3'):
            mySim.simulateOneStep();
        case('2'):
            mySim.simulateOneStep();
        case('1'):
            mySim.simulateOneStep();
            break;
        case('5'):
            mySim.simulate(10);
        case('4'):
            mySim.simulate(40);
            break;
        case('0'):
            mySim.simulate(100);
            break;
        case('L'):
            mySim.runLongSimulation();
            break;
        case('X'):
            System.out.println("Goodbye.");
            break;
        }
    }


}