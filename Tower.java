package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
//  static UserInterface ui = new GUI("Towers of Hanoi");
	static UserInterface ui = new TestUI2();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    for(int i = nDisks; i > 0; i--) {
    	pegs[0].push(i);
    }

  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };

    while (pegs[0].empty() == false || pegs[1].empty() == false) {
      displayPegs();
      int imove = ui.getCommand(moves);
      if (imove == -1)
        return;
      String move = moves[imove];
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
    while(!peg.empty()) helper.push(peg.pop());

    while(!helper.empty()) s += peg.push(helper.pop());

    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk from pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages.
	  if(pegs[from].empty()) {
		ui.sendMessage("Cannot move disk from empty stack");
		return;
	  }
	  if(!pegs[to].empty() && pegs[to].peek() < pegs[from].peek()) {
		  ui.sendMessage("That move is not allowed");
		  return;
	  }
	  pegs[to].push(pegs[from].pop());
  }

  // EXERCISE:  create Goal class.
  class Goal {
    // Data.
	  int num; // number of disks you want to move
	  int fromPeg; // 0 for a, 1 for b, 2 for c
	  int toPeg;  // ditto

    // Constructor.
	  public Goal(int num, int fromPeg, int toPeg) {
		  this.num = num;
		  this.fromPeg = fromPeg;
		  this.toPeg = toPeg;
	  }

    public String toString () {
      String[] pegNames = { "a", "b", "c" };
      String s = "";










      return s;
    }
  }
  


  // EXERCISE:  display contents of a stack of goals
	void displayStackOfGoals(StackInt<Goal> goalStack) {
		StackInt<Goal> helper = new ArrayStack<Goal>();
		String s = "";
		
		while(!goalStack.empty()) {
			Goal goal = helper.push(goalStack.pop());
			s += "\nMove " + goal.num + " disks from " + goal.fromPeg + " to " + goal.toPeg;
		}
		
		while(!helper.empty()) {
			goalStack.push(helper.pop());
		}
		ui.sendMessage(s);
	}


  
  void solve () {
    // EXERCISE
	  StackInt<Goal> goalStack = new ArrayStack<Goal>();
	  goalStack.push(new Goal(nDisks, 0, 2));
	  while(!goalStack.empty()) {
		  displayStackOfGoals(goalStack);
		  displayPegs();
		  Goal goal = goalStack.pop();
		  if(goal.num == 1) {
			  move(goal.fromPeg, goal.toPeg);
		  } else {
			  goalStack.push(new Goal(goal.num-1, 3-(goal.fromPeg + goal.toPeg), goal.toPeg));
			  goalStack.push(new Goal(1, goal.fromPeg, goal.toPeg));
			  goalStack.push(new Goal(goal.num - 1, goal.fromPeg, 3 - (goal.fromPeg + goal.toPeg)));
		  }
	  }
//	  displayPegs();
  }        
}