/* Copyright (c) 2016, Pablo D. Sánchez
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package es.pdsanchez.ai.ga;

import java.util.Arrays;
import java.util.Random;

/**
 * A population is an abstraction of a collection of individuals. The population
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population
 * as a whole, and selecting individuals to mutate or crossover.
 *
 * @author pdsanchez
 * @author bkanber
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
