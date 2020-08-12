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

class GUI(private val context: Context) : JFrame(), KeyListener {
    private val image : BufferedImage = BufferedImage(FIELD_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_RGB)
    private val canvas: Graphics = image.graphics
    private var bird = ImageIO.read(File("data/bird.png"))

    private val panel : JPanel = object: JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(image, 0, 0, FIELD_WIDTH, FIELD_HEIGHT, this)
        }
    }

    init {
        canvas.color = Color.WHITE
        canvas.fillRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT)
        add(panel)
        setSize(FIELD_WIDTH, FIELD_HEIGHT)
        isResizable = false
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
        addKeyListener(this)
        panel.repaint()
        panel.revalidate()
    }

    fun update(){
        canvas.color = Color.WHITE
        canvas.fillRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT)

        canvas.color = Color.BLACK
        canvas.drawImage(bird, (context.bird.x - RAD).roundToInt(), (context.bird.y - RAD).roundToInt(), 2 * RAD, 2 * RAD, null)
        canvas.color = Color.BLUE
        for (pipe in context.pipes) {
            canvas.fillRect(pipe.first.x, pipe.first.y, pipe.first.width, pipe.first.height)
            canvas.fillRect(pipe.second.x, pipe.second.y, pipe.second.width, pipe.second.height)
        }
        for (pipe in context.crossedPipes) {
            canvas.fillRect(pipe.first.x, pipe.first.y, pipe.first.width, pipe.first.height)
            canvas.fillRect(pipe.second.x, pipe.second.y, pipe.second.width, pipe.second.height)
        }

        canvas.color = Color.RED
        if(context.pipes.isNotEmpty()) {
            canvas.drawLine(context.bird.x.toInt(), context.bird.y.toInt(), context.pipes[0].first.x, context.pipes[0].first.y + context.pipes[0].first.height)
            canvas.drawLine(context.bird.x.toInt(), context.bird.y.toInt(), context.pipes[0].second.x, context.pipes[0].second.y)
        }
        canvas.drawLine(context.bird.x.toInt(), context.bird.y.toInt(), context.bird.x.toInt(), 0)
        canvas.drawLine(context.bird.x.toInt(), context.bird.y.toInt(), context.bird.x.toInt(), FIELD_HEIGHT)
        canvas.drawLine(0, context.bird.y.toInt(), FIELD_WIDTH, context.bird.y.toInt())
        panel.repaint()
    }

    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyPressed(p0: KeyEvent?) {
        if(p0 != null){
            if(p0.keyCode == KeyEvent.VK_SPACE){
                context.bird.jump()
            }
        }}

    override fun keyReleased(p0: KeyEvent?) {}
}