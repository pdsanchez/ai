package es.pdsanchez.ai.ga.crossover;

import java.util.Arrays;

import es.pdsanchez.ai.ga.Individual;

public class OrderedCrossover implements CrossoverInterface {

	@Override
	public Individual crossover(Individual parent1, Individual parent2) {
		int chromosomeLength = parent1.getChromosomeLength();
        
        // Initialize offspring
		int offspringChromosome[] = new int[chromosomeLength];
		Arrays.fill(offspringChromosome, -1);
		Individual offspring = new Individual(offspringChromosome);

		// Get subset of parent chromosomes
		int substrPos1 = (int) (Math.random() * chromosomeLength);
		int substrPos2 = (int) (Math.random() * chromosomeLength);

		// make the smaller the start and the larger the end
		final int startSubstr = Math.min(substrPos1, substrPos2);
		final int endSubstr = Math.max(substrPos1, substrPos2);

		// Loop and add the sub tour from parent1 to our child
		for (int i = startSubstr; i < endSubstr; i++) {
			offspring.setGene(i, parent1.getGene(i));
		}
      
		// Loop through parent2's city tour
		for (int i = 0; i < chromosomeLength; i++) {
			int parent2Gene = i + endSubstr;
			if (parent2Gene >= chromosomeLength) {
				parent2Gene -= chromosomeLength;
			}
			// If offspring doesn't have the city add it
			if (offspring.containsGene(parent2.getGene(parent2Gene)) == false) {
				// Loop to find a spare position in the child's tour
				for (int ii = 0; ii < chromosomeLength; ii++) {
					// Spare position found, add city
					if (offspring.getGene(ii) == -1) {
						offspring.setGene(ii, parent2.getGene(parent2Gene));
						break;
					}
				}
			}
		}

		return offspring;
	}
}
