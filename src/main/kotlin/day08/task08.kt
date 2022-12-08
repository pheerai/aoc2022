package day08

import println

fun main() {
    val testForest = Forest(rawTestInput08)
    val forest = Forest(        rawInput08)
    testForest.task081().println()
    forest.task081().println()

    testScenicScores(testForest, ForestLocation(1, 2))
    testScenicScores(testForest, ForestLocation(3, 2))
    testForest.task082().println()
    forest.task082().println()
}

fun testScenicScores(forest: Forest, coord: ForestLocation) {
    val testRowX = forest.getXRow(coord.x)
    val testRowY = forest.getYRow(coord.y)
    val testTree = forest.getTree(coord)
    testTree.getViewingDistanceUp(testRowY.reversed()).println()
    testTree.getViewingDistanceLeft(testRowX.reversed()).println()
    testTree.getViewingDistanceRight(testRowX).println()
    testTree.getViewingDistanceDown(testRowY).println()
    testTree.getScenicScore(testRowX, testRowY).println()
}
