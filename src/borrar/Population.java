/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package borrar;

import java.util.Arrays;
import java.util.Random;

/**
 * A population is an abstraction of a collection of individuals. The population
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population
 * as a whole, and selecting individuals to mutate or crossover.
 *
 * @author pdsanchez
 */
public class Population {

    private final Individual[] population;
    private double populationFitness = -1;

    /**
     * Initializes blank population of individuals
     *
     * @param populationSize The number of individuals in the population
     */
    public Population(int populationSize) {
        // Initial population
        this.population = new Individual[populationSize];
    }

    /**
     * Initializes Population with specific individuals
     *
     * @param population The individuals array
     */
    public Population(Individual[] population) {
        // Initial population
        this.population = population;
    }

    /**
     * Get individuals from the population
     *
     * @return individuals Individuals in population
     */
    public Individual[] getIndividuals() {
        return this.population;
    }

    /**
     * Find an individual in the population by its fitness
     *
     * This method lets you select an individual in order of its fitness. This
     * can be used to find the single strongest individual (eg, if you're
     * testing for a solution), but it can also be used to find weak individuals
     * (if you're looking to cull the population) or some of the strongest
     * individuals (if you're using "elitism").
     *
     * @param idx The index of the individual you want, sorted by fitness. 0 is
     * the strongest, population.length - 1 is the weakest.
     * @return individual Individual at index
     */
    public Individual getFittest(int idx) {
        // Order population by fitness
        Arrays.sort(this.population);

        // Return the fittest individual
        return this.population[idx];
    }

    /**
     * Set population's group fitness
     *
     * @param fitness The population's total fitness
     */
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    /**
     * Get population's group fitness
     *
     * @return populationFitness The population's total fitness
     */
    public double getPopulationFitness() {
        return this.populationFitness;
    }

    /**
     * Get population's size
     *
     * @return size The population's size
     */
    public int size() {
        return this.population.length;
    }

    /**
     * Set individual at index
     *
     * @param individual
     * @param idx
     * @return individual
     */
    public Individual setIndividual(int idx, Individual individual) {
        return population[idx] = individual;
    }

    /**
     * Get individual at index
     *
     * @param idx
     * @return individual
     */
    public Individual getIndividual(int idx) {
        return population[idx];
    }

    /**
     * Shuffles the population in-place
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int idx = rnd.nextInt(i + 1);
            Individual a = population[idx];
            population[idx] = population[i];
            population[i] = a;
        }
    }
}
