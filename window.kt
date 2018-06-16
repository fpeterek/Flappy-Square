package game

import java.awt.Dimension
import javax.swing.JFrame

class Window(game: Game, width: Int, height: Int): JFrame("Flappy Square") {

    init {

        add(game)
        addKeyListener(game)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        size = Dimension(width, height)
        isResizable = false
        isVisible = true

    }

}