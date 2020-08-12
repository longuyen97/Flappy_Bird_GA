package de.longuyen

import de.longuyen.core.Callback
import de.longuyen.core.Context
import de.longuyen.genetic.PredictiveNextMove
import de.longuyen.genetic.Population
import de.longuyen.gui.GUI
import java.util.*

fun main() {
    val context = Context()
    val gui = GUI(context)
    val population = Population(context, 20)
    population.fitAndSort()
    var max = population.fittest().fitness
    var generation = 0
    while(true) {
        population.evolve()
        population.fitAndSort()
        if(population.fittest().fitness > max) {
            generation += 1
            max = population.fittest().fitness
            context.run(Random(42), PredictiveNextMove(population.fittest()), object : Callback {
                override fun callback() {
                    gui.update(generation, max)
                }
            }, 1000 / 60)
        }
    }
}