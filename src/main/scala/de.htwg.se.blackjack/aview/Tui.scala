package de.htwg.se.blackjack.aview

import de.htwg.se.blackjack.controller.controllerComponent.ControllerInterface
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.util.{Observable, Observer}
import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
//import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.DealerStrategy
import de.htwg.se.blackjack.model.cardComponent.cardBaseImpl.CardFactory
import de.htwg.se.blackjack.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.blackjack.model.statusComponent.statusBaseImpl.StatusStrategy
import de.htwg.se.blackjack.model.cardComponent.cardBaseImpl.CardFactory
import de.htwg.se.blackjack.model.playerComponent.playerBaseImpl.Player

import scala.collection.mutable.Stack
import scala.io.StdIn._



class Tui(controller: ControllerInterface) extends Observer {
  //controller.add(this)
  var status = new StatusStrategy()

  // initial cards
  status.generateDeck()
  // shuffle cards
  status.shuffleDeck()


  // Ask Player if he wants to play another round
  def askYesNo(prompt: String): Boolean = {
    while (true) {
      //val user_input = readLine(prompt)
      prompt match {
        case "y" => return true
        case "n" => return false
        case _ => println("You stupid!")
          sys.exit(-1)
      }
    }
    true // to make compiler happy
  }

  // Ask Player if Hit = Take another card or Stand = stand with the current value of cards
  def HitStand(prompt: String): Boolean = {
    while (true) {
      //val user_input = readLine(prompt)
      prompt match {
        case "h" => return true
        case "s" => return false
        case "undo" => controller.undo()
        case "redo" => controller.redo()
        case _ => println("Try Again!")
      }
    }
    true // to make compiler happy
  }

  def processInput(input: String): CardFactory = {
    input match {
      case "h" => controller.draw()
    }
}

  def gameStart(): Int = {

    var player = Player("Player")
    var dealer = Player("Dealer")

    player.addCard(controller.draw())
    //DealerStrategy.takeCard
    dealer.addCard(controller.draw())
    player.addCard(controller.draw())
    dealer.addCard(controller.draw())
    printf("Player:\t\t Your total is %d\n", player.handValue())

    var want = true
    while (want && player.handValue() < 21) {
      println("[h]it or [s]tand?: ")
      var input = readLine()
      if (input.equals("h")) {
        want = true
        player.addCard(controller.draw())
        printf("Player:\t\t Your total is %d\n", player.handValue())
        if (player.handValue() > 21) {
          println("Player:\t\t You Bust! Sorry you lost right away!")
          return -1
        }
      } else if(input.equals("s")) {
        want = false
      } else if(input.equals("undo")) {
        player.takeCardAway()
        println("Taken Card was: " + player.playerstack.top)
        printf("Player:\t\t Your total is %d\n", player.handValue())
      } else if (input.equals("redo")) {
        player.returnCard()
        println("Given Card was: " + player.karte.top)
        printf("Player:\t\t Your total is %d\n", player.handValue())
      } else if (input.equals("save")) {
        controller.save(player, dealer)
      } else if (input.equals("load")) {
        controller.load
      }
    }

//    DealerStrategy.strategy

    while (dealer.handValue() < 17) {
      dealer.addCard(controller.draw())
    }

    printf("Dealer:\t\t The dealer's total is %d\n", dealer.handValue())

    // summary
    println("--------------------------------")
    println("Summary:")
    val player_total = player.handValue()
    val dealer_total = dealer.handValue()
    printf("\nPlayer:\t\t Your total is %d\n", player_total)
    printf("Dealer:\t\t The dealer's total is %d\n", dealer_total)

    status.WinLose(player_total, dealer_total)
  }




  override def update: Boolean = {
   // println(controller.toString)
    true
  }
}
