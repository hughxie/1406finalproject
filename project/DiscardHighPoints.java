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
        if (previousCard.getRank() == 2 && previousCard.rounds == 1){

            int drawCount = 0;
            while (drawCount < 2){
                if(!drawPile.isEmpty()){
                    hand.add(drawPile.pop());
                }
                drawCount++;
            }
            System.out.println("\nPicks up 2 cards from draw pile if draw pile isn't empty.");
        }
        else if (previousCard.getRank() == 4 && previousCard.rounds == 1){
            System.out.println("\nSkipped my turn.");
        }
        // Gets rid of highest card if it is an 8 and sets suit to next highest card
        else if (highestPointCard.getRank() == 8){

            discardPile.add(this.hand.remove(0));
            highestPointCard = this.highest_card();
            Card suitChange = new Card(highestPointCard.getSuit(), "8");
            discardPile.add(suitChange);

            System.out.println("\nGets rid of highest card if it is an 8, and sets suit to next highest card.");
        }
        // Gets Rid of highest card if suits are the same
        else if (highestPointCard.getSuit().equals(previousCard.getSuit()) && !(highestPointCard.getRank() == 8)){
            discardPile.add(this.hand.remove(0));

            System.out.println("\nPlays highest point card.");
        }
        // Checks if we have any playable card
        else if (canPlay(previousCard)){

            //---------------------------------------------------------------------------------------------------
            if (highest_card_counter() > 0){               // Plays highest card with same suit
                int cardAdded = 0;

                for (int i = 0; i < hand.size() - 1; i++){

                    if ((hand.get(i+1).getSuit().equals(previousCard.getSuit()) || hand.get(i+1).getRank() == previousCard.getRank()) && (cardPoints(hand.get(i+1)) == cardPoints(highest_card()))){

                        discardPile.add(hand.get(i+1));
                        hand.remove(hand.get(i+1));
                        cardAdded = cardAdded + 1;
                        break;
                    }
                }
                if (cardAdded != 0){
                    System.out.println("\nPlays highest point card with the same suit as prevoius.");
                }

                                                        // Plays card with same suit as highest card or cards
                if (cardAdded == 0){
                    for (int i = 0; i < highest_card_counter() + 1; i++){
                        for (Card c: hand){

                            if (c.getSuit().equals(hand.get(i).getSuit()) && c.getRank() == previousCard.getRank()){
                                discardPile.add(c);
                                hand.remove(c);
                                cardAdded++;
                                break;
                            }
                        }
                        if (cardAdded != 0){
                            System.out.println("\nPlays card with same suit as highest card.");
                            break;
                        }
                    }
                }
                                                            // Plays any valid card
                if (cardAdded == 0){
                    for (Card c: hand){
                        if (c.getSuit().equals(previousCard.getSuit()) || c.getRank() == previousCard.getRank()){
                            discardPile.add(c);
                            hand.remove(c);
                            System.out.println("\nPlays any valid card.");
                            break;
                        }
                    }
                }
            }

            //---------------------------------------------------------------------------------------------------
            else{
                int cardAdded = 0;
                for (Card c: hand){                         // plays card with same suit as highest card

                    if (c.getSuit().equals(highestPointCard.getSuit()) && c.getRank() == previousCard.getRank()){
                        discardPile.add(c);
                        hand.remove(c);
                        cardAdded++;
                        System.out.println("\nPlays card with same suit as highest card.");
                        break;
                    }
                }
                if (cardAdded == 0){                        // plays any valid card
                    for (Card c: hand){
                        if (c.getSuit().equals(previousCard.getSuit()) || c.getRank() == previousCard.getRank()){
                            discardPile.add(c);
                            hand.remove(c);
                            System.out.println("\nPlays any valid card.");
                            break;
                        }
                    }
                }
            }
        }
        //--------------------------------------------------------------------------------------------------------
        // No playable card so we now have to pick up from discard pile
        else{
            // pick from pile
            if (!drawPile.isEmpty()){

                boolean checker = false;
                Card fromDraw;

                while (!checker && !drawPile.isEmpty()){
                    fromDraw = drawPile.pop();
                    if ((fromDraw.getSuit().equals(previousCard.getSuit())) ||
                            (fromDraw.getRank() == previousCard.getRank())){

                        discardPile.add(fromDraw);
                        System.out.println("\nPlays a valid card from draw pile.");
                        checker = true;
                    }
                    else{
                        hand.add(fromDraw);
                        System.out.println("\nCard from draw pile is not valid, draw again.");
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

        highest_card();     // sort deck
        for (int i = 0; i < hand.size() - 1; i++){

            if (cardPoints(highest_card()) == cardPoints(hand.get(i + 1))) {
                counter = counter +1;
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

    private static int cardPoints(Card c){
        if (c.getRank() == 8){
            return 50;
        }
        else if (c.getRank() == 2 || c.getRank() == 4){
            return 25;
        }
        else if (c.getRank() == 7){
            return 20;
        }
        else if (c.getRank() >= 11 && c.getRank() <=13){
            return 10;
        }
        else if (c.getRank() == 14){
            return 1;
        }
        else{
            return c.getRank();
        }
    }




}
