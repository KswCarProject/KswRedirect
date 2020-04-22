package projekt.redirector.utils

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.Environment
import android.os.ParcelFileDescriptor
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import projekt.redirector.R
import java.io.File
import java.io.FileOutputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


object ParseConfig {

    fun read(location: String, packageName: String): List<String>? {
        return try {
            val fXmlFile = File(location)
            val dbFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
            val dBuilder: DocumentBuilder = dbFactory.newDocumentBuilder()
            val doc: Document = dBuilder.parse(fXmlFile)
            doc.documentElement.normalize()
            val nodeList: NodeList = doc.getElementsByTagName("redirect")
            val list: MutableList<String> = ArrayList()

            for (temp in 0 until nodeList.length) {
                val node: Node = nodeList.item(temp)
                if (node.nodeType.toInt() == Node.ELEMENT_NODE.toInt()) {
                    val element: Element = node as Element
                    if (element.getAttribute("name") == packageName) {
                        list.add(element.firstChild.nodeValue)
                        return list
                    }
                }
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun copyFromRaw(applicationContext : Context) {
        val file =
            File(Environment.getExternalStorageDirectory().absolutePath + "/redirects.xml")
        if (!file.exists()) {
            val input = applicationContext.resources.openRawResource(R.raw.redirects)
            val output = FileOutputStream(file)

            val buffer = ByteArray(1024)
            while (true) {
                val size = input.read(buffer)
                if (size == -1) {
                    break
                }
                output.write(buffer, 0, size)
            }

            input.close()
            output.close()
        }
    }
}