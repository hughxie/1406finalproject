import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class ExtraCards extends Player{


  //constructor
  public ExtraCards(Card[] cards) {
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
  }


  //play method
  public boolean play(
    DiscardPile       discardPile,
    Stack<Card>       drawPile,
    ArrayList<Player> players
  )
  {

    System.out.println("- - - - - - - - - - - - - - -");
    System.out.println("Hand before play: " + hand);
    boolean played = false;
    // Checks if previous card was a 2
    if ((discardPile.top().getRank() == 2) && (discardPile.top().rounds == 1)){
        pickupCard(drawPile);
        pickupCard(drawPile);
    }
    int direction = Crazy8Game.getDirection();

    int next = nextPlayer(players, direction);

    //if someone is about to win the game
    if (players.get(next).getSizeOfHand() == 1) {
      System.out.println("Player is going to win: " + next);
      int power = havePower();
      //if power card is available, play the card
      if (power > 0) {
        Card card = hand.get(power);
        if (canPlay(discardPile, card)) {
          System.out.println("has power: " + hand.get(power));
          System.out.println(card);
          discardPile.add(card);
          hand.remove(card);
          played = true;
          System.out.println("Stopped player "+ next +" from winning with a " + card);

        }
      } else {
        System.out.println("no power to play.");
        //if no power card, keep picking up until one is available
        boolean check = false;
        while ((!check) && (!drawPile.isEmpty())) {
          System.out.println("picking up until has power");
          pickupCard(drawPile);
          if (isPower(topCard())) {
            System.out.println("found power: " + topCard());
            playCard(discardPile, hand.size());
            played = true;
            break;
          }
        }
      }
      //if nobody is going to win, pick up one card and if its a power card, play it
    } else {
      System.out.println("nobody is going to win.");
        pickupCard(drawPile);
        if (isPower(topCard())) {
          playCard(discardPile, hand.size());
          played = true;
        }
      }

    //if no power cards come up, play a valid card
    for (Card c : hand) {
      if ((canPlay(discardPile, c)) && (!played)) {
        discardPile.add(c);
        hand.remove(c);
        played = true;
        break;
      }
    }


    //if cant play
    if ((!played) && (!drawPile.isEmpty())) {
      System.out.println("Can't play.");
      pickupCard(drawPile);
    }
    System.out.println("Hand after play: " + hand);

    //win check
    if (hand.size() == 0) {
      return true;
    }
    return false;
  }

  //check to see if the card is valid to play
  public boolean canPlay(DiscardPile discardPile, Card card) {
    Card lastCard = discardPile.top();
    int lastRank = lastCard.getRank();
    String lastSuit = lastCard.getSuit();
    if (((card.getRank() == lastRank) || (card.getSuit().equals(lastSuit) || (card.getRank() == 8)))&&(hand.size() > 0)) {
      return true;
    }
    return false;
  }

  //checks to see if hand has power code
  public int havePower() {
    for (int i = 0; i < hand.size(); i++) {
      if (isPower(hand.get(i))) {
        return i;
      }
    }
    return -1;
  }

  //checks if card is power
  public boolean isPower(Card card) {
    int rank = card.getRank();
    if ((rank == 2) || (rank == 4) || (rank == 7) || (rank == 8)) {
      return true;
    }
    return false;
  }

  public void playCard(DiscardPile discardPile, int index) {
  	System.out.println("playcard method" + hand);
  	Card card = hand.get(index-1);
  	discardPile.add(card);
  	hand.remove(card);
  }

  public boolean pickupCard(Stack<Card> drawPile) {
    if (!drawPile.isEmpty()) {
      hand.add(drawPile.pop());
      return true;
    }
    return false;
  }

  public Card topCard() {
    return this.hand.get(hand.size()-1);
  }


  public int nextPlayer(ArrayList<Player> players, int direction) {
    int next;
    int me = 0;
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).equals(this)) {
        me = i;
        break;
      }
    }
    if ((me >= players.size()-1) && (direction == 1)) {
      next = 0;
    } else if ((me <= 0) && (direction == -1)) {
      next = players.size()-1;
    } else {
      next = me + direction;
    }
    return next;
  }

}
