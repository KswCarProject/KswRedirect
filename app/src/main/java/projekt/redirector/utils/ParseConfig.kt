package projekt.redirector.utils

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


object ParseConfig {

    fun read(location: String, packageName: String): List<String> {
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
                    }
                }
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList()
        }
    }
}