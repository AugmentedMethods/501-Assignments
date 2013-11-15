/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 11/15/13
 * Time: 1:42 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.net.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import Inspector.*;

public class Receiver {
    public static void main(String[] args) throws IOException {
        int port = 8880;
        ServerSocket serverSocket = null;
        // set the server socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Failed: " + port);
            System.exit(1);
        }
        while (true) {
            System.out.println("Waiting for client");
            File file = new File("receive_ata.xml");
            // listen for client
            Socket s = null;
            try {
                s = serverSocket.accept();
                System.out.println("Client connected");
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            receiveFile(file, s);
            Object obj = buildObject(file);
            Inspector inspector = new Inspector();
            inspector.inspect(obj, false);
        }
    }

    private static void receiveFile(File file, Socket s) throws IOException,
            FileNotFoundException {
        InputStream input = s.getInputStream();
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[1024 * 1024];

        int bytesReceived = 0;
        System.out.println("receiving file");
        while ((bytesReceived = input.read(buffer)) > 0) {
            out.write(buffer, 0, bytesReceived);
            System.out.println(bytesReceived + " Bytes received");
            break;
        }
    }

    private static Object buildObject(File file) {
        SAXBuilder builder = new SAXBuilder();
        Object obj = null;
        try {
            Document doc = (Document) builder.build(file);
            obj = Deserializer.deserialize(doc);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}