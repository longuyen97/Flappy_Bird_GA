package de.longuyen

import java.awt.Rectangle
import java.util.*


class Context () {
    val bird: Bird = Bird()
    val rectangles: ArrayList<Rectangle> = ArrayList()

    fun run( callback: Callback) : Double{
        var fitness = 0.0
        var steps = 0L
        while (true){
            bird.update()
            callback.callback()
            if (steps % 90L == 0L) {
                val topPipe = Rectangle(WIDTH, 0, PIPE_W, (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt())
                val h2 = (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt()
                val bottomPipe = Rectangle(WIDTH, HEIGHT - h2, PIPE_W, h2)
                rectangles.add(topPipe)
                rectangles.add(bottomPipe)
            }
            val toRemove = ArrayList<Rectangle>()
            for (rect in rectangles) {
                rect.x -= 3
                if (rect.x + rect.width <= 0) {
                    toRemove.add(rect)
                }
                if (rect.contains(bird.x.toInt(), bird.y.toInt())) {
                    break
                }
            }
            rectangles.removeAll(toRemove)

            fitness++
            steps++
            if (bird.y > HEIGHT || bird.y + RAD < 0) {
                break
            }
            Thread.sleep((1000 / FPS).toLong())
        }
        rectangles.clear()
        bird.reset()
        return fitness
    }
}