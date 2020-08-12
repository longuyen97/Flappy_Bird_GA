package de.longuyen.genetic

import java.util.*
import kotlin.math.floor


class Chromosome(random: Boolean, private val size: Int) {
    private var random = Random()
    var data: ByteArray = ByteArray(size)
    var fitness = 0.0

    init {
        for (i in data.indices) {
            data[i] = if (random) floor(Math.random() * 256.0).toByte() else 0
        }
    }

    fun deepCopy() : Chromosome{
        val ret = Chromosome(true, size)
        ret.data = data.copyOf(data.size)
        return ret
    }

    fun crossoverNoise(other: Chromosome, mutationprob: Double): Chromosome {
        val newdna = Chromosome(false, data.size)
        val numswaps = data.size / 10
        val swaps = IntArray(numswaps + 1)
        for (i in 0 until swaps.size - 1) {
            swaps[i] = floor(Math.random() * data.size).toInt()
        }
        swaps[numswaps] = data.size //save last
        Arrays.sort(swaps)
        var swapidx = 0
        var that = true
        for (i in data.indices) {
            if (i >= swaps[swapidx]) {
                swapidx++
                that = !that
            }
            var d: Byte = 0
            d = if (that) {
                data[i]
            } else {
                other.data[i]
            }
            d = (d + ((random.nextGaussian() * mutationprob * 256).toByte())).toByte()
            newdna.data[i] = d
        }
        return newdna
    }

    fun mutateNoise(prob: Double, mag: Double) {
        for (i in data.indices) {
            if (Math.random() < prob) data[i] = (data[i] + (random.nextGaussian() * mag * 256).toByte()).toByte()
        }
    }
}