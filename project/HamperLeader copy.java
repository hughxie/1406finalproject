import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;
public class HamperLeader extends Player{

    public HamperLeader(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}

    /* play a card */
    public boolean play(DiscardPile       discardPile,
                        Stack<Card>       drawPile,
                        ArrayList<Player> players)
    {
        Card previousCard = discardPile.top();
        int smallestHand = 52;
        Player smallestPlayer = null;

        // Finds Smallest Hand
        for (Player p: players){
            if (p.getSizeOfHand() < smallestHand){

                smallestPlayer = p;
            }
        }
        if (smallestPlayer.equals(players.indexOf(this) + 1) ||
                (smallestPlayer.equals(players.get(0)) && this.equals(players.get(players.size()-1)))){

            if (hasSpecial()){

                if (smallestPlayer.equals(players.indexOf(this) + 1) ||
                        (smallestPlayer.equals(players.get(0)) && this.equals(players.get(players.size()-1)))) {
                    for (Card c : hand) {
                        if (c.getRank() == 8 || c.getRank() == 2 || c.getRank() == 4) {
                            discardPile.add(c);
                            hand.remove(c);
                            break;
                        }
                    }

                }
                else if (smallestPlayer.equals(players.indexOf(this) - 1) ||
                        (smallestPlayer.equals(players.get(players.size()-1)) && this.equals((players.get(0))))){
                    if (hasSeven()){
                        for (Card c : hand) {
                            if (c.getRank() == 7) {
                                discardPile.add(c);
                                hand.remove(c);
                                break;
                            }
                        }
                    }
                    else{
                        for (Card c: hand){
                            if (c.getRank() == previousCard.getRank() || c.getSuit().equals(previousCard.getSuit())){
                                discardPile.add(c);
                                hand.remove(c);
                            }
                        }
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
        }

        if( this.hand.size() == 0 ){return true;}
        return false;

    }

    public boolean hasSpecial(){
        for (Card c: hand){
            if (c.getRank() == 8 || c.getRank() == 2 || c.getRank() == 4 || c.getRank() == 7){
                return true;
            }
        }
        return false;
    }

    public boolean hasSeven(){
        for (Card c: hand){
            if (c.getRank() == 7){
                return true;
            }
        }
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
