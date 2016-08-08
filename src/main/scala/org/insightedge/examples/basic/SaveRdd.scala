package org.insightedge.examples.basic

import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.basic._

import scala.util.Random

/**
  * Generates 100000 Products, converts to Spark RDD and saves to Data Grid. Products have fixed IDs.
  */
object SaveRdd {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0) args else Array("spark://127.0.0.1:7077", "insightedge-space", "insightedge", "127.0.0.1:4174")
    if (settings.length < 4) {
      System.err.println("Usage: SaveRdd <spark master url> <space name> <space groups> <space locator>")
      System.exit(1)
    }
    val Array(master, space, groups, locators) = settings
    val config = InsightEdgeConfig(space, Some(groups), Some(locators))
    val sc = new SparkContext(new SparkConf().setAppName("example-save-rdd").setMaster(master).setInsightEdgeConfig(config))

    val products = (1 to 100000).map { i => Product(i, "Description of product " + i, Random.nextInt(10), Random.nextBoolean()) }
    println(s"Saving ${products.size} products RDD to the space")
    val rdd = sc.parallelize(products)
    rdd.saveToGrid()
    sc.stopInsightEdgeContext()
  }

}