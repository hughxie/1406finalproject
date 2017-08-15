import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Random;


public class DHPGame {



    public static void main(String[] args) {

        int pointsNeeded = 0;
        boolean flag = false;

        while(!flag){

            Card[] deck = new Card[52];
            int index = 0;

            for (int r = 2; r <= 14; r += 1) {
                for (int s = 0; s < 4; s += 1) {
                    deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
                }
            }
            /* shuffle the deck */
            Random rnd = new Random();
            Card swap;
            for (int i = deck.length - 1; i >= 0; i = i - 1) {
                int pos = rnd.nextInt(i + 1);
                swap = deck[pos];
                deck[pos] = deck[i];
                deck[i] = swap;
            }

            /* players in the game */
            /*
            Player[] players = new Player[3];
            players[0] = new DiscardHighPoints(Arrays.copyOfRange(deck, 0, 5));
            System.out.println("0 : " + Arrays.toString(Arrays.copyOfRange(deck, 0, 5)));
            players[1] = new DiscardHighPoints(Arrays.copyOfRange(deck, 5, 10));
            System.out.println("0 : " + Arrays.toString(Arrays.copyOfRange(deck, 5, 10)));
            players[2] = new BadPlayer(Arrays.copyOfRange(deck, 10, 15));
            System.out.println("0 : " + Arrays.toString(Arrays.copyOfRange(deck, 10, 15)));
            */

            Card[] deck0 = new Card[6];
            Card[] deck1 = new Card[6];


            //deck0[0] = new Card (Card.SUITS[0], Card.RANKS[5]);
            deck0[0] = new Card (Card.SUITS[0], Card.RANKS[7]);
            deck0[1] = new Card (Card.SUITS[3], Card.RANKS[5]);
            deck0[2] = new Card (Card.SUITS[3], Card.RANKS[4]);
            deck0[3] = new Card (Card.SUITS[0], Card.RANKS[2]);
            deck0[4] = new Card (Card.SUITS[3], Card.RANKS[8]);
            deck0[5] = new Card (Card.SUITS[3], Card.RANKS[4]);

            //deck1[0] = new Card (Card.SUITS[0], Card.RANKS[5]);
            deck1[0] = new Card (Card.SUITS[1], Card.RANKS[8]);
            deck1[1] = new Card (Card.SUITS[0], Card.RANKS[2]);
            deck1[2] = new Card (Card.SUITS[1], Card.RANKS[4]);
            deck1[3] = new Card (Card.SUITS[2], Card.RANKS[5]);
            deck1[4] = new Card (Card.SUITS[1], Card.RANKS[5]);
            deck1[5] = new Card (Card.SUITS[1], Card.RANKS[13]);



            Player[] players = new Player[2];
            players[0] = new BadPlayer( deck0 );
            System.out.println("0 : " + players[0].hand);
            players[1] = new DiscardHighPoints( deck1 );
            System.out.println("0 : " + players[1].hand);



            /* discard and draw piles */
            DiscardPile discardPile = new DiscardPile();
            Stack<Card> drawPile = new Stack<Card>();
            for (int i = 15; i < deck.length; i++) {
                drawPile.push(deck[i]);
            }

            System.out.println("draw pile is : " + Arrays.toString(Arrays.copyOfRange(deck, 15, deck.length)));

            deck = null;

            boolean win = false;
            int player = -1;    // start game play with player 0

            ArrayList<Player> people = new ArrayList<Player>(Arrays.asList(players));
            discardPile.add(drawPile.pop());
            int negate = 1;

            while (!win) {

                //handles reverse order, prevents out of bounds
                if ((player <= 0) && (negate==(-1))) {
                    player = players.length-1;
                } else if ((player >= players.length-1) && (negate == 1)) {
                    player = 0;
                } else {
                    player = player + negate;
                }
                System.out.println("__________________________________________________________________");


                System.out.println("player " + player);
                System.out.println("\n          hand: " + players[player].hand);

                if (!drawPile.isEmpty()){
                    System.out.println("\n\ndraw pile   : " + drawPile.peek());
                }
                else{
                    System.out.println("Draw Pile is Empty");
                }

                System.out.println("discard pile : " + discardPile.top());
                if (discardPile.top().getRank() == 2){
                    discardPile.top().rounds++;
                }

                win = people.get(player).play(discardPile, drawPile, people);

                System.out.println("\n          hand: " + players[player].hand);

                if (!drawPile.isEmpty()){
                    System.out.println("\n\ndraw pile   : " + drawPile.peek());
                }
                else{
                    System.out.println("Draw Pile is Empty");
                }


                System.out.println("discard pile : " + discardPile.top());

                System.out.println("__________________________________________________________________");

                if (players[player].getSizeOfHand() == 0){
                    break;
                }
                else if (discardPile.top().getRank() == 7){              // Reverses order if 7 Card is played
                    negate *= -1;
                }
                else if (discardPile.top().getRank() == 4) {          // Skips next player if 4 Card is played
                    player = (player + negate);
                }

            }
            System.out.println("winner is player " + player);
            int pointsAdded = addPoints(players);
            for (Player p: players){
                if (p.equals(players[player])){
                    p.points = p.points + pointsAdded;
                    if (p.points >= pointsNeeded){
                        flag = true;
                    }
                }
            }
        }
    }

    public static int addPoints(Player[] players){
        ArrayList<Card> remaining = new ArrayList<>();
        int points = 0;
        for (Player p: players){
            for (Card c: p.hand){
                remaining.add(c);
            }
        }
        for (Card c: remaining){
            points = points + cardPoints(c);
        }
        return points;
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
