package day09

import println


fun main() {
    Rope(rawTestInputDay091.parseMovementOps()).task09().println()
    Rope(rawInputDay09.parseMovementOps()).task09().println()

    Rope(rawTestInputDay091.parseMovementOps(), 10).task09().println()
    Rope(rawTestInputDay092.parseMovementOps(), 10).task09().println()
    Rope(rawInputDay09.parseMovementOps(), 10).task09().println()
}
