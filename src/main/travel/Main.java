/*
 * Copyright (c) 2016, pdsanchez
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package main.travel;

import es.pdsanchez.ai.ga.GeneticAlgorithm;
import es.pdsanchez.ai.ga.Individual;

/**
 *
 * @author pdsanchez
 */
public class Main extends GeneticAlgorithm {

    public Main(int chromosomeLength) {
        super(chromosomeLength);
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
    public void populateChromosome(Individual individual) {
        for (int i = 0; i < individual.getChromosomeLength(); i++) {
            individual.setGene(i, i + 1);
        }
        individual.shuffleChromosome();
    } 

    public static void main(String[] args) {
        Main ga = new Main(8);
        ga.setParentSelector(ParentSelector.TOURNAMENT);
        ga.setCrossoverSelector(CrossoverSelector.UNIFORM_CROSSOVER);
        ga.setMutationSelector(MutationSelector.SWAP_MUTATION);
        ga.run();
    }

}
