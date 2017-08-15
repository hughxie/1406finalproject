import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;
import java.util.Collections;

public class Crazy8Game{

    public static void main(String[] args){

		/* create the deck */
        Card[] deck = new Card[52];
        int index = 0;
        for(int r=2; r<=14; r+=1){
            for(int s=0; s<4; s+=1){
                deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
            }
        }

		/* shuffle the deck */
        Random rnd = new Random();
        Card swap;
        for(int i = deck.length-1; i>=0; i=i-1){
            int pos = rnd.nextInt(i+1);
            swap = deck[pos];
            deck[pos] = deck[i];
            deck[i] = swap;
        }

		/* players in the game */
        Player[] players = new Player[5];
        players[0] = new ExtraCards( Arrays.copyOfRange(deck, 0, 5) );
        System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 0, 5)));
        players[1] = new ExtraCards( Arrays.copyOfRange(deck, 5, 10) );
        System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 5, 10)));
        players[2] = new BadPlayer( Arrays.copyOfRange(deck, 10, 15) );
        System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));
        players[3] = new BadPlayer( Arrays.copyOfRange(deck, 10, 15) );
        System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));
        players[4] = new BadPlayer( Arrays.copyOfRange(deck, 10, 15) );
        System.out.println("0 : " + Arrays.toString( Arrays.copyOfRange(deck, 10, 15)));


		/* discard and draw piles */
        DiscardPile discardPile = new DiscardPile();
        Stack<Card> drawPile = new Stack<Card>();
        for(int i=15; i<deck.length; i++){
            drawPile.push(deck[i]);
        }

        System.out.println("draw pile is : " + Arrays.toString( Arrays.copyOfRange(deck, 15, deck.length) ));

        deck = null;

        boolean win = false;
        int player = -1;    // start game play with player 0

        ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
        discardPile.add( drawPile.pop() );

        int negate = 1;

        while( !win ){
            //handles reverse order, prevents out of bounds
            if ((player <= 0) && (negate==(-1))) {
              player = players.length-1;
            } else if ((player >= players.length-1) && (negate == 1)) {
              player = 0;
            } else {
              player = player + negate;
            }
            System.out.println("player " + player);
            System.out.println("draw pile    : " + drawPile.peek() );
            System.out.println("discard pile : " + discardPile.top() );

            win = people.get(player).play(discardPile, drawPile, people);

            System.out.println("draw pile   : " + drawPile.peek() );
            System.out.println("discard pile : " + discardPile.top() );

            if (discardPile.top().getRank() == 7){              // Reverses order if 7 Card is played
                negate *= -1;
            }
            else if(discardPile.top().getRank() == 4){          // Skips next player if 4 Card is played
                player = (player + negate);
            }

        }
        System.out.println("winner is player " + player);

    }
}
