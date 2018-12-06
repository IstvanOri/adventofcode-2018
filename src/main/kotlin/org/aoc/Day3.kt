package org.aoc

import java.io.File
import java.util.ArrayList
import com.andreapivetta.kolor.Kolor
import com.andreapivetta.kolor.Color

class Day3 : Day{

	override fun run() {
		println("These lil' firecrackers aren't the brightest candles on the cake.")
		print("They've claimed the parts and ")
		
		val parsed = File(Day1::class.java.getResource("3.txt").file).readLines().map {it -> parse(it)}
		
		val row: Int = parsed.map { x -> x.p[0] + x.size[0] }.max()!!
		val column: Int = parsed.map { x -> x.p[1] + x.size[1] }.max()!!
		
		val fabric = Array(row, {IntArray(column)})
		
		var uniques: MutableList<Int> = ArrayList()
		parsed.forEach {
			var collided = false
			for(i in it.p[0]..it.p[0]+it.size[0]-1)
				for(j in it.p[1]..it.p[1]+it.size[1]-1)
					if(fabric[i][j] == 0){
						fabric[i][j] = it.id
					}else{
						uniques.remove(fabric[i][j])
						fabric[i][j] = -1
						collided = true
					}
			if(!collided)
				uniques.add(it.id)
		}
		
		val overlap = fabric.map {x -> x.map {c -> if(c == -1) 1 else 0 }.sum()}.sum()
		print(Kolor.foreground("${overlap}", Color.YELLOW))
		println(" squere bananas of claimed fabric were overlapping.")
		println()
		print("Oh! Wait! There is a snowflake who claimed a part that is not overlapping, its id is ")
		println(Kolor.foreground("${uniques.get(0)}", Color.YELLOW))
	}
	
	private fun parse(it: String) = object{
		val id: Int = it.substring(1, it.indexOf('@', 0, false)).trim().toInt()
		val p: Array<Int> = it.substring(it.indexOf('@', 0, false)+1, it.indexOf(':', 0, false)).split(',').map { x -> x.trim().toInt() }.toTypedArray()
		val size: Array<Int> = it.substring(it.indexOf(':', 0, false)+1).split('x').map { x -> x.trim().toInt() }.toTypedArray()
	}
	
}