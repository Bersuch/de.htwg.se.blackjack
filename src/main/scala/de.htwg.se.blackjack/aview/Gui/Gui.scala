package de.htwg.se.blackjack.aview.Gui

import de.htwg.se.blackjack.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.blackjack.model.cardComponent.cardBaseImpl.CardFactory
import de.htwg.se.blackjack.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.blackjack.model.statusComponent.statusBaseImpl.StatusStrategy
import javafx.event.ActionEvent
import scalafx.Includes.handle
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{StackPane, VBox}
import scalafx.scene.paint.Color.Black

import scala.collection.mutable.Stack

object Gui extends JFXApp {
  val status = new StatusStrategy
  val controller = new Controller(status)
  var dealerStack = Stack[CardFactory]()
  var playerStack = Stack[CardFactory]()
  stage = new PrimaryStage {
  val menuPane = new StackPane()

      val menubar = new MenuBar {

        menus = List(
          new Menu("Game") {
            items = List(
              new MenuItem {
                text = "New Game"
                onAction = (e: ActionEvent) => {
//                  content = Seq(view, label, buttonStart)
                  menuPane
                }
              },
              new MenuItem {
                text = "Undo"
                onAction = (e: ActionEvent) => {
                  controller.undo()
                }
              },
              new MenuItem {
                text = "Redo"
                onAction = (e: ActionEvent) => {
                  controller.redo()
                }
              },
            )
          },
          new Menu("Help") {
            items = List(
              new MenuItem {
                text = "About"
                onAction = (e: ActionEvent) => {
                  new Alert(AlertType.Information, "Contributors:\t\t Daniel Bersuch & Marc Freibauer\n" +
                    "Class:\t\t\t Software Engineering by Marko Boger\n" +
                    "Version: \t\t\t 1.0\n").showAndWait()
                }
              },
              new MenuItem {
                text = "Rules"
                onAction = (e: ActionEvent) => {
                  new Alert(AlertType.Information, "In the First Round you and the Dealer get 2 Cards\n" +
                    "The Values of the Card will be automatically summed up\n" +
                    "You can choose between 'Hit: Take another Card' or 'Stand: stand with your current cards\n" +
                    "The Dealer must take as long as he is bellow 17\n" +
                    "The Goal is not to be above 21 before the Dealer ist\n" +
                    "You Win if the Dealer is above 21 or has lower Value than you\n" +
                    "But you will loose if you get above 21 before the Dealer does or when the Dealer has higher Value than you\n").showAndWait()
                }
              }
            )
          },
          new Menu("Quit") {
            items = List(
              new MenuItem {
                text = "Quit"
                onAction = handle {
                  sys.exit(0)
                }
              }
            )
          }
        )
      }
      menubar.setId("menubar")
      title = "Blackjack"
    scene = new Scene {
      fill = Black

      val pane = new StackPane()
      val stack = new Stack[ImageView]

      val img = new Image("file:image\\background\\blackjackStart.jpg")
      val view = new ImageView(img)
      val contentPane = new VBox()
      val buttonStart = new Button("Start Game")
      // Game Starts here after clicking Start Game Button
      buttonStart.onAction = (e: ActionEvent) => {
        val img2 = new Image("file:image\\background\\blackjacktable2.jpg")
        val view2 = new ImageView(img2)
        controller.generateDeck
        controller.shuffleDeck
        val CardImg = new ImageView(new Image("file:image\\cards\\red_back.png", 86, 110, false, true))
        CardImg.layoutX = 820
        CardImg.layoutY = 80

        val player = Player("Player")
        val dealer = Player("Dealer")

        player.addCard(controller.draw())
        val Playerimg1 = new ImageView(new Image("file:image\\cards\\" + player.karte.top.face + "" + player.karte.top.suit + ".png", 86, 110, false, true))
        Playerimg1.layoutX = 500
        Playerimg1.layoutY = 410
        dealer.addCard(controller.draw())
        val Dealerimg1 = new ImageView(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
        Dealerimg1.layoutX = 500
        Dealerimg1.layoutY = 80
        player.addCard(controller.draw())
        val Playerimg2 = new ImageView(new Image("file:image\\cards\\" + player.karte.top.face + "" + player.karte.top.suit + ".png", 86, 110, false, true))
        Playerimg2.layoutX = 580
        Playerimg2.layoutY = 410
        dealer.addCard(controller.draw())
        val HiddenCard = new ImageView(new Image("file:image\\cards\\red_back.png", 86, 110, false, true))
        HiddenCard.layoutX = 580
        HiddenCard.layoutY = 80

        val Playerimg3 = new ImageView(new Image("file:image\\cards\\" + player.karte.top.face + "" + player.karte.top.suit + ".png", 86, 110, false, true))
        Playerimg3.layoutX = 660
        Playerimg3.layoutY = 410

        val Dealerimg2 = new ImageView(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
        Dealerimg2.layoutX = 660
        Dealerimg2.layoutY = 80

        val Dealerimg3 = new ImageView(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
        Dealerimg3.layoutX = 660
        Dealerimg3.layoutY = 80

        //Player Label
        val playerLabel = new Label(player.name + ": " + player.handValue())
        playerLabel.layoutY = 550
        playerLabel.layoutX = 560

        //Dealer Label
        val dealerLabel = new Label(dealer.name + ": " + dealer.handValue())
        dealerLabel.layoutY = 35
        dealerLabel.layoutX = 560

        // Hidden Card till pressed Stand
        val hiddenLabel = new Label("Hidden Card!")
        hiddenLabel.layoutY = 35
        hiddenLabel.layoutX = 560

        //
        val hitButton = new Button("Hit")
        hitButton.layoutY = 215
        hitButton.layoutX = 530

        val standButton = new Button("Stand")
        standButton.layoutY = 215
        standButton.layoutX = 590

        val saveButton = new Button( "Save")
        saveButton.layoutY = 47
        saveButton.layoutX = 25

        val loadButton = new Button( "Load")
        loadButton.layoutY = 87
        loadButton.layoutX = 25

        // Check if Player is below 21
        if (dealer.handValue() > 21) {
          new Alert(AlertType.Information, "Dealer Bust!").showAndWait()
          content = Seq(view, menubar, label, buttonStart)
        }

        if (player.handValue() > 21) {
          new Alert(AlertType.Information, "Player Bust!").showAndWait()
          content = Seq(view, menubar, label, buttonStart)
        }

        if(player.handValue() > 21) {
          hitButton.setText("Result")
        }

        hitButton.onAction = (e: ActionEvent) => {
          if ((player.handValue() < 21) && (dealer.handValue() <= 21)) {
            player.addCard(controller.draw())
            val img2 = new ImageView(new Image("file:image\\cards\\" + player.karte.top.face + "" + player.karte.top.suit + ".png", 88, 62, false, true))
            playerLabel.setText(player.name + ": " + player.handValue().toString)
            Playerimg2.setImage(new Image("file:image\\cards\\" + player.karte.top.face + "" + player.karte.top.suit + ".png", 86, 110, false, true))

            //pane.children = List(view2, menubar, hitButton, standButton, hiddenLabel, Dealerimg1, HiddenCard, playerLabel, Playerimg1, Playerimg2, Playerimg3, CardImg)
            content = List(view2, menubar, hitButton, standButton, saveButton, loadButton, hiddenLabel, Dealerimg1, HiddenCard, playerLabel, Playerimg1, Playerimg2, Playerimg3, CardImg)
          } else {
            if (player.handValue() > 21 && dealer.handValue() <= 21) {
              new Alert(AlertType.Information, "Player Bust!").showAndWait()
              content = Seq(view, menubar, label, buttonStart)
            } else if (player.handValue() == 21 && dealer.handValue() != 21) {
              new Alert(AlertType.Information, "Player has a BLACKJACK!").showAndWait()
              content = Seq(view, menubar, label, buttonStart)
            } else if (player.handValue() < dealer.handValue() && dealer.handValue() <= 21) {
              new Alert(AlertType.Information, "Dealer Wins!").showAndWait()
              content = Seq(view, menubar, label, buttonStart)
            } else if (player.handValue() < dealer.handValue() && dealer.handValue() > 21) {
              new Alert(AlertType.Information, "Player Wins!").showAndWait()
              content = Seq(view, menubar, label, buttonStart)
            } else if ((player.handValue() == dealer.handValue()) && player.handValue() <= 21) {
              new Alert(AlertType.Information, "You have a Tie!").showAndWait()
              content = Seq(view, menubar, label, buttonStart)
            }
          }
          //              content = List(view, menubar, hitButton, standButton, dealerLabel, Dealerimg1, Dealerimg2, playerLabel, Playerimg1, Playerimg2, CardImg)
          //content = List(pane)
        }
        //content = List(pane)


        saveButton.onAction = (e: ActionEvent) => {
          controller.save(player, dealer)
        }

//        loadButton.onAction = (e: ActionEvent) => {
//          controller.load(player, dealer)
//        }


        standButton.onAction = (e: ActionEvent) => {
          val Dealerimg2 = new ImageView(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
          Dealerimg2.layoutX = 580
          Dealerimg2.layoutY = 80
          Dealerimg2.setImage(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
//            content = List(view2, menubar, hitButton, standButton, dealerLabel, Dealerimg1, Dealerimg2, playerLabel, Playerimg1, Playerimg2, Playerimg3, CardImg)
          content = List(view2, menubar, hitButton, standButton, saveButton, loadButton, dealerLabel, Dealerimg1, Dealerimg2, playerLabel, Playerimg1, Playerimg2, CardImg)
          while (dealer.handValue() < 17) {
            dealer.addCard(controller.draw())
            Dealerimg3.setImage(new Image("file:image\\cards\\" + dealer.karte.top.face + "" + dealer.karte.top.suit + ".png", 86, 110, false, true))
            dealerLabel.setText(dealer.name + ": " + dealer.handValue())
//            content = List(view2, menubar, hitButton, standButton, dealerLabel, Dealerimg1, Dealerimg2, Dealerimg3, playerLabel, Playerimg1, Playerimg2, Playerimg3, CardImg)
            content = List(view2, menubar, hitButton, standButton, saveButton, loadButton, dealerLabel, Dealerimg1, Dealerimg2, Dealerimg3, playerLabel, Playerimg1, Playerimg2, CardImg)
          }

            if(dealer.handValue() > 21 && player.handValue() <= 21) {
              new Alert(AlertType.Information, "Dealer Bust!").showAndWait()
            } else if ((player.handValue() > dealer.handValue()) && player.handValue() < 21) {
              new Alert(AlertType.Information, "Player Wins!").showAndWait()
            } else if (player.handValue() == 21 && dealer.handValue() != 21) {
              new Alert(AlertType.Information, "Player has a BLACKJACK!").showAndWait()
            } else if (player.handValue() < dealer.handValue() && dealer.handValue() < 21) {
              new Alert(AlertType.Information, "Dealer Wins!").showAndWait()
           } else if (dealer.handValue() == 21 && player.handValue() != 21) {
              new Alert(AlertType.Information, "Dealer has a BLACKJACK!").showAndWait()
             // hier kann der Dealer sofort gewinnen, ohne dass seine Karte aufgedeckt wird...
            } else if (player.handValue() > dealer.handValue() && dealer.handValue() <= 21) {
              new Alert(AlertType.Information, "Player Bust!").showAndWait()
            } else if (player.handValue() > dealer.handValue() && dealer.handValue() > 21) {
              new Alert(AlertType.Information, "Player Bust!").showAndWait()
            } else if (player.handValue() == dealer.handValue() && dealer.handValue() <= 21) {
              new Alert(AlertType.Information, "You have a Tie!").showAndWait()
            }

          content = Seq(view, menubar,  label, buttonStart)

        }

        content = List(view2, menubar, hitButton, standButton, saveButton, loadButton, hiddenLabel, Dealerimg1, HiddenCard, playerLabel, Playerimg1, Playerimg2, CardImg)

        if (player.handValue() > 21) {
          new Alert(AlertType.Information, "Player Bust!").showAndWait()
          content = Seq(view, menubar, label, buttonStart)
        } else if (player.handValue() == 21 && dealer.handValue() != 21) {
          new Alert(AlertType.Information, "Player has a BLACKJACK!").showAndWait()
          content = Seq(view, menubar, label, buttonStart)
        } else if (player.handValue() < dealer.handValue() && dealer.handValue() > 21) {
          new Alert(AlertType.Information, "Player Wins!").showAndWait()
          content = Seq(view, menubar, label, buttonStart)
        }

      }

      buttonStart.layoutX = 500
      buttonStart.layoutY = 300
      val label = new Label("Welcome to Blackjack!") {
        style = "-fx-font-size: 48pt"
        padding = Insets(20)
      }
      label.layoutX = 250
      label.layoutY = 150
      pane.children = List(menubar, view, label)
      content = Seq(view, menubar,  label, buttonStart)
    }
  }


  def openImage(hand: CardFactory) = {
    val img = new ImageView(new Image("file:image\\cards\\" + hand.face + "" + hand.suit + ".png", 88, 62, false, true))
  }
}
