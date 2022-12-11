package day10

@JvmInline
value class InputDay10(val value: String)

val rawTestInput101 = InputDay10("""noop
addx 3
addx -5""")

val rawTestInput102 = InputDay10("""addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop""")

val rawInput10 = InputDay10("""addx 2
addx 4
noop
noop
addx 17
noop
addx -11
addx -1
addx 4
noop
noop
addx 6
noop
noop
addx -14
addx 19
noop
addx 4
noop
noop
addx 1
addx 4
addx -20
addx 21
addx -38
noop
addx 7
noop
addx 3
noop
addx 22
noop
addx -17
addx 2
addx 3
noop
addx 2
addx 3
noop
addx 2
addx -8
addx 9
addx 2
noop
noop
addx 7
addx 2
addx -27
addx -10
noop
addx 37
addx -34
addx 30
addx -29
addx 9
noop
addx 2
noop
noop
noop
addx 5
addx -4
addx 9
addx -2
addx 7
noop
noop
addx 1
addx 4
addx -1
noop
addx -19
addx -17
noop
addx 1
addx 4
addx 3
addx 11
addx 17
addx -23
addx 2
noop
addx 3
addx 2
addx 3
addx 4
addx -22
noop
addx 27
addx -32
addx 14
addx 21
addx 2
noop
addx -37
noop
addx 31
addx -26
addx 5
addx 2
addx 3
addx -2
addx 2
addx 5
addx 2
addx 3
noop
addx 2
addx 9
addx -8
addx 2
addx 11
addx -4
addx 2
addx -15
addx -22
addx 1
addx 5
noop
noop
noop
noop
noop
addx 4
addx 19
addx -15
addx 1
noop
noop
addx 6
noop
noop
addx 5
addx -1
addx 5
addx -14
addx -13
addx 30
noop
addx 3
noop
noop""")
