package day07

import head

sealed interface Command {
    class LS(val output: List<String>) : Command {
        fun updateDirContent(dir: Node.DirNode) {
            output
                .map { it.split(" ").filter { s -> s.isNotBlank() } }
                .filter { it.size == 2 }
                .forEach {
                    when {
                        it[0] == "dir" -> dir.mkdirOrCd(it[1])
                        else -> dir.touch(it[1], it[0].toLong())
                    }
                }
        }

    }
    class CD(val path: String) : Command {
        fun performCd(currentDir: Node.DirNode, localRoot: Node.ROOT): Node.DirNode {
            return when (this.path) {
                "/" -> localRoot
                ".." -> currentDir.parent ?: localRoot
                else -> currentDir.mkdirOrCd(path)
            }
        }
    }

    companion object {
        fun parse(commandBlock: String): Command {
            if (commandBlock.isBlank()) error("Invalid command Block: empty)")

            val lines = commandBlock.lines()
            val (cli, out) = lines.filter { it.isNotBlank() }
                .head()
            val argv = cli?.split(" ")
                ?.filter { it.isNotBlank() }
                ?.map { it.trim() }
                ?: listOf()

            return when(argv[0]) {
                "ls" -> LS(out)
                "cd" -> CD(argv[1])
                else -> error("Parser error: ${commandBlock}")
            }
        }

        fun parseFullOutput(input: InputDay07) = input.value
            .split("$")
            .filter { it.isNotBlank() }
            .map { parse(it) }
    }
}

sealed class Node(val name: String, val parent: DirNode?) {
    abstract val nodeSize: Long

    sealed class DirNode(
        name: String,
        parent: DirNode?,
        val subDirs: MutableCollection<DirNode> = mutableListOf(),
        val files: MutableCollection<File> = mutableListOf()
    ) : Node(name, parent) {
        override val nodeSize: Long by lazy { files.sumOf { it.nodeSize }  + subDirs.sumOf { it.nodeSize }}

        fun mkdir(name: String): DirNode {
            val newDir = Dir(name, this)
            this.subDirs.add(newDir)
            return newDir
        }

        fun mkdirOrCd(name: String): DirNode {
            return this.subDirs.firstOrNull { it.name == name } ?: mkdir(name)
        }

        fun touch(name: String, size: Long): File {
            val newFile = File(name, this, size)
            this.files.add(newFile)
            return newFile
        }

        fun asSequence(): Sequence<DirNode> {
            return sequence {
                yieldAll(subDirs.flatMap { it.asSequence() })
                yield(this@DirNode)
            }
        }
    }

    class ROOT : DirNode("<root>", null)
    class Dir(name: String, parent: DirNode) : DirNode(name, parent)

    class File(name: String, parent: DirNode, fileSize: Long) : Node(name, parent) {
        override val nodeSize = fileSize
    }
}

class Shell() {
    private val localRoot = Node.ROOT()
    private var currentDir: Node.DirNode = localRoot

    fun getTask071(): Long = localRoot.asSequence()
        .map { it.name to it.nodeSize }
        .filter { it.second <= 100_000 }
        .sumOf { it.second }

    fun getTask072(): Long {
        val freeSpace = 70_000_000 - localRoot.nodeSize
        val spaceToFree = 30_000_000 - freeSpace
        return localRoot.asSequence()
            .map { it.nodeSize }
            .maxBy { it >= spaceToFree }
    }

    fun perform(cmd: Command) {
        when (cmd) {
            is Command.LS ->                 cmd.updateDirContent(currentDir)
            is Command.CD -> {
                currentDir = cmd.performCd(currentDir, localRoot)
            }
        }
    }
}
