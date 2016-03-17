/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.robot;

import es.pdsanchez.ai.ga.GeneticAlgorithm;
import es.pdsanchez.ai.ga.Individual;

/**
 *
 * @author pdsanchez
 */
public class RobotController extends GeneticAlgorithm {

    /**
     * Initialize a maze. We'll write this by hand, because, y'know, this book
     * isn't called "maze generation algorithms".
     *
     * The 3s represent the correct route through the maze. 1s are walls that
     * can't be navigated through, and 0s are valid positions, but not the
     * correct route. You can follow the 3s visually to find the correct path
     * through the maze.
     *
     * If you've read the docblock for
     * GeneticAlgorithm::isTerminationConditionMet, I mention that we don't know
     * what a perfect solution looks like, so the only constraint we can give
     * the algorithm is the number of generations. That's both true and untrue.
     * In this case, because we made the maze by hand, we actually DO know the
     * winning fitness: it's 29, or the number of 3s below! However, we can't
     * use that as a termination condition; if this maze were procedurally
     * generated we would not necessarily know that the magic number is 29.
     *
     * As a reminder: 0 = Empty 1 = Wall 2 = Starting position 3 = Route 4 =
     * Goal position
     */
    Maze maze1 = new Maze(new int[][]{
        {0, 0, 0, 0, 1, 0, 1, 3, 2},
        {1, 0, 1, 1, 1, 0, 1, 3, 1},
        {1, 0, 0, 1, 3, 3, 3, 3, 1},
        {3, 3, 3, 1, 3, 1, 1, 0, 1},
        {3, 1, 3, 3, 3, 1, 1, 0, 0},
        {3, 3, 1, 1, 1, 1, 0, 1, 1},
        {1, 3, 0, 1, 3, 3, 3, 3, 3},
        {0, 3, 1, 1, 3, 1, 0, 1, 3},
        {1, 3, 3, 3, 3, 1, 1, 1, 4}
    });
    
    Maze maze2 = new Maze(new int[][]{
        {0, 2, 0, 0, 1, 0, 1, 0, 0},
        {1, 3, 1, 1, 1, 0, 1, 0, 1},
        {1, 3, 0, 1, 0, 0, 0, 0, 1},
        {3, 3, 1, 1, 0, 1, 1, 0, 1},
        {3, 1, 1, 0, 0, 1, 1, 0, 0},
        {3, 3, 1, 1, 1, 1, 0, 1, 1},
        {1, 3, 0, 1, 3, 3, 3, 0, 1},
        {0, 3, 1, 1, 3, 1, 3, 1, 0},
        {1, 3, 3, 3, 3, 1, 3, 4, 0}
    });
    
    Maze maze3 = new Maze(new int[][]{
        {0, 0, 0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 1, 3, 3, 0, 0, 0},
        {0, 0, 0, 1, 1, 3, 0, 0, 0},
        {0, 0, 0, 0, 1, 3, 0, 0, 0},
        {0, 1, 1, 3, 3, 3, 0, 0, 0},
        {0, 0, 0, 3, 1, 1, 1, 1, 1},
        {0, 0, 1, 3, 3, 0, 1, 0, 0},
        {0, 0, 1, 1, 3, 0, 1, 0, 0},
        {0, 0, 0, 1, 4, 0, 1, 0, 0}
    });
    
    Maze maze4 = new Maze(new int[][]{
        {0, 0, 0, 0, 1, 2, 0, 0, 0},
        {0, 0, 0, 0, 1, 3, 0, 0, 0},
        {0, 0, 1, 1, 1, 3, 1, 0, 0},
        {0, 0, 1, 0, 3, 3, 1, 0, 0},
        {0, 0, 1, 1, 3, 1, 0, 0, 0},
        {0, 0, 0, 1, 3, 1, 1, 0, 0},
        {0, 0, 1, 1, 3, 0, 1, 0, 0},
        {0, 0, 1, 0, 3, 1, 1, 0, 0},
        {0, 0, 1, 1, 4, 0, 0, 0, 0}
    });
    
    Maze maze5 = new Maze(new int[][]{
        {1, 2, 1, 1, 1, 0, 0, 0, 0},
        {1, 3, 3, 3, 1, 0, 0, 0, 0},
        {1, 1, 1, 3, 1, 0, 0, 0, 0},
        {0, 0, 1, 3, 1, 1, 1, 0, 0},
        {0, 0, 1, 3, 3, 3, 1, 0, 0},
        {0, 1, 1, 1, 1, 3, 1, 0, 0},
        {0, 1, 3, 3, 3, 3, 1, 0, 0},
        {0, 1, 3, 1, 1, 1, 1, 0, 0},
        {0, 1, 4, 1, 0, 0, 0, 0, 0}
    });
    
    Maze[] mazes = {maze1, maze2, maze3, maze4, maze5};

    public RobotController(int chromosomeLength) {
        super(chromosomeLength);
    }

    @Override
    public void populateChromosome(Individual individual) {
        individual.randomizeBinaryChromosome();
    }

    @Override
    public double calcFitness(Individual individual) {
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
        
        //Math.random()
        //Random r = new Random();
        //Maze maze = mazes[r.nextInt(5)];
        Maze maze = mazes[0];

        // Get fitness
        Robot robot = new Robot(chromosome, maze, 100);
        robot.run();
        int fitness = maze.scoreRoute(robot.getRoute());

        // Store fitness
        individual.setFitness(fitness);

        return fitness;
    }

    public static void main(String[] args) {
        RobotController rc = new RobotController(128);
        rc.setMaxGenerations(1000);
        rc.setParentSelector(ParentSelector.TOURNAMENT);
        rc.setCrossoverSelector(CrossoverSelector.TWO_POINT_CROSSOVER);
        Individual best = rc.run();

        Maze maze = new Maze(new int[][]{
            {0, 2, 1, 0},
            {1, 3, 1, 1},
            {0, 3, 3, 1},
            {1, 1, 3, 4}
        });

        Robot robot = new Robot(best.getChromosome(), maze, 100);
        robot.run();
        
        int fitness = maze.scoreRoute(robot.getRoute());
        
        System.out.println("FITNESS: " + fitness);
        System.out.println("ROUTE: " + robot.printRoute());
    }
}
