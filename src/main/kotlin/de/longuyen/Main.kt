package de.longuyen

import de.longuyen.core.Callback
import de.longuyen.core.Context
import de.longuyen.genetic.ChromosomeNextMove
import de.longuyen.genetic.Population
import de.longuyen.gui.GUI
import java.util.*

fun main() {
    val context = Context()
    val gui = GUI(context)
    val population = Population(context, 20)
    population.fitAndSort()
    var max = population.fittest().fitness

    while(true) {
        population.evolve()
        population.fitAndSort()
        if(population.fittest().fitness > max) {
            max = population.fittest().fitness
            println("New best bird: ${population.fittest().fitness}")
            context.run(Random(42), ChromosomeNextMove(population.fittest()), object : Callback {
                override fun callback() {
                    gui.update()
                }
            }, 1000 / 60)
        }
    }
}