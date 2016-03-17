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

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An "Individual" represents a single candidate solution. The core piece of
 * information about an individual is its "chromosome", which is an encoding of
 * a possible solution to the problem at hand. A chromosome can be a string, an
 * array, a list, etc (in this class, the chromosome is an integer array)
 *
 * An individual position in the chromosome is called a gene, and these are the
 * atomic pieces of the solution that can be manipulated or mutated. When the
 * chromosome is a string, as in this case, each character or set of characters
 * can be a gene.
 *
 * An individual also has a "fitness" score; this is a number that represents
 * how good a solution to the problem this individual is. The meaning of the
 * fitness score will vary based on the problem at hand.
 *
 * @author pdsanchez
 * @author bkanber
 */
public class Individual implements Comparable<Individual> {

    private final int[] chromosome;
    private double fitness = -1;

    /**
     * Initializes individual with specific chromosome
     *
     * @param chromosome The chromosome to give individual
     */
    public Individual(int[] chromosome) {
        // Create individual chromosome
        this.chromosome = chromosome;
    }
    
    /**
     * Creates the chromosome
     * 
     * @param chromosomeLength
     */
    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
    }

    /**
     * This method assumes that the chromosome is made entirely of 0s and 1s. 
     */
    public void randomizeBinaryChromosome() {
        int chromosomeLength = this.chromosome.length;
        
        for (int gene = 0; gene < chromosomeLength; gene++) {
            int value = (0.5 < Math.random()) ? 1 : 0;
            this.setGene(gene, value);
        }
    }
    
    /**
     * Implementing Fisher–Yates shuffle
     */
    public void shuffleChromosome() {
        int chromosomeLength = this.chromosome.length;
        
        Random rnd = ThreadLocalRandom.current();
        for (int i = chromosomeLength - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = chromosome[index];
            chromosome[index] = chromosome[i];
            chromosome[i] = a;
        }
    }

    /**
     * Gets individual's chromosome
     *
     * @return The individual's chromosome
     */
    public int[] getChromosome() {
        return this.chromosome;
    }

    /**
     * Gets individual's chromosome length
     *
     * @return The individual's chromosome length
     */
    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    /**
     * Set gene at index
     *
     * @param gene
     * @param idx
     */
    public void setGene(int idx, int gene) {
        this.chromosome[idx] = gene;
    }

    /**
     * Get gene at index
     *
     * @param idx
     * @return gene
     */
    public int getGene(int idx) {
        return this.chromosome[idx];
    }

    /**
     * Store individual's fitness
     *
     * @param fitness The individuals fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Gets individual's fitness
     *
     * @return The individual's fitness
     */
    public double getFitness() {
        return this.fitness;
    }

    @Override
    public int compareTo(Individual o) {
        if (this.getFitness() > o.getFitness()) {
            return -1;
        } else if (this.getFitness() < o.getFitness()) {
            return 1;
        }
        return 0;
    }

    /**
     * Display the chromosome as a string.
     *
     * @return string representation of the chromosome
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output.append(this.chromosome[gene]);
        }
        output.append("\t").append(this.fitness);

        return output.toString();
    }
}
