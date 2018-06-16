package game

import java.awt.Color
import java.awt.Rectangle

class Square : Rectangle(0, 0, 25, 25) {

    private var force = 0.0

    private var ycoor = 0.0

    private var jumpCooldown = 0

    var windowHeight = 0

    val color: Color
        get() = Color.RED

    val side: Int
        get() = super.width

    private fun updateY() {
        super.y = ycoor.toInt()
    }

    fun reset(x: Int, y: Int) {

        super.x = x
        super.y = y
        ycoor = y.toDouble()
        force = 0.0

    }

    fun update() {

        forceUpdate()

        ycoor -= force.toInt()

        if (jumpCooldown > 0) {
            --jumpCooldown
        }

        checkBoundaries()
        updateY()

    }

    private fun checkBoundaries() {


        if (ycoor < 0) {

            ycoor = 0.0
            force = 0.0

        } else if (ycoor > windowHeight - side) {

            ycoor = windowHeight - side.toDouble()

        }

    }

    private fun forceUpdate() {

        if (force == -7.0) {
            return
        }

        force -= if (force > 0.1) {

            0.1 + (5.0 - force) / 20.0

        } else {

            0.2 + (0 - force) / 20.0

        }

        force = Math.max(-7.0, force)

    }

    fun jump() {

        if (jumpCooldown == 0) {
            force = 5.0
            jumpCooldown = 10
        }
    }

}