package de.longuyen.core

import de.longuyen.FIELD_HEIGHT
import de.longuyen.FIELD_WIDTH

class Bird {
    var x: Float = FIELD_WIDTH / 3.toFloat()
    var y: Float = FIELD_HEIGHT / 3.toFloat()
    private var velocityX = 0f
    private var velocityY = 0f

    fun update() {
        x += velocityX
        y += velocityY
        velocityY += 0.5f
    }

    fun jump() {
        velocityY = -8f
    }

    fun reset() {
        x = FIELD_WIDTH / 2.toFloat()
        y = FIELD_HEIGHT / 2.toFloat()
        velocityX = 0f
        velocityY = 0f
    }
}