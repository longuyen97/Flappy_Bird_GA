package de.longuyen

import java.awt.*
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JPanel

class GUI(private var bird: Bird, private var rectangles: ArrayList<Rectangle>) : JPanel() {
    private var pipeHead: Image = ImageIO.read(File("data/pipe.png"))
    private var pipeLength: Image = ImageIO.read(File("data/pipe_part.png"))

    public override fun paintComponent(g: Graphics) {
        g.color = BACKGROUND_COLOR
        g.fillRect(0, 0, WIDTH, HEIGHT)
        bird.paint(g)
        g.color = Color.RED

        for (rectangle in rectangles) {
            val g2d = g as Graphics2D
            g2d.color = Color.GREEN

            val old = g2d.transform
            g2d.translate(rectangle.x + PIPE_W / 2, rectangle.y + PIPE_H / 2)
            if (rectangle.y < HEIGHT / 2) {
                g2d.translate(0, rectangle.height)
                g2d.rotate(Math.PI)
            }
            g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, PIPE_W, PIPE_H, null)
            g2d.drawImage(pipeLength, -PIPE_W / 2, PIPE_H / 2, PIPE_W, rectangle.height, null)
            g2d.transform = old
        }
    }
}