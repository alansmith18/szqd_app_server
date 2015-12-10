package test

import java.net.{URL, HttpURLConnection}

import com.szqd.project.common.model.account.AccountEntityDB


/**
 * Created by like on 8/18/15.
 */
object ScalaSpringMvc {
  def main(args: Array[String]) {
        try {
    var addr = "http://localhost:9888/mobile-lock/app-account/create-account.do"
    addr = "http://localhost:9888/mobile-lock/app-account/login-account.do"
    val url = new URL(addr);
    val connection = url.openConnection().asInstanceOf[HttpURLConnection];
    connection.setRequestMethod("POST")
    connection.setDefaultUseCaches(false)
    connection.setReadTimeout(20000)
    connection.setConnectTimeout(20000)
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.connect
    val outputStream = connection.getOutputStream
    val param = new StringBuilder
    param.append("name=18696513717&")
    param.append("password=2222&source=1")
    val parameter = param.toString().getBytes
    outputStream.write(parameter)
    outputStream.flush
    outputStream.close


    val inputStream = connection.getInputStream
    val data = new Array[Byte](1024 * 1000 * 2)
    val readLength = inputStream.read(data, 0, data.length)
    inputStream.close
    val result = new String(data, 0, readLength)
    println(result)
  }
  catch
  {
    case e: Exception => e.printStackTrace();
  };

  //    val connection:HttpURLConnection = null


}

}
