package de.longuyen.genetic

import de.longuyen.BIAS
import kotlin.math.exp


class Layer(private var prev: Layer?, size: Int) {
    var output: DoubleArray = DoubleArray(size)
    var parameters: Array<ByteArray>

    init {
        parameters = if (prev != null) Array(size) { ByteArray(prev!!.output.size + 1) } else Array(0) { ByteArray(0) }
    }

    private fun sigmoid(x: Double): Double {
        return 1.0 / (1 + exp(-x / 2.0))
    }

    fun activate() {
        if (prev == null) return
        for (i in parameters.indices) {
            var sum = 0.0
            for (j in 0 until parameters[0].size - 1) {
                sum += parameters[i][j] * prev!!.output[j]
            }
            sum += parameters[i][parameters[0].size - 1] * BIAS
            output[i] = sigmoid(sum)
        }
    }
}