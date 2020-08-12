package de.longuyen.core

import de.longuyen.*
import java.awt.Rectangle
import java.util.*

data class Result(val steps: Long, var fitness: Double)


class Context() {
    val bird = Bird()
    val pipes = mutableListOf<Rectangle>()

    fun run(random: Random, decisionMaker: DecisionMaker, callback: Callback, sleep: Long = 100): Result {
        var fitness = 0.0
        var steps = 0L
        var lost = false
        while (!lost) {
            callback.callback()
            if (decisionMaker.jump(this)) {
                bird.jump()
            }
            bird.update()
            if (steps % 90L == 0L) {
                val topPipeHeight = random.nextInt(170 - 100 + 1) + 70
                val topPipe = Rectangle(FIELD_WIDTH - PIPE_WIDTH, 0, PIPE_WIDTH, topPipeHeight)

                val bottomPipeHeight = FIELD_HEIGHT - (topPipeHeight + PIPE_SPACE)
                val bottomPipe = Rectangle(FIELD_WIDTH - PIPE_WIDTH, bottomPipeHeight, PIPE_WIDTH, bottomPipeHeight)
                pipes.add(topPipe)
                pipes.add(bottomPipe)
            }

            val obsoletePipes = mutableListOf<Rectangle>()
            for (pipe in pipes) {
                pipe.x -= 3
                if (pipe.x + pipe.width <= 0) {
                    obsoletePipes.add(pipe)
                }
                if (pipe.contains(bird.x.toInt(), bird.y.toInt())) {
                    lost = true
                }
            }
            pipes.removeAll(obsoletePipes)

            fitness++
            steps++

            if (bird.y > FIELD_HEIGHT || bird.y < 0) {
                lost = true
            }
            Thread.sleep(sleep)
        }
        bird.reset()
        pipes.clear()
        return Result(steps, fitness)
    }
}