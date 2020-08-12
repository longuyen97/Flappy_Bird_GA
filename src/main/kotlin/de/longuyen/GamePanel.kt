package de.longuyen

import java.awt.*
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JPanel

class GamePanel(private val fb: FlappyBird, private var bird: Bird, private var rects: ArrayList<Rectangle>) :
    JPanel() {
    private val scoreFont: Font = Font("Comic Sans MS", Font.BOLD, 18)
    private val pauseFont: Font = Font("Arial", Font.BOLD, 48)
    private var pipeHead: Image = ImageIO.read(File("data/pipe.png"))
    private var pipeLength: Image = ImageIO.read(File("data/pipe_part.png"))

    public override fun paintComponent(g: Graphics) {
        g.color = bg
        g.fillRect(0, 0, WIDTH, HEIGHT)
        bird.update(g)
        g.color = Color.RED
        for (r in rects) {
            val g2d = g as Graphics2D
            g2d.color = Color.GREEN

            val old = g2d.transform
            g2d.translate(r.x + PIPE_W / 2, r.y + PIPE_H / 2)
            if (r.y < HEIGHT / 2) {
                g2d.translate(0, r.height)
                g2d.rotate(Math.PI)
            }
            g2d.drawImage(
                pipeHead,
                -PIPE_W / 2,
                -PIPE_H / 2,
                PIPE_W,
                PIPE_H,
                null
            )
            g2d.drawImage(
                pipeLength,
                -PIPE_W / 2,
                PIPE_H / 2,
                PIPE_W,
                r.height,
                null
            )
            g2d.transform = old
        }
        g.font = scoreFont
        g.color = Color.BLACK
        g.drawString("Score: " + fb.score, 10, 20)
        if (fb.paused()) {
            g.font = pauseFont
            g.color = Color(0, 0, 0, 170)
            g.drawString("PAUSED", WIDTH / 2 - 100, HEIGHT / 2 - 100)
            g.drawString("PRESS SPACE TO BEGIN", WIDTH / 2 - 300, HEIGHT / 2 + 50)
        }
    }
}