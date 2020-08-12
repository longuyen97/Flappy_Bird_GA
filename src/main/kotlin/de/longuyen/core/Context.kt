package de.longuyen.core

import de.longuyen.FIELD_HEIGHT
import de.longuyen.FIELD_WIDTH
import de.longuyen.PIPE_SPACE
import de.longuyen.PIPE_WIDTH
import java.awt.Rectangle
import java.util.*

data class Result(val steps: Long, var fitness: Double)

class Context() {
    val bird = Bird()
    val pipes = mutableListOf<Pair<Rectangle, Rectangle>>()
    val crossedPipes = mutableListOf<Pair<Rectangle, Rectangle>>()

    fun run(random: Random, decisionMaker: DecisionMaker, callback: Callback, sleep: Long = 100): Result {
        var fitness = 0.0
        var steps = 0L
        var lost = false
        while (!lost) {
            bird.update()
            if (steps % 90L == 0L) {
                val topPipeHeight = random.nextInt(170 - 100 + 1) + 70
                val topPipe = Rectangle(FIELD_WIDTH - PIPE_WIDTH, 0, PIPE_WIDTH, topPipeHeight)

                val bottomPipeHeight = FIELD_HEIGHT - (topPipeHeight + PIPE_SPACE)
                val bottomPipe = Rectangle(FIELD_WIDTH - PIPE_WIDTH, bottomPipeHeight, PIPE_WIDTH, bottomPipeHeight)

                pipes.add(Pair(topPipe, bottomPipe))
            }

            val disappearedPipes = mutableListOf<Pair<Rectangle, Rectangle>>()

            for (pipe in crossedPipes) {
                pipe.first.x -= 3
                pipe.second.x -= 3
                if (pipe.first.x + pipe.first.width <= 0) {
                    disappearedPipes.add(pipe)
                }
            }
            for (pipe in pipes) {
                pipe.first.x -= 3
                pipe.second.x -= 3
                if (pipe.first.x + pipe.first.width <= 0) {
                    disappearedPipes.add(pipe)
                }
                if (pipe.first.x + pipe.first.width < bird.x) {
                    crossedPipes.add(pipe)
                }
                if (pipe.first.contains(bird.x.toInt(), bird.y.toInt()) || pipe.second.contains(bird.x.toInt(), bird.y.toInt())) {
                    lost = true
                }
            }
            crossedPipes.removeAll(disappearedPipes)
            pipes.removeAll(disappearedPipes)
            pipes.removeAll(crossedPipes)

            fitness++
            steps++

            if (bird.y > FIELD_HEIGHT || bird.y < 0) {
                lost = true
            }

            callback.callback()
            if (decisionMaker.jump(this)) {
                bird.jump()
            }
            Thread.sleep(sleep)
        }
        bird.reset()
        pipes.clear()
        return Result(steps, fitness)
    }
}