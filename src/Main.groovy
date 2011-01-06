import northover.*

/*
*   @author Robert Northover
*/

@Fluent
class Main {
	@Delegate List value
    boolean modified


  public static void main(String[] args){
    def x = new Main().setValue([1, 2, 3]).setModified(true) << 5
    println x
  }
}