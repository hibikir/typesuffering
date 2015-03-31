import org.scalatest.{FlatSpec, Matchers}
import shapeless._
import shapeless.test.illTyped
import LUBConstraint._
class ShapelessLooksEasy extends FlatSpec with Matchers{
  
  object Cake
  
  it should "be easy to create a type safe HList" in{
    val hlist = 1::1.2::"A gazebo"::Cake ::HNil
    val doubleDoubler:Double=>Double = (d:Double) => d*d
    
    val myString = hlist.tail.tail.head
    //it keeps types!
    doubleDoubler(hlist.tail.head) should be (1.44 +-0.001)
    
    //the wrong type will not compile!
    illTyped(
      """
        doubleDoubler(hlist.tail.tail.head)
      """)
    
    val longerList = hlist :+ "something else"
    // according to IntelliJ what's the type of last below? Say Terrified!
    // hlist.Last.Aux[::[Int, ::[Double, ::[String, ::[ShapelessLooksEasy.this.Cake.type, hlist.Prepend.Aux[HNil, ::[String, HNil], ::[String, HNil]]#Out]]]], hlist.Last.Aux[::[Double, ::[String, ::[ShapelessLooksEasy.this.Cake.type, hlist.Prepend.Aux[HNil, ::[String, HNil], ::[String, HNil]]#Out]]], hlist.Last.Aux[::[String, ::[ShapelessLooksEasy.this.Cake.type, hlist.Prepend.Aux[HNil, ::[String, HNil], ::[String, HNil]]#Out]], hlist.Last.Aux[::[ShapelessLooksEasy.this.Cake.type, hlist.Prepend.Aux[HNil, ::[String, HNil], ::[String, HNil]]#Out], hlist.Last.Aux[::[String, HNil], String]#Out]#Out]#Out]#Out]#Out
    //this happens to resolve to String in the console, eventually. The compiler works very hard at figuring that out. IntelliJ just doesn't
    val last = longerList.last
    last should be ("something else")
    
    //we can even filter an HList by picking up only items matching the given types!
    val justStrings = longerList.filter[String]
    
    def checkListOfStrings(s:List[String]) = s.size ==2
    
    //This is a perfectly valid scala statement, but it's way too much effort for IntelliJ to figure it out as of March 2015 
    assert(checkListOfStrings(justStrings.toList))
  }
  
  it should "only be a bit icky to do soe type tricks" in {
    trait Fruit
    object Apple extends Fruit
    object Orange extends Fruit
    val hlist1 = Apple :: Apple :: Orange ::Apple ::HNil
    val hlist2 = Apple :: Apple :: "A cat pretending to be fruit" ::Orange ::HNil
  
    
    //Lord Sabin, in his wisdom, made the λ symbol mandatory.
    //note that the space between : and <<: is mandatory, as otherwise it thinks tht :<<: is some kind
    // of eldritch operator at a place where only a type is expected
    def fruitChecker[T <:HList : <<:[Fruit]#λ ](fruits: T) = true
   
    fruitChecker(hlist1.reverse)
    illTyped(
      """
        |fruitChecker(hlist2)
      """)
  }
  
}
