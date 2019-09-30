package com.lambdaschool.notetaker

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkAdapter {
    val GET = "GET"
    val POST = "POST"
    val PUT = "PUT"
    val DELETE = "DELETE"
    val TIMEOUT = 3000

    @JvmOverloads
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
            result = e.message
        } catch (e: IOException) {
            e.printStackTrace()
            result = e.message
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
