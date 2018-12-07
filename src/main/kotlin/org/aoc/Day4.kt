package org.aoc

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.HashMap
import java.util.ArrayList
import java.time.Period
import java.time.Duration

class Day4: Day {
	
	override fun run() {
		val chronological = File(Day4::class.java.getResource("4.txt").file).readLines().map {it -> parse(it)}.sortedBy { x -> x.dateTime }
		
		var currentGuard: Int = 0
		var fellAsleepAt: LocalDateTime = LocalDateTime.MIN
	
		val sleptMinutes: MutableMap<Int, MutableList<Int>> = HashMap()
		
		chronological.forEach { x ->
			val regex = """[0-9]+""".toRegex()
			val matchResult = regex.find(x.event)
			if(matchResult != null){
				currentGuard = matchResult.value.toInt()
				if(!sleptMinutes.containsKey(currentGuard)) sleptMinutes.put(currentGuard, ArrayList())
			}else if(x.event.contains("falls asleep")){
				fellAsleepAt = x.dateTime
			}else if(x.event.contains("wakes up")){
				if(fellAsleepAt != LocalDateTime.MIN){
					var min: Int = fellAsleepAt.minute
					while(min < x.dateTime.minute){
						//Not-null managed at guard switch
						sleptMinutes.get(currentGuard)!!.add(min)
						min++
					}
				}
				fellAsleepAt = LocalDateTime.MIN
			}
		}
		
		val guardId = sleptMinutes.maxBy { x->x.value.size }?.key
		val min = sleptMinutes.get(guardId)?.groupBy { x -> x }?.maxBy { x -> x.value.size }?.key
		println("${guardId} slept the most and his preferred minute was ${min}")
		println(min!! * guardId!!)
		
		sleptMinutes.forEach { x ->
			val a = x.value.groupBy { y -> y}.maxBy { y -> y.value.size }
			println()
			println(x.key)
			println(a?.key)
			println(a?.value?.size)
		}
	}
	
	private fun parse(it: String) = object{
		val dateTime: LocalDateTime = LocalDateTime.parse(it.substring(1, it.indexOf(']')), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
		val event: String = it.substring(it.indexOf(']')+2)
	}
}