package org.aoc

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.HashMap
import java.util.ArrayList
import java.time.Period
import java.time.Duration
import com.andreapivetta.kolor.Kolor
import com.andreapivetta.kolor.Color

class Day4: Day {
	val strategy1 = { it: Map<Int, MutableList<Int>> ->
		val guardId = it.maxBy { x->x.value.size }?.key
		val min = it.get(guardId)?.groupBy { x -> x }?.maxBy { x -> x.value.size }?.key
		println("My first strategy is to find the most sleepy guard and which minute was they asleep for the most. And I found that Guard${guardId} was the most sleep especially at 00:${min}")
		min!! * guardId!!
	}
	
	val strategy2 = { it: Map<Int, MutableList<Int>> ->
		var maxGuard = 0
		var maxVal = Int.MIN_VALUE
		var maxMinute = 0
		it.forEach { x ->
			val a = x.value.groupBy { y -> y}.maxBy { y -> y.value.size }
			if(a != null && maxVal < a.value.size){
				maxVal = a.value.size
				maxMinute = a.key
				maxGuard = x.key
			}
		}
		println("My second strategy is to find of all guards, which guard is most frequently asleep on the same minute. And I found that Guard${maxGuard} was the most sleep especially at 00:${maxMinute}")
		maxMinute*maxGuard
	}
	
	override fun run() {
		
		println("I found a bunch of notes about the guards and when they fell asleep. Maybe I found something useful there.")
		println()
		println("Processing...")
		println()
		
		val sleepTimetable = creatSleepTimetable()
				
		val a = getSolution(sleepTimetable, strategy1)
		print("I write the 'hash' of the solution into my notebook: ")
		println(Kolor.foreground("${a}", Color.YELLOW))
		println()
		val b = getSolution(sleepTimetable, strategy2)
		print("I write the 'hash' of the solution into my notebook: ")
		println(Kolor.foreground("${b}", Color.YELLOW))
	}
	
	private fun getSolution(sleepTimetable: Map<Int, MutableList<Int>>, strategy: (Map<Int, MutableList<Int>>) -> Int): Int{
		return strategy.invoke(sleepTimetable)
	}
	
	private fun parse(it: String) = object{
		val dateTime: LocalDateTime = LocalDateTime.parse(it.substring(1, it.indexOf(']')), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
		val event: String = it.substring(it.indexOf(']')+2)
	}
	
	private fun creatSleepTimetable(): Map<Int, MutableList<Int>>{
		val chronological = File(Day4::class.java.getResource("4.txt").file).readLines().map {it -> parse(it)}.sortedBy { x -> x.dateTime }
		
		val sleptMinutes: MutableMap<Int, MutableList<Int>> = HashMap()
		
		var currentGuard: Int = 0
		var fellAsleepAt: LocalDateTime = LocalDateTime.MIN
		
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
		
		return sleptMinutes
	}
}