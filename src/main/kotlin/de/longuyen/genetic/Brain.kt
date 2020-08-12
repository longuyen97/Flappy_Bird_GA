package de.longuyen.genetic

class Brain(stageSizes: IntArray) {
    private var layers: Array<Layer?> = arrayOfNulls(stageSizes.size)

    init {
        var prev: Layer? = null
        for (i in stageSizes.indices) {
            layers[i] = Layer(prev, stageSizes[i])
            prev = layers[i]
        }
    }

    fun loadParameters(rawParameters: ByteArray, symmetrical: Boolean = false) {
        if(symmetrical){
            var idx = 0
            for (s in 1 until layers.size) {
                if (layers[s]!!.parameters.size % 2 == 1) {
                    System.err.println("Symmetrical Net without even sized stages. Bad.")
                    return
                }
                for (i in 0 until layers[s]!!.parameters.size / 2) {
                    for (j in layers[s]!!.parameters[0].indices) {
                        layers[s]!!.parameters[i][j] = rawParameters[idx]
                        layers[s]!!.parameters[layers[s]!!.parameters.size - 1 - i][layers[s]!!.parameters[0].size - 1 - j] =
                            rawParameters[idx++]
                    }
                }
            }
        }else {
            var idx = 0
            for (s in 1 until layers.size) {
                for (element in layers[s]!!.parameters) {
                    for (j in layers[s]!!.parameters[0].indices) {
                        element[j] = rawParameters[idx++]
                    }
                }
            }
        }
    }

    fun forwardPropagation(input: DoubleArray): DoubleArray {
        for (i in input.indices) {
            layers[0]!!.output[i] = input[i]
        }
        for (i in 1 until layers.size) {
            layers[i]!!.activate()
        }

        return  layers[layers.size - 1]!!.output
    }

    companion object {
        fun calculateNumberOfParameters(stageSizes: IntArray, symmetrical: Boolean): Int {
            var sum = 0
            if (stageSizes.size < 2) return 0
            for (i in 1 until stageSizes.size) {
                sum += if (symmetrical) (stageSizes[i] * (stageSizes[i - 1] + 1) + 1) / 2 else stageSizes[i] * (stageSizes[i - 1] + 1)
            }
            return sum
        }
    }
}