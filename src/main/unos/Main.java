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

    public Main(int chromosomeLength) {
        super(chromosomeLength);
    }

    @Override
    public void populateChromosome(Individual individual) {
        individual.randomizeBinaryChromosome();
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
        //Logger.getLogger(GeneticAlgorithm.class.getName()).setLevel(Level.OFF);
                
        Main ga = new Main(32);
        ga.setParentSelector(ParentSelector.TOURNAMENT);
        ga.setCrossoverSelector(CrossoverSelector.UNIFORM_CROSSOVER);
        ga.run();
    }
}
