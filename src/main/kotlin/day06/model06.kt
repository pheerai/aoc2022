package day06

import CapacityQueue

typealias CommBuffer = CapacityQueue<Char>

sealed interface PackageSearch {
    data class NotFound(val buffer:CommBuffer): PackageSearch
    data class Found(val index: Int): PackageSearch
}

class Comm(private val datastream: String) {
    fun startPosition(packageSize: Int): Int {
        val bufferSize = packageSize - 1
        val foundIndex = datastream.asSequence()
            .runningFoldIndexed(PackageSearch.NotFound(CommBuffer(bufferSize))) { index: Int, acc: PackageSearch, c: Char ->
                when(acc) {
                    is PackageSearch.NotFound -> {
                        if (allItemsUniqueStep(c, acc.buffer, bufferSize)) {
                            PackageSearch.Found(index)
                        } else {
                            acc
                        }
                    }
                    is PackageSearch.Found -> acc
                }
            }
            .dropWhile { it is PackageSearch.NotFound }
            .filterIsInstance<PackageSearch.Found>()
            .firstOrNull()
            ?.index
        return foundIndex?.plus(1) ?: -1
    }

    private fun allItemsUniqueStep(c: Char, buffer: CommBuffer, bufferSize: Int): Boolean {
        if (c !in buffer && !buffer.hasDuplicates() && buffer.size == bufferSize) {
            return true
        } else {
            buffer.enqueue(c)
        }
        return false
    }

    companion object {
        fun CommBuffer.hasDuplicates(): Boolean = this.toSet().size != this.size
    }
}
