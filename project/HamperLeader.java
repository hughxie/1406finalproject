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
      System.out.println("----------------");
      System.out.println(hand);
      System.out.println("----------------");

      //Gets position in list of players
      int position = getPosition(players);

      //Checks if a 2 was played,
      if (discardPile.top().getRank() == 2){
          if(!drawPile.isEmpty()){
            hand.add(drawPile.pop());
          }
          if(!drawPile.isEmpty()){
          hand.add(drawPile.pop());
        }
          System.out.println("----------------");
          System.out.println("HAMPER PICKED UP 2");
          System.out.println("----------------");
          return false;
        }


      do{
        //IF the next person is leader, checks for 2, checks for 4
        if(nextIsLeader(players,position)){
          System.out.println("----------------");
          System.out.println("LEADER IN FRONT");
          System.out.println("----------------");
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() == 2 && playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              System.out.println("----------------");
              System.out.println("HAMPER PLAYED A 2");
              System.out.println("----------------");
              break;
            }
          }
          if(!hasNotPlayed){
            for(int i =0; i<hand.size(); ++i){
              if(hand.get(i).getRank() == 4 && playable(hand.get(i),discardPile)){
                discardPile.add(this.hand.remove(i));
                hasNotPlayed = false;
                System.out.println("----------------");
                System.out.println("HAMPER PLAYED A 4");
                System.out.println("----------------");
                break;
              }
            }
          }
        }
        //If the previous person is leader plays 7
        else if(previousIsLeader(players,position)){
          System.out.println("----------------");
          System.out.println("LEADER BEHIND");
          System.out.println("----------------");
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() == 7 && playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              hasNotPlayed = false;
              System.out.println("----------------");
              System.out.println("HAMPER PLAYED A 7");
              System.out.println("----------------");
              break;
            }
          }
        }
        if(hasNotPlayed){
          for(int i =0; i<hand.size(); ++i){
            if(hand.get(i).getRank() == 8){
              discardPile.add(this.hand.remove(i));
              System.out.println("----------------");
              System.out.println("HAMPER PLAYED AN 8");
              System.out.println("----------------");
              hasNotPlayed = false;
              break;
            }
            else if(playable(hand.get(i),discardPile)){
              discardPile.add(this.hand.remove(i));
              System.out.println("----------------");
              System.out.println("HAMPER PLAYED A RANDOM CARD");
              System.out.println("----------------");
              hasNotPlayed = false;
              break;
            }
          }
        }
        if (hasNotPlayed && !drawPile.isEmpty()){
          System.out.println("----------------");
          System.out.println("HAMPER PICKED UP A CARD");
          System.out.println("----------------");
          Card pickup;

          pickup = drawPile.pop();

          if(playable(pickup,discardPile)){
            discardPile.add(pickup);
            System.out.println("----------------");
            System.out.println("HAMPER PLAYED A RANDOM CARD");
            System.out.println("----------------");
            hasNotPlayed = false;
            break;
          }
          else{
            hand.add(pickup);
          }
        }

          if(hasNotPlayed){
            System.out.println("----------------");
            System.out.println("HAMPER PASSES");
            System.out.println("----------------");
            hasNotPlayed = false;
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
