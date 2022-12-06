package day06

class Comm(private val datastream: String) {
    fun startPosition(packageSize: Int): Int {
        val foundIndex = datastream.asSequence()
            .windowed(packageSize)
            .mapIndexed { i, cs ->
                if (cs.allItemsUnique()) i else null
            }
            .filterNotNull()
            .firstOrNull()
        return foundIndex?.plus(packageSize) ?: -1
    }

    private fun List<Char>.allItemsUnique(): Boolean {
        val tArray = arrayListOf<Char>()
        for (char in this) {
            if (char in tArray) {
                return false
            } else {
                tArray.add(char)
            }
        }
        return true
    }
}
