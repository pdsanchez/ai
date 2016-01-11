/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package borrar;

/**
 * The GeneticAlgorithm class is our main abstraction for managing the
 * operations of the genetic algorithm. This class is meant to be
 * problem-specific, meaning that (for instance) the "calcFitness" method may
 * need to change from problem to problem.
 *
 * This class concerns itself mostly with population-level operations, but also
 * problem-specific operations such as calculating fitness, testing for
 * termination criteria, and managing mutation and crossover operations (which
 * generally need to be problem-specific as well).
 *
 * Generally, GeneticAlgorithm might be better suited as an abstract class or an
 * interface, rather than a concrete class as below. A GeneticAlgorithm
 * interface would require implementation of methods such as
 * "isTerminationConditionMet", "calcFitness", "mutatePopulation", etc, and a
 * concrete class would be defined to solve a particular problem domain. For
 * instance, the concrete class "TravelingSalesmanGeneticAlgorithm" would
 * implement the "GeneticAlgorithm" interface. This is not the approach we've
 * chosen, however, so that we can keep each chapter's examples as simple and
 * concrete as possible.
 *
 * @author pdsanchez
 */
public abstract class GeneticAlgorithm {
    
    public static final int DEFAULT_POPULATION_SIZE = 100;
    public static final double DEFAULT_MUTATION_RATE = 0.01;
    public static final double DEFAULT_CROSSOVER_RATE = 0.95;
    public static final int DEFAULT_ELISTISM_COUNT = 2;

    private int populationSize;

    /**
     * Mutation rate is the fractional probability than an individual gene will
     * mutate randomly in a given generation. The range is 0.0-1.0, but is
     * generally small (on the order of 0.1 or less).
     */
    private double mutationRate;

    /**
     * Crossover rate is the fractional probability that two individuals will
     * "mate" with each other, sharing genetic information, and creating
     * offspring with traits of each of the parents. Like mutation rate the
     * rance is 0.0-1.0 but small.
     */
    private double crossoverRate;

    /**
     * Elitism is the concept that the strongest members of the population
     * should be preserved from generation to generation. If an individual is
     * one of the elite, it will not be mutated or crossover.
     */
    private int elitismCount;

    public GeneticAlgorithm() {
        this.populationSize = DEFAULT_POPULATION_SIZE;
        this.mutationRate = DEFAULT_MUTATION_RATE;
        this.crossoverRate = DEFAULT_CROSSOVER_RATE;
        this.elitismCount = DEFAULT_ELISTISM_COUNT;
        
        
    }

    public abstract Population initPopulation();

    public abstract double calcFitness(Individual individual);

    public abstract void evalPopulation(Population population);

    public abstract boolean isTerminationConditionMet(Population population);

    public abstract Individual selectParent(Population population);

    public abstract Population crossoverPopulation(Population population);

    public abstract Population mutatePopulation(Population population);

    public void run() {
        // Initialize population
        Population population = this.initPopulation();

        // Evaluate population
        this.evalPopulation(population);

        // Keep track of current generation
        int generation = 1;

        /**
         * Start the evolution loop
         *
         * Every genetic algorithm problem has different criteria for finishing.
         * In this case, we know what a perfect solution looks like (we don't
         * always!), so our isTerminationConditionMet method is very
         * straightforward: if there's a member of the population whose
         * chromosome is all ones, we're done!
         */
        while (this.isTerminationConditionMet(population) == false) {
            // Print fittest individual from population
            System.out.println("Best solution: " + population.getFittest(0).toString());

            // Apply crossover
            population = this.crossoverPopulation(population);

            // Apply mutation
            population = this.mutatePopulation(population);

            // Evaluate population
            this.evalPopulation(population);

            // Increment the current generation
            generation++;
        }

        /**
         * We're out of the loop now, which means we have a perfect solution on
         * our hands. Let's print it out to confirm that it is actually all
         * ones, as promised.
         */
        //System.out.println("Found solution in " + generation + " generations");
        //System.out.println("Best solution: " + population.getFittest(0).toString());
    }

    /**
     * @return the populationSize
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * @param populationSize the populationSize to set
     */
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    /**
     * @return the mutationRate
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * @param mutationRate the mutationRate to set
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    /**
     * @return the crossoverRate
     */
    public double getCrossoverRate() {
        return crossoverRate;
    }

    /**
     * @param crossoverRate the crossoverRate to set
     */
    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    /**
     * @return the elitismCount
     */
    public int getElitismCount() {
        return elitismCount;
    }

    /**
     * @param elitismCount the elitismCount to set
     */
    public void setElitismCount(int elitismCount) {
        this.elitismCount = elitismCount;
    }
    
    
}
