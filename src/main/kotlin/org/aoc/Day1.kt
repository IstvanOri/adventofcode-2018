package org.aoc

import java.io.File
import java.util.ArrayList
import java.util.LinkedList
import java.util.TreeSet
import com.andreapivetta.kolor.Kolor
import com.andreapivetta.kolor.Color

class Day1() : Day{
	
	private var currentFrequency: Int = 0
	private var firstDuplicate: Int? = null;
	private var firstRun: Boolean = true;
	private var frequencyHistory: MutableSet<Int> = TreeSet<Int>()
	
	override fun run() {
		println("Yeaks! Device Error: \"Device must be calibrated before first use. Frequency drift detected. Cannot maintain destination lock.\"")
		println()
		println("Calibrating...")
		println()
		while(firstDuplicate == null){
			File(Day1::class.java.getResource("1.txt").file).forEachLine {it -> handleLine(it)}
			if(firstRun){
				firstRun = false
				print("Chronal Calibration completed. The current frequency is ")
				println(Kolor.foreground("${currentFrequency}", Color.YELLOW))
			}
		}
		
		println("Hmmmm... The device runs the sequence over and over...")
		print("It reached some frequencies for multiple times. The first such frequency was ")
		println(Kolor.foreground("${firstDuplicate}", Color.YELLOW))
	}
	
	private fun checkDuplicate() {
		if(frequencyHistory.contains(currentFrequency) && firstDuplicate == null) {
			firstDuplicate = currentFrequency
		}
	}
	
	private fun handleLine(it: String){
		checkDuplicate()
		frequencyHistory.add(currentFrequency)
		currentFrequency += it.toInt()
	}
	
}