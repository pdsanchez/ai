/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.unos;

import es.pdsanchez.ai.ga.GeneticAlgorithm;
import es.pdsanchez.ai.ga.Individual;
import es.pdsanchez.ai.ga.Population;

/**
 *
 * @author pdsanchez
 */
public class Main extends GeneticAlgorithm {

    @Override
    public Population initPopulation() {
        Population population = new Population(this.getPopulationSize());

        // Create each individual in turn
        int chromosomeLength = 50;
        for (int individualCount = 0; individualCount < this.getPopulationSize(); individualCount++) {
            // Create an individual, initializing its chromosome to the given length
            Individual individual = new Individual(chromosomeLength);
            // Randomize
            individual.randomizeBinaryChromosome();
            // Add individual to population
            population.setIndividual(individualCount, individual);
        }

        return population;
    }

    @Override
    public double calcFitness(Individual individual) {
        int chromosomeLength = individual.getChromosomeLength();
        
        // Track number of correct genes
        int correctGenes = 0;

        // Loop over individual's genes
        for (int geneIndex = 0; geneIndex < chromosomeLength; geneIndex++) {
            // Add one fitness point for each "1" found
            if (individual.getGene(geneIndex) == 1) {
                correctGenes += 1;
            }
        }

        // Calculate fitness
        double fitness = (double) correctGenes / chromosomeLength;

        // Store fitness
        individual.setFitness(fitness);

        return fitness;
    }

    @Override
    public boolean isTerminationConditionMet(Population population, int generationsCount) {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Main ga = new Main();
        ga.setParentSelector(ParentSelector.TOURNAMENT);
        ga.setCrossoverSelector(CrossoverSelector.UNIFORM_CROSSOVER);
        ga.run();
    }
}
