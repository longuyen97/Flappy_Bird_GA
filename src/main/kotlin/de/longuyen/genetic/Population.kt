package de.longuyen.genetic

import de.longuyen.DNA_LENGTH
import de.longuyen.core.Callback
import de.longuyen.core.Context
import java.util.*

class Population(private val context: Context, size: Int = 20) {
    private var chromosomes: MutableList<Chromosome> = mutableListOf()
    private val random = Random()

    init {
        for(i in 0 until size){
            chromosomes.add(Chromosome(true, DNA_LENGTH))
        }
    }

    fun fittest() : Chromosome{
        return chromosomes.first()
    }

    fun fitAndSort() {
        chromosomes.stream().forEach {
            it.fitness = context.run(Random(42), PredictiveNextMove(it), object: Callback {
                override fun callback() {}
            }, 0).fitness
        }
        chromosomes.sortByDescending(Chromosome::fitness)
    }

    fun evolve() {
        val newPopulation = mutableListOf<Chromosome>()
        for(i in 0 until 3) {
            newPopulation.add(chromosomes[i].deepCopy())
        }

        while (newPopulation.size < chromosomes.size) {
            val mother = select()
            val father = select()
            mother.mutateNoise(0.1, 0.01)
            father.mutateNoise(0.1, 0.01)
            newPopulation.add(mother.crossoverNoise(father, 0.01))
        }
        chromosomes = newPopulation
    }

    private fun select(): Chromosome {
        val bias =  chromosomes.size * 0.75
        for (i in chromosomes.indices) {
            val chromosome = chromosomes[i]
            if (i > 0) {
                if (random.nextDouble() <= (chromosomes.size - i) / (chromosomes.size.toDouble() + bias)) {
                    return chromosome.deepCopy()
                }
            }
        }
        return chromosomes.first().deepCopy()
    }
}