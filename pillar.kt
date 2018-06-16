package game

import java.awt.Color
import java.awt.Rectangle
import java.util.*

val pillarDistance = 150

class Pillar(private val windowHeight: Int) {


    var pointAvailable = true

    val color: Color
        get() = Color(40, 200, 30)

    val minGapY
        get() = windowHeight / 8
    val maxGapY
        get() = windowHeight - minGapY - gapSize

    val pillarGap
        get() = (windowHeight / 2.5).toInt()
    val pillarWidth
        get() = 50

    val gapSize
        get() = 125

    var x: Int
        get() = upperRectangle.x
        set(value) {
            upperRectangle.x = value
            lowerRectangle.x = value
        }

    val upperRectangle: Rectangle
    val lowerRectangle: Rectangle

    init {

        val rand = Random()
        val gapPosition = rand.nextInt(maxGapY - minGapY) + minGapY

        upperRectangle = Rectangle(0, 0, pillarWidth, gapPosition)
        lowerRectangle = Rectangle(0, gapPosition + gapSize, pillarWidth, windowHeight - gapPosition - gapSize)

    }


}