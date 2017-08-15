import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class RandomPlayer extends Player{

	public RandomPlayer(Card[] cards) {
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
  }
  /* play a card */
	public boolean play(DiscardPile       discardPile,
	                    Stack<Card>       drawPile,
											ArrayList<Player> players)
	{

    //picks up cards if last played was 2
		if (discardPile.top().getRank() == 2) {
      getCard(drawPile);
      getCard(drawPile);
    }

    Card        previous = discardPile.top();
    int     previousRank = previous.getRank();
    String  previousSuit = previous.getSuit();
    boolean       played = false;

    //loops through hand, plays the first card that is valid
    for (Card c : hand) {
      if (hand.size() > 0) {
        if ((c.getRank() == previousRank) || (c.getSuit().equals(previousSuit) || (c.getRank() == 8))) {
          discardPile.add(c);
          hand.remove(c);
          System.out.println("Played: " + c);
          played = true;
          break;
        }
      }
    }

    //if no cards were played then pick one card up
    if (!played) {
      getCard(drawPile);
      played = true;
    }

    //if hand is empty, return true
    if (hand.size() == 0) {
      return true;
    }
    return false;
	}

  public boolean getCard(Stack<Card> drawPile) {
    if (!drawPile.isEmpty()) {
      hand.add(drawPile.pop());
      return true;
    }
    return false;
  }
}
