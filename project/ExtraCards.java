import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class ExtraCards extends Player{

  public ExtraCards(Card[] cards) {
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
  }

  public boolean play(
    public DiscardPile       discardPile,
    Stack<Card>       drawPile,
    ArrayList<Player> players
  )
  {
    // Checks if previous card was a 2
    if (discardPile.top().getRank() == 2){
        hand.add(drawPile.pop());
        hand.add(drawPile.pop());
    }
    for (int i = 0;i < players.size(); i++) {
      Card current;
      int next = nextPlayer(players);

      //if someone is about to win the game
      if (players.get(next).getSizeOfHand() == 1) {
        int power = havePower();
        if (power > 0) {
          playCard(discardPile, power);
        } else {
          getPower(drawPile);
        }
      }
    }
  }

  public boolean getPower(Stack<Card> drawPile) {
    boolean check = false;
    while (!check) {
      pickupCard(drawPile);
      if (isPower(topCard())) {
        return true;
      }
    }
    return false;
  }

  public int havePower() {
    for (int i = 0; i < hand.size(); i++) {
      if (isPower(hand.get(i))) {
        return i;
      }
    }
    return -1;
  }

  public boolean isPower(Card card) {
    int rank = card.getRank();
    if ((rank == 2) || (rank == 4) || (rank == 7) || (rank == 8)) {
      return true;
    }
    return false;
  }

  public void playCard(DiscardPile discardPile, int index) {
    discardPile.add(hand.remove(index));
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

  public int nextPlayer(ArrayList<Player> players) {
    int me;
    int next;
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).equals(this)) {
        me = i;
        break;
      }
    }
    if (me < players.size()) {
      next = me + 1;
    } else {
      next = 0;
    }
    return next;
  }

}
