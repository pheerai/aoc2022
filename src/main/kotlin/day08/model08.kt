package day08

import day08.ForestLocationComponent.ForestLocationX
import day08.ForestLocationComponent.ForestLocationY
import day08.ForestRow.ForestRowX.ForestRowXF
import day08.ForestRow.ForestRowX.ForestRowXR
import day08.ForestRow.ForestRowY.ForestRowYF
import day08.ForestRow.ForestRowY.ForestRowYR

// X: Down, Y: Right (easier to parse from the start)

typealias ForestGrid = List<List<Tree>>

class ForestLocation(val x: ForestLocationX, val y: ForestLocationY) {
    constructor(x: Int, y: Int) : this(ForestLocationX(x), ForestLocationY(y))
}

sealed class ForestLocationComponent(val coord: Int) {
    class ForestLocationX(coord: Int) : ForestLocationComponent(coord)
    class ForestLocationY(coord: Int) : ForestLocationComponent(coord)
}

sealed class ForestRow(val row: List<Tree>) : List<Tree> by row {
    fun setIsVisible() {
        row.fold(-1) { lastVisibleHeight, tree ->
            if (tree.height > lastVisibleHeight) {
                tree.setVisible()
                tree.height
            } else {
                when (tree.visible) {
                    TreeVisibility.UNSET -> {
                        tree.setHidden()
                        lastVisibleHeight
                    }

                    TreeVisibility.VISIBLE -> lastVisibleHeight
                    TreeVisibility.HIDDEN -> lastVisibleHeight
                }
            }
        }
    }

    abstract fun reversed(): ForestRow

    sealed class ForestRowX(row: List<Tree>) : ForestRow(row) {
        class ForestRowXR(row: List<Tree>) : ForestRowX(row) {
            override fun reversed(): ForestRowXF = ForestRowXF(row.reversed())
        }

        class ForestRowXF(row: List<Tree>) : ForestRowX(row) {
            override fun reversed(): ForestRowXR = ForestRowXR(row.reversed())
        }
    }

    sealed class ForestRowY(row: List<Tree>) : ForestRow(row) {
        class ForestRowYR(row: List<Tree>) : ForestRowY(row) {
            override fun reversed(): ForestRowYF = ForestRowYF(row.reversed())
        }

        class ForestRowYF(row: List<Tree>) : ForestRowY(row) {
            override fun reversed(): ForestRowYR = ForestRowYR(row.reversed())
        }
    }
}

enum class TreeVisibility {
    VISIBLE,
    HIDDEN,
    UNSET
}

class Tree(
    val x: ForestLocationX,
    val y: ForestLocationY,
    val height: Int
) {
    var visible: TreeVisibility = TreeVisibility.UNSET
        private set

    private var viewingDistanceDown: Int? = null
    private var viewingDistanceUp: Int? = null
    private var viewingDistanceRight: Int? = null
    private var viewingDistanceLeft: Int? = null

    fun setVisible() {
        visible = TreeVisibility.VISIBLE
    }

    fun setHidden() {
        visible = TreeVisibility.HIDDEN
    }

    fun getScenicScore(rowX: ForestRowXF, rowY: ForestRowYF): Int {
        return getViewingDistanceLeft(rowX.reversed()) * getViewingDistanceRight(rowX) * getViewingDistanceUp(rowY.reversed()) * getViewingDistanceDown(
            rowY
        )
    }

    fun getViewingDistanceDown(rowY: ForestRowYF): Int {
        if (this.viewingDistanceDown == null) {
            this.viewingDistanceDown = calcViewingDistanceDown(rowY)
        }
        return viewingDistanceDown!!
    }

    private fun calcViewingDistance(row: ForestRow): Int {
        val t1 = row.dropWhile { it != this }
        val t2 = t1.drop(1)
        val t3 = t2.takeWhile { this.height > it.height }
        // Handle infinite sight (you don't see a blocking tree)
        return if (t2.size == t3.size) {
            t3.size
        } else {
            t3.size + 1
        }
    }

    private fun calcViewingDistanceDown(rowY: ForestRowYF): Int {
        return if (this.y.coord == rowY.size - 1) {
            0
        } else {
            calcViewingDistance(rowY)
        }
    }

    fun getViewingDistanceUp(rowY: ForestRowYR): Int {
        if (this.viewingDistanceUp == null) {
            this.viewingDistanceUp = calcViewingDistanceUp(rowY)
        }
        return viewingDistanceUp!!
    }

    private fun calcViewingDistanceUp(rowY: ForestRowYR): Int {
        return if (this.y.coord == 0) {
            0
        } else {
            calcViewingDistance(rowY)
        }
    }

    fun getViewingDistanceRight(rowX: ForestRowXF): Int {
        if (this.viewingDistanceRight == null) {
            this.viewingDistanceRight = calcViewingDistanceRight(rowX)
        }
        return viewingDistanceRight!!
    }

    private fun calcViewingDistanceRight(rowX: ForestRowXF): Int {
        return if (this.x.coord == rowX.size - 1) {
            0
        } else {
            calcViewingDistance(rowX)
        }
    }

    fun getViewingDistanceLeft(rowX: ForestRowXR): Int {
        if (this.viewingDistanceLeft == null) {
            this.viewingDistanceLeft = calcViewingDistanceLeft(rowX)
        }
        return viewingDistanceLeft!!
    }

    private fun calcViewingDistanceLeft(rowX: ForestRowXR): Int {
        return if (this.x.coord == 0) {
            0
        } else {
            calcViewingDistance(rowX)
        }
    }
}

class Forest(
    private val trees: ForestGrid
) {
    constructor(input: InputDay08) : this(input.parse())

    fun getXRow(index: ForestLocationX): ForestRowXF = ForestRowXF(trees[index.coord])
    fun getYRow(index: ForestLocationY): ForestRowYF = ForestRowYF(trees.map { it[index.coord] })

    private fun getAllTrees(): Iterable<Tree> = trees.flatten()

    private fun getTree(x: ForestLocationX, y: ForestLocationY): Tree = trees[x.coord][y.coord]
    fun getTree(l: ForestLocation): Tree = getTree(l.x, l.y)

    fun task082(): Int {
        return getAllTrees().map {
            it to (getXRow(it.x) to getYRow(it.y))
        }.maxOfOrNull { (t, rs) -> t.getScenicScore(rs.first, rs.second) } ?: -1
    }

    fun task081(): Int {
        for (x in trees.indices.map { ForestLocationX(it) }) {
            val xRow = getXRow(x)
            xRow.setIsVisible()
            xRow.reversed().setIsVisible()
        }
        for (y in trees.getOrNull(0)?.indices?.map { ForestLocationY(it) } ?: listOf()) {
            val yRow = getYRow(y)
            yRow.setIsVisible()
            yRow.reversed().setIsVisible()
        }

        return getAllTrees().count { it.visible == TreeVisibility.VISIBLE }
    }

    companion object {
        fun InputDay08.parse(): ForestGrid {
            return this.value.lines()
                .mapIndexed { ix, l ->
                    l.toCharArray()
                        .mapIndexed { iy, c ->
                            Tree(ForestLocationX(ix), ForestLocationY(iy), c.digitToInt())
                        }
                }
        }
    }
}
