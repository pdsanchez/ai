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
 *
 * @author pdsanchez
 */
public class UniformCrossover implements CrossoverInterface {

    @Override
    public Individual crossover(Individual parent1, Individual parent2) {
        int chromosomeLength = parent1.getChromosomeLength();
                
        // Initialize offspring
        Individual offspring = new Individual(chromosomeLength);

        // Loop over genome
        for (int geneIndex = 0; geneIndex < chromosomeLength; geneIndex++) {
            // Use half of parent1's genes and half of parent2's genes
            if (0.5 > Math.random()) {
                offspring.setGene(geneIndex, parent1.getGene(geneIndex));
            } else {
                offspring.setGene(geneIndex, parent2.getGene(geneIndex));
            }
        }

        return offspring;
    }
    
}
