package org.aoc

enum class Puzzles(val order: Int, val title: String, val day: Day) {
	DAY1(1, "Chronal Calibration", Day1()),
	DAY2(2, "Inventory Management System", Day2()),
	DAY3(3, "No Matter How You Slice It", Day3()),
	DAY4(4, "Repose Record", Day4()),
	DAY5(5, "Alchemical Reduction", Day5())
}