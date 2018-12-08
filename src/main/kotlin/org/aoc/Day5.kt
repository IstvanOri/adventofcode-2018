package org.aoc

import java.io.File
import com.andreapivetta.kolor.Kolor
import com.andreapivetta.kolor.Color

class Day5: Day{
	
	override fun run() {
		println("Here's the material. It's shrinking is not the best, but I can reduce this material...")
		println()
		println("Reducing...")
		println()
		
		val input = File(Day5::class.java.getResource("5.txt").file).readText()
		
		print("The new length is ")
		println(Kolor.foreground("${fullyReact(input).length}", Color.YELLOW))
		println()
		println("If I eliminate a single block type, I can do better...")
		println()
		
		var min = Int.MAX_VALUE
		for(i in 'a'..'z'){
			var res = fullyReact(input.filter {x -> x != i && x != i.toUpperCase()})
			if(res.length<min) min=res.length
		}
		print("The new length is ")
		println(Kolor.foreground("${min}", Color.YELLOW))
		
	}
	
	private fun fullyReact(input: String): String{
		var res = input
		var lastSize = Int.MAX_VALUE
		while(res.length < lastSize){
			lastSize = res.length
			res = res.fold("") {acc,x -> if(acc.length > 0 && acc.last() != x && acc.last().toLowerCase() == x.toLowerCase()) acc.substring(0, acc.length-1) else "${acc}${x}" }
		}
		return res
	}
}