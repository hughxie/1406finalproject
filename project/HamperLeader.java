public class HamperLeader extends Player{

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



  public boolean play(
    DiscardPile discardPile,
	  Stack<Card> drawPile,
		ArrayList<Player> players){
      // return true if player wins game by playing last card
    	// returns false otherwise
    	// side effects: plays a card to top of discard Pile, possibly taking zero
    	//               or more cards from the top of the drawPile
    	//               card played must be valid card


    }
}
