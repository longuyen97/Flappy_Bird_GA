package de.longuyen.gui

import de.longuyen.FIELD_HEIGHT
import de.longuyen.FIELD_WIDTH
import de.longuyen.RAD
import de.longuyen.core.Context
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.roundToInt
import java.awt.Graphics2D

class GUI(private val context: Context) : JFrame(), KeyListener {
    private val image: BufferedImage = BufferedImage(FIELD_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_RGB)
    private val canvas: Graphics = image.graphics
    private val bird = ImageIO.read(File("data/bird.png"))
    private val background = ImageIO.read(File("data/background.png"))
    private  var pipeBody = ImageIO.read(File("data/pipe.png"))
    private val panel: JPanel = object : JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(image, 0, 0, FIELD_WIDTH, FIELD_HEIGHT, this)
        }
    }
    val PIPE_W = 50
    val PIPE_H = 30

    init {
        canvas.color = Color.WHITE
        canvas.fillRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT)
        add(panel)
        setSize(FIELD_WIDTH, FIELD_HEIGHT)
        isResizable = false
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        addKeyListener(this)
        panel.repaint()
        panel.revalidate()
    }

    fun update(generation: Int, fitness: Double) {
        canvas.color = Color.WHITE
        canvas.fillRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT)

        canvas.color = Color.BLACK
        canvas.drawImage(background, 0, 0, FIELD_WIDTH, FIELD_HEIGHT, null)
        canvas.drawImage(
            bird,
            (context.bird.x - RAD).roundToInt(),
            (context.bird.y - RAD).roundToInt(),
            2 * RAD,
            2 * RAD,
            null
        )

        val g2d = canvas as Graphics2D
        for (pipe in context.pipes) {
            val old = g2d.transform
            g2d.translate(pipe.first.x + PIPE_W / 2, pipe.first.y + PIPE_H / 2)
            g2d.translate(0, pipe.first.height)
            g2d.rotate(Math.PI)
            g2d.drawImage(pipeBody, -PIPE_W / 2, PIPE_H / 2, PIPE_W, pipe.first.height, null)
            g2d.transform = old

            g2d.translate(pipe.second.x + PIPE_W / 2, pipe.second.y )
            g2d.translate(0, pipe.second.height)
            g2d.rotate(Math.PI)
            g2d.drawImage(pipeBody, -PIPE_W / 2, PIPE_H / 2, PIPE_W, pipe.second.height, null)
            g2d.transform = old
        }
        for (pipe in context.crossedPipes) {
            val old = g2d.transform
            g2d.translate(pipe.first.x + PIPE_W / 2, pipe.first.y + PIPE_H / 2)
            g2d.translate(0, pipe.first.height)
            g2d.rotate(Math.PI)
            g2d.drawImage(pipeBody, -PIPE_W / 2, PIPE_H / 2, PIPE_W, pipe.first.height, null)

            g2d.transform = old
            g2d.translate(pipe.second.x + PIPE_W / 2, pipe.second.y )
            g2d.translate(0, pipe.second.height)
            g2d.rotate(Math.PI)
            g2d.drawImage(pipeBody, -PIPE_W / 2, PIPE_H / 2, PIPE_W, pipe.second.height, null)
            g2d.transform = old
        }

        /*canvas.color = Color.RED
        if (context.pipes.isNotEmpty()) {
            canvas.drawLine(context.bird.x.toInt(),
                context.bird.y.toInt(),
                context.pipes[0].first.x,
                context.pipes[0].first.y + context.pipes[0].first.height
            )
            canvas.drawLine(
                context.bird.x.toInt(),
                context.bird.y.toInt(),
                context.pipes[0].second.x,
                context.pipes[0].second.y
            )
        }
        canvas.drawLine(context.bird.x.toInt(), context.bird.y.toInt(), context.bird.x.toInt(), FIELD_HEIGHT)
        */


        canvas.color = Color.BLACK
        canvas.drawString("Generation $generation", 5, 20)
        canvas.drawString("Fitness $fitness", 5, 35)

       /* val encoded = context.encode()
        canvas.drawString("Bird's velocity: ${encoded.velocity}", context.bird.x.toInt(), (context.bird.y + 30).toInt())
        canvas.drawString("Bird's position: ${encoded.birdX}, ${encoded.birdY}",context.bird.x.toInt(), (context.bird.y + 40).toInt())
        canvas.drawString("Top pipe's position: ${encoded.topPipeX}, ${encoded.topPipeY}", context.bird.x.toInt(), (context.bird.y + 50).toInt())
        canvas.drawString("Bottom pipe's position: ${encoded.bottomPipeX}, ${encoded.bottomPipeY}", context.bird.x.toInt(), (context.bird.y + 60).toInt())
        canvas.drawString("Top pipe's distance: ${encoded.topPipeDistance}",context.bird.x.toInt(), (context.bird.y + 70).toInt())
        canvas.drawString("Bottom pipe's distance: ${encoded.bottomPipeDistance}", context.bird.x.toInt(), (context.bird.y + 80).toInt())*/

        panel.repaint()
    }

    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyPressed(p0: KeyEvent?) {
        if (p0 != null) {
            if (p0.keyCode == KeyEvent.VK_SPACE) {
                context.bird.jump()
            }
        }
    }

    override fun keyReleased(p0: KeyEvent?) {}
}