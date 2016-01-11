/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package borrar;

/**
 * An "Individual" represents a single candidate solution. The core piece of
 * information about an individual is its "chromosome", which is an encoding of
 * a possible solution to the problem at hand. A chromosome can be a string, an
 * array, a list, etc -- in this class, the chromosome is an integer array. 
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
 */
public class IndividualInteger implements Comparable<IndividualInteger> {

    private final int[] chromosome;
    private double fitness = -1;

    /**
     * Initializes individual with specific chromosome
     *
     * @param chromosome The chromosome to give individual
     */
    public IndividualInteger(int[] chromosome) {
        // Create individual chromosome
        this.chromosome = chromosome;
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
    public int compareTo(IndividualInteger o) {
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
