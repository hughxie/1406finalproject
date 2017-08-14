import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
public class MindTheEights extends Player{

    public MindTheEights(Card[] cards) {
        this.hand = new ArrayList<Card>(Arrays.asList(cards));
    }

    /* play a card */
    public boolean play(DiscardPile       discardPile,
                        Stack<Card>       drawPile,
                        ArrayList<Player> players)
    {
        Card previousCard = discardPile.top();              // Returns top card in discard Pile

        int myEights = 0;
        int smallestHand = 52;

        // Finds My Amount of Eights
        for (Card c: hand){
            if (c.getRank() == 8){
                myEights++;
            }
        }

        // Finds Smallest Hand
        for (Player p: players){
            if (p.getSizeOfHand() < smallestHand){
                smallestHand = p.getSizeOfHand();
            }
        }

        // Decide what to do
        if (myEights == smallestHand && myEights != 0){
            for (Card c: hand){
                if (c.getRank() == 8){
                    discardPile.add(c);
                    hand.remove(c);
                }
            }
        }
        else if (canPlay(previousCard)){
            for (Card c: hand){
                if (c.getRank() == previousCard.getRank() || c.getSuit().equals(previousCard.getSuit())){
                    discardPile.add(c);
                    hand.remove(c);
                }
            }
        }
        else{
            if (!drawPile.isEmpty()){

                boolean checker = false;
                Card fromDraw;

                while (!checker){
                    fromDraw = drawPile.pop();
                    if ((fromDraw.getSuit().equals(previousCard.getSuit())) ||
                            (fromDraw.getRank() == previousCard.getRank())){

                        discardPile.add(fromDraw);
                        checker = true;
                    }
                    else{
                        hand.add(fromDraw);
                    }
                }
            }
        }

        if( this.hand.size() == 0 ){return true;}
        return false;

    }

    private boolean canPlay(Card discardTop){
        for (Card c: hand){
            if (c.getRank() == discardTop.getRank() || c.getSuit().equals(discardTop.getSuit())){
                return true;
            }
        }
        return false;
    }


}
