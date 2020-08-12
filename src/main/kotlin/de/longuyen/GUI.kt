package de.longuyen

import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel

class Panel(private val context: Context) : JPanel(){
    private var pipe: Image = ImageIO.read(File("data/pipe.png"))

    public override fun paintComponent(graphics: Graphics) {
        println("Paint component")
        graphics.color = BACKGROUND_COLOR
        graphics.fillRect(0, 0, JFrame.WIDTH, JFrame.HEIGHT)
        context.bird.paint(graphics)
        graphics.color = Color.RED

        for (rectangle in context.rectangles) {
            val graphics2D = graphics as Graphics2D
            graphics2D.color = Color.GREEN

            val old = graphics2D.transform
            graphics2D.translate(rectangle.x + PIPE_W / 2, rectangle.y + PIPE_H / 2)
            if (rectangle.y < JFrame.HEIGHT / 2) {
                graphics2D.translate(0, rectangle.height)
                graphics2D.rotate(Math.PI)
            }
            graphics2D.drawImage(pipe, -PIPE_W / 2, PIPE_H / 2, PIPE_W, rectangle.height, null)
            graphics2D.transform = old
        }
    }
}

class GUI(private val context: Context) : JFrame("Flappy Bird"), Callback {
    private val panel: JPanel = Panel(context)

    init {
        setSize(WIDTH, HEIGHT)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
        add(panel)
        addKeyListener(object : KeyListener {
            override fun keyTyped(p0: KeyEvent?) {
                if (p0 != null && p0.keyCode == KeyEvent.VK_SPACE) {
                    context.bird.jump()
                }
            }
            override fun keyPressed(p0: KeyEvent?) {
            }
            override fun keyReleased(p0: KeyEvent?) {
            }
        })
        context.run(this)
    }

    override fun callback() {
        panel.repaint()
    }
}