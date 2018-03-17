import name.xinmy.totokt.*

fun main(args : Array<String>) { 
 
    var factory = OneTimePasswordFactory("please send your answer to big pig care of the funny farm")
    println(factory.generate())

}