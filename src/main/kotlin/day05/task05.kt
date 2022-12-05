package day05

import println

fun main() {
    Task05(testInitialState05, rawTestOps05) {
        CraneOperationTask051(it)
    }.performTask().println()
    Task05(initialState05, rawOps05) {
    CraneOperationTask051(it)
    }.performTask().println()
    Task05(testInitialState05, rawTestOps05){
        CraneOperationTask052(it)
    }.performTask().println()
    Task05(initialState05, rawOps05) {
        CraneOperationTask052(it)
    }.performTask().println()
}
