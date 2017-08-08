public class BalancedTest {

  public static String[] strings = {
    "((test)()[]{}{({[(())]})})",
    "(){}[]",
    "test () ----- y x {}[]",
    "([[}]])",
    "())([])[])",
    "([{}()])"

  };

  public static void main(String[] args) {
    System.out.println(Balanced.numberOfBalancedStrings(strings));
  }
}
