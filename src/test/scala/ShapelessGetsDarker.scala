import org.scalatest.{Matchers, FlatSpec}
import shapeless._
import shapeless.ops.hlist._
import shapeless.LUBConstraint._

class ShapelessGetsDarker extends FlatSpec with Matchers{
  
  trait Fruit
  case object Apple extends Fruit
  case object Orange extends Fruit
 
  case class Owner(name:String, lastName:String)
 
  def fruitViewer(f:Fruit) = f.toString
  
  //this BadBasket does not even work, because the implcit is impossible to resolve
  //it will only compile as long as we do not use it!
  class BadBasket(val owner:Owner, val contents :HList = HNil){
    def add(f:Fruit)(implicit e:Prepend[HList,Fruit::HNil]) = new BadBasket(owner,f +: contents)
  }

  //This one will actually keep types and work, but we have to move into generics land
  //along with having the implicits. It sure looks that we are starting to do some contortions
  case class Basket[T<:HList](owner:Owner, contents :T = HNil){
    def add[R](f:R)(implicit e:Prepend[T,R::HNil]) = new Basket(owner, contents:+f )
    def reverse(implicit e:Reverse[T]) = new Basket(owner,contents.reverse)
  }
  
  it should "need some boilerplate to even be able to add something" in {
    val basket = new Basket(Owner("Tyrion", "Lannister"))
    val basketWithStuff = basket.add("a cat").add(Apple).add(Apple)
    //it will not work with badBasket, because it does not have the right types!
    fruitViewer(basketWithStuff.contents.last)
    println(basketWithStuff.reverse)
  }
  
  case class FruitBasket[T<:HList : <<: [Fruit]#λ](owner:Owner, contents :T = HNil){
    def add(f:Fruit)(implicit e:Prepend[T,Fruit::HNil]) = new FruitBasket(owner, f+: contents )
    //Go combine implicits, we ahve to call the Aux version of reverse, that lets us take the output type
    //all the way to the top method.
    //the regular Reverse would produce an e.Out, that we cannot feed to an implicit in the same parameter list
   def reverse[T0<:HList](implicit e:Reverse.Aux[T,T0], e2: LUBConstraint[T0,Fruit]) =
     new FruitBasket(owner,contents.reverse)
   }

  it should "be able to handle a fruit basket" in {
    //create an empty basket, and it won't even compile!
    val fruitBasket = new FruitBasket(Owner("Joffrey", "Baratheon"),Apple::HNil)
    val basketWithFruit = fruitBasket.add(Apple).add(Orange)
    val moreFruit = basketWithFruit.add(Orange)
    println(moreFruit)
  }
}
