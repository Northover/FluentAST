import northover.*

@Fluent
class Main {
	int a

  public static void main(String[] args){
    def x = new Main().setA(1)
    println x
  }
}