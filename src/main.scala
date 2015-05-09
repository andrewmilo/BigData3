import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import scala.collection.mutable.ListBuffer

object main {
  
  def main(args: Array[String]): Unit = {
    
    val conf = new SparkConf().setAppName("Hello").setMaster("local[*]")
    val sc = new SparkContext(conf)
    
    val gene = "gene_.*_gene".r.toString() // Our filter for gene_SOMETHING_gene format
   
    val lines = sc.textFile("project3.dat")
    val totalDocs = lines.count() //Counting the total number of documents
    
    //Word Count
    val counts =   lines.flatMap{
     
                         line => lazy val s = line.split("\\s+")
                         val count = s.length
                         val id = s.head
                         s.filter(word => word.matches(gene)).map(word => ((word, id, count), 1))
                   }.reduceByKey(_+_)
   // counts holds : (term, documentID) => wordCount
    counts.foreach(x => println(x)) 
    
    //val counts2 = counts.map( pair => (pair._1._2, (pair._1._1, pair._2)) ).reduceByKey((a,b) => (a._1,a._2 + b._2))
    
   // counts2.foreach(x => println(x))
                     
                   
  }
}