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
    private var vx = 0f
    private var vy = 0f
    private var img: Image = ImageIO.read(File("data/bird.png"))

    fun physics() {
        x += vx
        y += vy
        vy += 0.5f
    }

    fun update(g: Graphics) {
        g.color = Color.BLACK
        g.drawImage(img, (x - RAD).roundToInt(), (y - RAD).roundToInt(), 2 * RAD, 2 * RAD, null)
    }

    fun jump() {
        vy = -8f
    }

    fun reset() {
        x = 640 / 2.toFloat()
        y = 640 / 2.toFloat()
        vy = 0f
        vx = vy
    }
}