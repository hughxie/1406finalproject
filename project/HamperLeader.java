import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;

public class HamperLeader extends Player{
  boolean hasNotPlayed =true ;

  public HamperLeader(Card[] cards){
    this.hand = new ArrayList<Card>(Arrays.asList(cards));
  }


  public boolean play(
    DiscardPile discardPile,
	  Stack<Card> drawPile,
		ArrayList<Player> players){
      // return true if player wins game by playing last card
    	// returns false otherwise
    	// side effects: plays a card to top of discard Pile, possibly taking zero
    	//               or more cards from the top of the drawPile
    	//               card played must be valid card
      /*
    `HamperLeader` will try to hamper the progress of the
    leader if the leader is either the next or previous
    player.  If the next player is the leader (least amount
    of cards) then this player will try to hamper their
    progress by playing a power card. If the previous player
    is the leader, this player will hold on to their
    power cards until the direction of play is reversed
    and then hamper them (if this player has a seven
    they will change direction so that they can try to hamper
    the leader).

      isLeader.setTrue if next player == lowest cards
      ifLeaderIsNext == false
        if have7 == true
          play 7
        play random card
        not power cards!
      else ifLeaderIsNext == true
        if have2 == tru
          play 2
        if have4 == true
          play 4
        if have8 == true
          play 8
          switch to (random||setupATracker)
      */


      int position = getPosition(players);

      if (previousCard.getRank() == 2){
          hand.add(drawPile.pop());
          hand.add(drawPile.pop());
      }

      do{
        if(nextIsLeader(players,position)){
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() == 2 && playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              break;
            }
          }
          if(!hasNotPlayed){
            for(int i =0; i<hand.size(); ++i){
              if(hand.get(i).getRank() == 4 && playable(hand.get(i),discardPile)){
                discardPile.add(this.hand.remove(i));
                break;
              }
            }
          }
        }
        else if(previousIsLeader(players,position)){
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() == 7 && playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              break;
            }
          }
        }
        else{
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() != 7 && hand.get(i).getRank() != 2 && (hand.get(i).getRank() != 4 && playable(hand.get(i),discardPile))){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              break;
            }
          }
        }
        if (hasNotPlayed && !drawPile.isEmpty()){
          Card pickup;
          pickup = drawPile.pop();
          hand.add(pickup);
        }
        else{
          for(int i =0; i<hand.size(); ++i){
            if(playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              break;
            }
          }
          if(hasNotPlayed){
            System.out.println("This player has passed");
            hasNotPlayed = false;
          }
        }
      }while(hasNotPlayed);

      if(this.hand.size()==0){
        return true;
      }
      return false;
    }

    // returns true if the next player is in the lead
    private boolean nextIsLeader(ArrayList<Player> players, int myPosition){
      int nextPosition = 0;
      if(myPosition==players.size()-1){
        nextPosition = 0;
      }
      else{
        nextPosition = myPosition + 1;
      }
      for(int i=0;i <players.size(); ++i){
        if (players.get(i).getSizeOfHand()>players.get(nextPosition).getSizeOfHand()){
          return false;
        }
      }
      return true;
    }
    // returns true if the previous player is in the lead
    public boolean previousIsLeader(ArrayList<Player> players, int myPosition){
      int previousPosition = 0;
      if(myPosition==0){
        previousPosition = players.size()-1;
      }
      else{
        previousPosition = myPosition - 1;
      }
      for(int i=0;i <players.size(); ++i){
        if (players.get(i).getSizeOfHand()>players.get(previousPosition).getSizeOfHand()){
          return false;
        }
      }
      return true;
    }

    public int getPosition(ArrayList<Player> players){
      int myPosition = 0;
      while(!(players.get(myPosition).equals(this))){
        ++myPosition;
      }
      return myPosition;
    }

    public boolean playable(Card cardToPlay,DiscardPile discardPile){
      if (cardToPlay.getRank() == discardPile.top().getRank() || cardToPlay.getSuit().equals(discardPile.top().getSuit())){
          return true;
      }

      return false;
    }






}
