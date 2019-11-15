package de.htwg.se.blackjack.aview

import de.htwg.se.sudoku.util.Observer

class Tui() extends Observer{

  def processInputLine(input: String) = {
    input match {
      case "q" => println("Bye!")
      case "ng" => println("Neu Game created!")
    }
  }

  override def update: Unit = ???
}