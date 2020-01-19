package de.htwg.se.blackjack.model.statusComponent

import de.htwg.se.blackjack.controller.GameState
import de.htwg.se.blackjack.model.cardComponent.cardBaseImpl.CardFactory
import de.htwg.se.blackjack.model.deckComponent.deckBaseImpl.Deck

trait StatusInterface {
  def generateDeck()

  def shuffleDeck()

  def draw(): CardFactory

  def handValue(hand: Array[CardFactory]): Int

  def WinLose(spieler: Integer, dealer: Integer): Int
}