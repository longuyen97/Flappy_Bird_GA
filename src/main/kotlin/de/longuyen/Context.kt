package de.longuyen

import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel


class Context : KeyListener {
    private var bird: Bird = Bird()
    private var frame: JFrame = JFrame("Flappy Bird")
    private var rects: ArrayList<Rectangle> = ArrayList()
    private var panel: JPanel
    private var scroll = 0

    init {
        panel = GUI(bird, rects)
        frame.add(panel)
        frame.setSize(WIDTH, HEIGHT)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true
        frame.addKeyListener(this)
    }

    fun run() : Double{
        var game = true
        var fitness = 0.0
        while (game){
            panel.repaint()
            bird.update()
            if (scroll % 90 == 0) {
                val r = Rectangle(WIDTH, 0, PIPE_W, (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt())
                val h2 = (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt()
                val r2 = Rectangle(WIDTH, HEIGHT - h2, PIPE_W, h2)
                rects.add(r)
                rects.add(r2)
            }
            val toRemove = ArrayList<Rectangle>()
            for (rect in rects) {
                rect.x -= 3
                if (rect.x + rect.width <= 0) {
                    toRemove.add(rect)
                }
                if (rect.contains(bird.x.toInt(), bird.y.toInt())) {
                    game = false
                }
            }
            rects.removeAll(toRemove)
            fitness++
            scroll++
            if (bird.y > HEIGHT || bird.y + RAD < 0) {
                game = false
            }
            if (!game) {
                rects.clear()
                bird.reset()
                scroll = 0
            }
            Thread.sleep((1000 / FPS).toLong())
        }
        return fitness
    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_SPACE) {
            bird.jump()
        }
    }

    override fun keyReleased(e: KeyEvent) {}

    override fun keyTyped(e: KeyEvent) {}
}