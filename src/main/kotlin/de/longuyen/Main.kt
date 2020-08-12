package de.longuyen

import de.longuyen.core.Callback
import de.longuyen.core.Context
import de.longuyen.core.DecisionMaker
import de.longuyen.gui.GUI
import java.util.*

fun main() {
    val context = Context()
    val gui = GUI(context)
    while(true) {
        context.run(Random(42), object : DecisionMaker {
            override fun jump(context: Context): Boolean {
                return false
            }
        }, object : Callback {
            override fun callback() {
                gui.update()
            }
        }, 1000 / 60)
    }
}