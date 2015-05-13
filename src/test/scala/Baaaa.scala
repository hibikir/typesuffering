object Baaaa extends App{


  object Banana {
    import scala.reflect.runtime.universe._

    def getInnerType[T](list:List[T])(implicit tag :TypeTag[T]) = tag.tpe.toString
  }


  val stringList = List("A")
  val stringName = Banana.getInnerType(stringList)

  println( s"a list of $stringName")


}

object Baaaaaaaa extends App {
  object Broccoli {
    import scala.reflect.runtime.universe._

    def gratuitousIntermediateMethod[T](list: List[T])(implicit tag: TypeTag[T]) = Baaaa.Banana.getInnerType(list)
  }
  val stringList = List("A")

  val stringName = Broccoli.gratuitousIntermediateMethod(stringList)

  println( s"a list of $stringName")
}
