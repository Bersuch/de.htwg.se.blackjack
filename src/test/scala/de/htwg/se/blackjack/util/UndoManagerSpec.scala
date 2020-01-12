package de.htwg.se.blackjack.util
import org.scalatest.{Matchers, WordSpec}

class UndoManagerSpec extends WordSpec with Matchers {
  "An UndoManager" should {
    val undoManager = new UndoManager
    "handle undo/redo if stack is empty correctly" in {
      val command = new incrCommand
      val oldState = command.state
      undoManager.undoStep()
      command.state should be (oldState)
      undoManager.redoStep()
      command.state should be (oldState)
    }
    "have a do, undo and redo" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep()
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }
  }
}
