package org.aoc

class DayFactory {
	fun create(puzzle: String): Day?{
		val retrieveStrategy : (String) -> Day?
		if (puzzle.matches(Regex("[1-9][0-9]*")))
			retrieveStrategy = {it: String -> Puzzles.values().find({ x -> x.order.equals(it.toInt())})?.day}
		else if (puzzle.matches(Regex("Day\\s*[1-9][0-9]*")))
			retrieveStrategy = {it: String -> Puzzles.values().find({ x -> x.order == it.replace(Regex("Day\\s*"), "").toInt() })?.day}
		else
			retrieveStrategy = {it: String -> Puzzles.values().find({ x -> x.title == it})?.day}

		return retrieveStrategy.invoke(puzzle)
	}
}