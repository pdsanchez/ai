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

import es.pdsanchez.ai.ga.crossover.CrossoverInterface;
import es.pdsanchez.ai.ga.crossover.SinglePointCrossover;
import es.pdsanchez.ai.ga.crossover.TwoPointCrossover;
import es.pdsanchez.ai.ga.crossover.UniformCrossover;
import es.pdsanchez.ai.ga.mutation.BitFlipMutation;
import es.pdsanchez.ai.ga.mutation.MutationInterface;
import es.pdsanchez.ai.ga.mutation.SwapMutation;
import es.pdsanchez.ai.ga.selector.ParentSelectionInterface;
import es.pdsanchez.ai.ga.selector.ParentSelectionByTournament;
import es.pdsanchez.ai.ga.selector.ParentSelectionByRoulette;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GeneticAlgorithm class is our main abstraction for managing the
 * operations of the genetic algorithm.
 *
 * This class concerns itself mostly with population-level operations, but also
 * problem-specific operations such as calculating fitness, testing for
 * termination criteria, and managing mutation and crossover operations (which
 * generally need to be problem-specific as well).
 *
 * GeneticAlgorithm is an abstract class and would require implementation of
 * methods such as "isTerminationConditionMet", "calcFitness",
 * "mutatePopulation", etc, and a concrete class would be defined to solve a
 * particular problem domain. For instance, the concrete class
 * "TravelingSalesmanGeneticAlgorithm" would extends the "GeneticAlgorithm"
 * abstract class.
 *
 * @author pdsanchez
 * @author bkanber
 */
public abstract class GeneticAlgorithm {

    public static final Logger LOG = Logger.getLogger(GeneticAlgorithm.class.getName());
            
    // Default values
    public static final int DEFAULT_POPULATION_SIZE = 100;
    public static final double DEFAULT_MUTATION_RATE = 0.01;
    public static final double DEFAULT_CROSSOVER_RATE = 0.95;
    public static final int DEFAULT_ELISTISM_COUNT = 2;
    public static final int DEFAULT_MAX_GENERATIONS = 1000;
    public static final double DEFAULT_TOURNAMENT_PERCENTAGE = 0.1;
    public static final int DEFAULT_TOURNAMENT_RATE = (int)(DEFAULT_POPULATION_SIZE * DEFAULT_TOURNAMENT_PERCENTAGE);

    public enum ParentSelector {

        ROULETTE(new ParentSelectionByRoulette()),
        TOURNAMENT(new ParentSelectionByTournament(DEFAULT_TOURNAMENT_RATE));

        private final ParentSelectionInterface selector;

        ParentSelector(ParentSelectionInterface selector) {
            this.selector = selector;
        }

        public Individual selectParent(Population population) {
            return selector.selectParent(population);
        }
    };

    public enum MutationSelector {

        BIT_FLIP_MUTATION(new BitFlipMutation()),
        SWAP_MUTATION(new SwapMutation());

        private final MutationInterface selector;

        MutationSelector(MutationInterface selector) {
            this.selector = selector;
        }

        public void mutateGene(Individual individual, int geneIndex) {
            selector.mutateGene(individual, geneIndex);
        }
    };

    public enum CrossoverSelector {

        UNIFORM_CROSSOVER(new UniformCrossover()),
        SINGLE_POINT_CROSSOVER(new SinglePointCrossover()),
        TWO_POINT_CROSSOVER(new TwoPointCrossover());

        private final CrossoverInterface selector;

        CrossoverSelector(CrossoverInterface selector) {
            this.selector = selector;
        }

        public Individual crossover(Individual parent1, Individual parent2) {
            return selector.crossover(parent1, parent2);
        }
    };
    
    private final int chromosomeLength;

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

    private int maxGenerations;

    private ParentSelector parentSelector;
    private MutationSelector mutationSelector;
    private CrossoverSelector crossoverSelector;
  
    public GeneticAlgorithm(int chromosomeLength) {
        this.chromosomeLength = chromosomeLength;
        
        this.populationSize = DEFAULT_POPULATION_SIZE;
        this.mutationRate = DEFAULT_MUTATION_RATE;
        this.crossoverRate = DEFAULT_CROSSOVER_RATE;
        this.elitismCount = DEFAULT_ELISTISM_COUNT;
        this.maxGenerations = DEFAULT_MAX_GENERATIONS;

        this.parentSelector = ParentSelector.ROULETTE; // roulette by default
        this.mutationSelector = MutationSelector.BIT_FLIP_MUTATION; // bit flip by default
        this.crossoverSelector = CrossoverSelector.UNIFORM_CROSSOVER; // uniform crossover by default
        
        this.activeLogs();
    }

    /**
     * Populate (and randomize) individual chromosome
     * 
     * @param individual the individual with the chromosome to populate
     */
    public abstract void populateChromosome(Individual individual);

    /**
     * Calculate fitness for an individual.
     *
     * @param individual the individual to evaluate
     * @return double The fitness value for individual
     */
    public abstract double calcFitness(Individual individual);
    
    /**
     * This method must be override.
     *
     * @param population
     * @param generationsCount
     * @return true if the termination condition is met
     */
    public boolean isTerminationConditionMet(Population population, int generationsCount) {
        return (generationsCount > maxGenerations);
    }

    /**
     * This method must be override.
     *
     * @param parent1
     * @param parent2
     * @return offspring
     */
    public Individual crossover(Individual parent1, Individual parent2) {
        return this.crossoverSelector.crossover(parent1, parent2);
    }

    /**
     * This method must be override. byte newGene =
     * individual.getGene(geneIndex) == 1 ? 0 : 1; individual.setGene(geneIndex,
     * newGene); // Mutate gene
     *
     * @param individual
     * @param geneIndex
     */
    public void mutateGene(Individual individual, int geneIndex) {
        this.mutationSelector.mutateGene(individual, geneIndex);
    }

    /**
     * Principal method of this class
     *
     * @return the best individual found
     */
    public Individual run() {
        if (LOG.isLoggable(Level.INFO)) {
            String msg = "GA size {0} - chromosome {1}\n"
                    + "ParentSelector: {2}\n"
                    + "CrossoverSelector: {3} [rate: {4}]\n"
                    + "MutationSelector: {5} [rate: {6}]";
            Object[] params = {populationSize, chromosomeLength, parentSelector, 
                crossoverSelector, crossoverRate, mutationSelector, mutationRate};
            LOG.log(Level.INFO, msg, params);
        }
        
        // Initialize population
        Population population = this._initPopulation();

        // Evaluate population
        this._evalPopulation(population);

        // Keep track of current generation
        int generation = 1;

        // Start the evolution loop
        // Every genetic algorithm problem has different criteria for finishing.
        while (this.isTerminationConditionMet(population, generation) == false) {
            // Print fittest individual from population
            LOG.log(Level.FINE, "Best solution: {0}", population.getFittest(0).toString());

            // Apply crossover
            population = this._crossoverPopulation(population);

            // Apply mutation
            population = this._mutatePopulation(population);

            // Evaluate population
            this._evalPopulation(population);

            // Increment the current generation
            generation++;
        }

        // We have a perfect solution
        LOG.log(Level.INFO, "Best solution [{0} generations]: {1}", 
                new Object[]{generation, population.getFittest(0).toString()});

        return population.getFittest(0);
    }
    
    private Population _initPopulation() {
        Population population = new Population(this.getPopulationSize());

        // Create each individual in turn
        for (int individualCount = 0; individualCount < this.getPopulationSize(); individualCount++) {
            // Create an individual, initializing its chromosome to the given length
            Individual individual = new Individual(chromosomeLength);
            
            this.populateChromosome(individual);
            
            // Add individual to population
            population.setIndividual(individualCount, individual);
        }

        return population;
    }

    private void _evalPopulation(Population population) {
        double populationFitness = 0;

        // Loop over population evaluating individuals and suming population
        // fitness
        for (Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    private Population _crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (getCrossoverRate() > Math.random() && populationIndex >= getElitismCount()) {
                // Find second parent
                Individual parent2 = parentSelector.selectParent(population);

                // Get offspring
                Individual offspring = this.crossover(parent1, parent2);

                // Add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    private Population _mutatePopulation(Population population) {
        // Initialize new population
        Population newPopulation = new Population(this.getPopulationSize());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Loop over individual's genes
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                // Skip mutation if this is an elite individual
                if (populationIndex > this.getElitismCount()) {
                    // Does this gene need mutation?
                    if (this.getMutationRate() > Math.random()) {
                        this.mutateGene(individual, geneIndex);
                    }
                }
            }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
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

    /**
     * @return the maxGenerations
     */
    public int getMaxGenerations() {
        return maxGenerations;
    }

    /**
     * @param maxGenerations the maxGenerations to set
     */
    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    /**
     *
     * @param parentSelector
     */
    public void setParentSelector(ParentSelector parentSelector) {
        this.parentSelector = parentSelector;
    }

    /**
     *
     * @param mutationSelector
     */
    public void setMutationSelector(MutationSelector mutationSelector) {
        this.mutationSelector = mutationSelector;
    }

    /**
     *
     * @param crossoverSelector
     */
    public void setCrossoverSelector(CrossoverSelector crossoverSelector) {
        this.crossoverSelector = crossoverSelector;
    }

    public final void activeLogs() {
        LOG.setLevel(Level.INFO);
    }
    
    public final void disableLogs() {
        LOG.setLevel(Level.OFF);
    }
}
