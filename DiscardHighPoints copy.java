import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class DiscardHighPoints extends Player{

    public DiscardHighPoints(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}

    /* play a card */
    public boolean play(DiscardPile       discardPile,
                        Stack<Card>       drawPile,
                        ArrayList<Player> players)
    {
        Card highestPointCard = this.highest_card();        // Returns highest card and sorts deck
        Card previousCard = discardPile.top();              // Returns top card in discard Pile


        // Checks if previous card was a 2
        if (previousCard.getRank() == 2){
            hand.add(drawPile.pop());
            hand.add(drawPile.pop());
        }
        // Gets rid of highest card if it is an 8
        else if (highestPointCard.getRank() == 8){
            discardPile.add(this.hand.remove(0));
        }
        // Gets Rid of highest card if suits are the same
        else if (highestPointCard.getSuit().equals(previousCard.getSuit())){
            discardPile.add(this.hand.remove(0));
        }
        // Checks if we have a playable card
        else if (canPlay(previousCard)){

            if (highest_card_counter() > 0){                // if we have more than one of highest value card
                for (Card c: hand){
                    if (c.getSuit().equals(previousCard.getSuit()) && c.getRank() == highestPointCard.getRank()){
                        discardPile.add(c);
                        hand.remove(c);
                        break;
                    }
                }
            }
            else{                                           // puts card with same suit has highest card
                for (Card c: hand){
                    if (c.getSuit().equals(highestPointCard.getSuit()) && c.getRank() == previousCard.getRank()){
                        discardPile.add(c);
                        hand.remove(c);
                        break;
                    }
                }
            }
        }
        // No playable card so we now have to pick up from discard pile
        else{
            // pick from pile
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

    private Card highest_card(){             // returns highest value card and sorts deck

        boolean checker = true;
        Card tempCard;

        while (checker){
            int which_card;
            checker = false;

            for (int i = 0; i < hand.size()-1; i++){
                which_card = hand.get(i).compareTo(hand.get(i+1));

                if (which_card == -1){                  // Sorts in descending order
                    tempCard = hand.get(i);
                    hand.set(i, hand.get(i+1));
                    hand.set(i+1, tempCard);
                    checker = true;
                }
            }
        }
        return hand.get(0);
    }

    private int highest_card_counter(){
        int counter = 0;

        highest_card();
        for (int i = 0; i < hand.size() - 1; i++){

            if (hand.get(i).compareTo(hand.get(i+1)) == 0) {
                counter++;
            }
            else{
                break;
            }
        }
        return counter;
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
