package org.aoc

import java.io.File
import com.andreapivetta.kolor.Kolor
import com.andreapivetta.kolor.Color

class Day2 : Day{
	private var condition2Count: Int = 0;
	private var condition3Count: Int = 0;
	
	override fun run() {
		println("Phuff! Let's work on these box ids")
		println()
		println("Calculating checksum...")
		println()
		File(Day2::class.java.getResource("2.txt").file).forEachLine {it -> check(it)}
		val checksum: Int = condition2Count * condition3Count
		
		print("Box id checksum is ")
		println(Kolor.foreground("${checksum}", Color.YELLOW))
		
		println()
		println("Now let's find the common part of similar ids")
		println()
		println("Searching...")
		println()
		print("Id found: ")
		println(Kolor.foreground("${findId()}", Color.YELLOW))
		
	}
	
	private fun check(it: String){
		if (it.filter { x -> it.count { y -> y==x } == 2 }.isNotEmpty()) condition2Count++
		if (it.filter { x -> it.count { y -> y==x } == 3 }.isNotEmpty()) condition3Count++
	}
	
	private fun findId(): String{
		val sorted = File(Day2::class.java.getResource("2.txt").file).readLines().sorted()
		for(i in 1..sorted.size-1){
			if(simplifiedLevenshtein(sorted.get(i), sorted.get(i-1))==1){
				return commonParts(sorted.get(i), sorted.get(i-1))
			}
		}
		return ""
	}
	
	private fun commonParts(a: String, b: String): String{
		var result = ""
		for(i in 0..a.length-1) if (a.get(i) == b.get(i)) result += a.get(i)
		return result 
	}
	
	private fun simplifiedLevenshtein(a: String, b: String): Int{
		if(a.length != b.length) return -1
		var result: Int = 0;
		for(i in 0..a.length-1) if (a.get(i) != b.get(i)) result++
		return result
	}
}