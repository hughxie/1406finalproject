import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class ExtraCards extends Player{

  public ExtraCards(Card[] cards) {
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
  }

  public boolean play(
    DiscardPile       discardPile,
    Stack<Card>       drawPile,
    ArrayList<Player> players
  )
  {
    // Checks if previous card was a 2
    if (previousCard.getRank() == 2){
        hand.add(drawPile.pop());
        hand.add(drawPile.pop());
    }
  }
}
