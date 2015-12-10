package test



//import org.apache.spark.SparkConf
//import org.apache.spark.api.java.JavaSparkContext
//import org.apache.spark.storage.StorageLevel
//import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by like on 8/9/15.
 */
object ScalaTest {
  def main(args: Array[String]) {

//    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("app1");
//    val sc = new StreamingContext(sparkConf,Seconds(1));
//
////    val lines = sc.textFileStream("http://www.baidu.com");
//    val lines = sc.socketTextStream("localhost",9999,StorageLevel.MEMORY_AND_DISK );
////    val lines = sc.textFileStream("hdfs://localhost:9000/test1/test1.txt");
//    // Split each line into words
////    lines.print();
//    val words = lines.flatMap(_.split(" "));
////    words.print();
//    val wordCounts = words.map(word => (word, 1)).reduceByKey(_ + _);
////    pairs.print();
//
//    wordCounts.print();
//    sc.start();
////    sc.awaitTermination();

//    val a = "hello"//new String("hello")
//    val b = new String("hello")
//    println(a == b)

//    val list = List(1,2,3)
//    val b = list.isInstanceOf[util.ArrayList]
//    println(list)
//    val giftEntity = new GiftEntityDB
//    giftEntity.icon = "http://xxx.jpg"
//    giftEntity.giftName = "红包名称"
//    giftEntity.giftInfo = "红包信息"
//    giftEntity.giftDesc = "红包描述"
//    giftEntity.url = "http://www.baidu.com"
//    val gson = new Gson()
//    val json = gson.toJson(giftEntity);
//    printf(json)



  }


}
