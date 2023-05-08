package com.lambdaschool.notetaker

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkAdapter {
    val GET = "GET"
    val POST = "POST"
    val PUT = "PUT"
    val DELETE = "DELETE"
    val TIMEOUT = 3000

    //@JvmOverloads
    fun httpRequest(stringUrl: String, requestType: String, body: String = ""): String {
        var result = ""
        var stream: InputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(stringUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.readTimeout = TIMEOUT
            connection.connectTimeout = TIMEOUT

            if (requestType == GET || requestType == DELETE) {
                connection.connect()
            } else if (requestType == POST || requestType == PUT) {
                val outputStream = connection.outputStream
                outputStream.write(body.toByteArray())
                outputStream.close()
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                stream = connection.inputStream
                if (stream != null) {
                    val reader = BufferedReader(InputStreamReader(stream))
                    val builder = StringBuilder()
                    var line: String
                    while ((line = reader.readLine()) != null) {
                        builder.append(line)
                    }
                    result = builder.toString()
                }
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            result = e.message.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            result = e.message.toString()
        } finally {
            connection?.disconnect()

            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return result
    }
}
