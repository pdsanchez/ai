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
package es.pdsanchez.ai.ga.crossover;

import es.pdsanchez.ai.ga.Individual;

/**
 * Crossover population using single point crossover
 *
 * Single-point crossover differs from the crossover used in chapter2.
 * Chapter2's version simply selects genes at random from each parent, but in
 * this case we want to select a contiguous region of the chromosome from each
 * parent.
 *
 * For instance, chapter2's version would look like this:
 *
 * Parent1: AAAAAAAAAA Parent2: BBBBBBBBBB Child : AABBAABABA
 *
 * This version, however, might look like this:
 *
 * Parent1: AAAAAAAAAA Parent2: BBBBBBBBBB Child : AAAABBBBBB
 *
 * @param population Population to crossover
 * @return Population The new population
 *
 * @author pdsanchez
 */
public class TwoPointCrossover implements CrossoverInterface {

    @Override
    public Individual crossover(Individual parent1, Individual parent2) {
        int chromosomeLength = parent1.getChromosomeLength();
                
        // Initialize offspring
        Individual offspring = new Individual(chromosomeLength);

        // Get random swap points
        int swapPoint1 = (int) (Math.random() * (chromosomeLength + 1));
        int swapPoint2 = (int) (Math.random() * (chromosomeLength + 1));

        // Loop over genome
        for (int geneIndex = 0; geneIndex < chromosomeLength; geneIndex++) {
            if (geneIndex < swapPoint1) {
                offspring.setGene(geneIndex, parent1.getGene(geneIndex));
            } 
            else if (geneIndex < swapPoint2) {
                offspring.setGene(geneIndex, parent2.getGene(geneIndex));
            }
            else {
                offspring.setGene(geneIndex, parent1.getGene(geneIndex));
            }
        }
        return offspring;
    }
}
