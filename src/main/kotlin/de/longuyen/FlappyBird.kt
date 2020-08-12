package de.longuyen

import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.Timer


class FlappyBird : ActionListener, KeyListener {
    private var bird: Bird = Bird()
    private var frame: JFrame = JFrame("Flappy Bird")
    private var rects: ArrayList<Rectangle> = ArrayList()
    private var panel: JPanel
    var score = 0
    private var scroll = 0
    private var timer: Timer
    private var paused = false

    init {
        panel = GamePanel(this, bird, rects)
        frame.add(panel)
        frame.setSize(WIDTH, HEIGHT)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true
        frame.addKeyListener(this)
        paused = true
        timer = Timer(1000 / FPS, this)
        timer.start()
    }

    override fun actionPerformed(e: ActionEvent) {
        panel.repaint()
        if (!paused) {
            bird.physics()
            if (scroll % 90 == 0) {
                val r = Rectangle(WIDTH, 0, PIPE_W, (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt())
                val h2 = (Math.random() * HEIGHT / 5f + 0.2f * HEIGHT).toInt()
                val r2 = Rectangle(WIDTH, HEIGHT - h2, PIPE_W, h2)
                rects.add(r)
                rects.add(r2)
            }
            val toRemove = ArrayList<Rectangle>()
            var game = true
            for (rect in rects) {
                rect.x -= 3
                if (rect.x + rect.width <= 0) {
                    toRemove.add(rect)
                }
                if (rect.contains(bird.x.toInt(), bird.y.toInt())) {
                    JOptionPane.showMessageDialog(frame, "You lose!\nYour score was: $score.")
                    game = false
                }
            }
            rects.removeAll(toRemove)
            score++
            scroll++
            if (bird.y > HEIGHT || bird.y + RAD < 0) {
                game = false
            }
            if (!game) {
                rects.clear()
                bird.reset()
                score = 0
                scroll = 0
                paused = true
            }
        }
    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_UP) {
            bird.jump()
        } else if (e.keyCode == KeyEvent.VK_SPACE) {
            paused = false
        }
    }

    override fun keyReleased(e: KeyEvent) {}

    override fun keyTyped(e: KeyEvent) {}

    fun paused(): Boolean {
        return paused
    }
}