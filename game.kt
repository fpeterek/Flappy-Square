package game

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.Color
import java.awt.Graphics
import java.util.*
import javax.swing.JPanel
import javax.swing.Timer

class Game(width: Int, height: Int): JPanel(), KeyListener, ActionListener {

    private val timer = Timer(15, this)

    private val window = Window(this, width, height)

    private val square = Square()

    private val pillars: LinkedList<Pillar> = LinkedList()

    private var score = 0

    private var gameOver = false

    init {

        background = Color(120, 200, 200)

        reset()

    }

    private fun reset() {

        pillars.clear()
        newPillar()
        score = 0
        gameOver = false

        square.reset(100, window.height / 2 - square.side)

        square.windowHeight = height

        window.isResizable = false


    }

    private fun newPillar() {

        val pillar = Pillar(height)
        pillar.x = window.width

        pillars.add(pillar)

    }

    private fun pillarUpdate() {

        val last = pillars.last

        if (last.x < window.width - last.pillarGap) {
            newPillar()
        }

        val first = pillars.first

        if (first.x < 0 - first.pillarWidth) {
            pillars.poll()
        }

        pillars.forEach {
            it.x -= 2
        }

    }

    private fun scoreUpdate() {

        val first = pillars.first

        if (first.x + first.pillarWidth < square.x - 1 && first.pointAvailable) {

            first.pointAvailable = false
            ++score

        }

    }

    private fun checkCollision() {

        if (square.y >= height - square.side) {

            gameOver = true

        }

        for (pillar in pillars) {

            if (square.intersects(pillar.upperRectangle) || square.intersects(pillar.lowerRectangle)) {

                gameOver = true
                break

            }

        }

        if (gameOver) {
            window.isResizable = true
        }

    }

    private fun gameUpdate() {

        if (!gameOver) {

            square.update()
            pillarUpdate()

            scoreUpdate()

            checkCollision()

            repaint()

        }

    }

    override fun paint(g: Graphics?) {

        if (g == null) {
            return
        }

        super.paint(g)

        pillars.forEach { paintPillar(it, g) }

        g.color = Color.BLACK
        g.drawString("Score: $score", window.width - 70, 20)

        g.color = square.color
        g.fillRoundRect(square.x, square.y, square.side, square.side, 10, 10)

        if (gameOver) {

            val origFont = g.font

            val newFont = origFont.deriveFont(30f)

            g.color = Color.BLACK
            g.font = newFont
            val width = g.fontMetrics.stringWidth("Game over")
            g.drawString("Game over", window.width / 2 - width / 2, window.height / 2 - 15)
            g.font = origFont

        }

    }

    private fun paintPillar(p: Pillar, g: Graphics) {

        g.color = p.color
        g.fillRect(p.upperRectangle.x, p.upperRectangle.y, p.upperRectangle.width, p.upperRectangle.height)
        g.fillRect(p.lowerRectangle.x, p.lowerRectangle.y, p.lowerRectangle.width, p.lowerRectangle.height)

    }

    override fun actionPerformed(e: ActionEvent?) {
        gameUpdate()
    }

    override fun keyPressed(e: KeyEvent?) {

        if (e == null) {
            return
        }

        when  {

            e.keyCode == KeyEvent.VK_UP -> square.jump()
            e.keyCode == KeyEvent.VK_ESCAPE -> System.exit(0)
            e.keyCode == KeyEvent.VK_SPACE && gameOver -> reset()

        }

    }

    override fun keyReleased(e: KeyEvent?) {

        if (e == null) {
            return
        }

    }

    override fun keyTyped(e: KeyEvent?) {

        if (e == null) {
            return
        }

    }

    private fun finalize() {
        timer.stop()
    }

    init {
        timer.start()
    }

}