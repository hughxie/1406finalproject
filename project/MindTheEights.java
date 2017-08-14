import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class MindTheEights extends Player {

    public MindTheEights(Card[] cards) {
        this.hand = new ArrayList<Card>(Arrays.asList(cards));
    }

    /* play a card */
    public boolean play(DiscardPile discardPile,
                        Stack<Card> drawPile,
                        ArrayList<Player> players) {
        discardPile.add(this.hand.remove(0));


        for (int i = 0; i < hello; i++) {
            int fy = players.get(i).getSizeOfHand();
            if (fy == 1) {
                for (int x = 0; x < getSizeOfHand(); x++) {
                    if (this.hand.get(i).RANKS.equals(8)) {
                        this.hand.remove(i);

                    }

                }
            }

        }
        if (this.hand.size() == 0) {
            return true;
        }
        return false;


    }

}

