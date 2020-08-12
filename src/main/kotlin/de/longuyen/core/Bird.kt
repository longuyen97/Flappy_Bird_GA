package de.longuyen

import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.roundToInt

class Bird {
    var x: Float = WIDTH / 2.toFloat()
    var y: Float = HEIGHT / 2.toFloat()
    private var velocityX = 0f
    private var velocityY = 0f
    private var img: Image = ImageIO.read(File("data/bird.png"))

    fun paint(g: Graphics) {
        g.color = Color.BLACK
        g.drawImage(img, (x - RAD).roundToInt(), (y - RAD).roundToInt(), 2 * RAD, 2 * RAD, null)
    }

    fun update() {
        x += velocityX
        y += velocityY
        velocityY += 0.5f
    }

    fun jump() {
        velocityY = -8f
    }

    fun reset() {
        x = WIDTH / 2.toFloat()
        y = WIDTH / 2.toFloat()
        velocityY = 0f
        velocityX = 0f
    }
}