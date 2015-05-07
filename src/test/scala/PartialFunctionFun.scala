import org.scalatest.{Matchers, FlatSpec}

case class Person(name:String)
case class Location[T](address:String,code:T)

object FeedMe{
  //def apply(p:Person) = p.name + " was yummy"
  def apply(f: (Location[_]) => Person) = f(Location[Int]("my house",42)).name + " was yummy"
}

class PartialFunctionFun extends FlatSpec with Matchers{
  it should "find the right apply method" in {
    FeedMe{
      case Location(address,_) => Person("someone in " + address)
    } should be("someone in my house was yummy")
  }
}
