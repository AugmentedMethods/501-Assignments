import java.awt.Point;
import java.io.*;
import java.util.*;
import org.jdom2.*;
import java.net.Socket;
import BasicObjects.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {

    private List<Object> objList = new ArrayList<Object>();
    private int port = 3332;
    private String server = "localhost";

    public static void main(String[] args) throws Exception {

        Sender send = new Sender();
        send.screenMessage();
        if(send.objList.size() >0)
        {
            send.serialize();
        }
    }
    private void screenMessage()
    {
        boolean exit_flag = false;

        System.out.println("Create an Object");
        try {
            System.out.println("Select Object Type");
            System.out.println("1. Simple Primitive Object(int, int)");
            System.out.println("2. Object with Reference to another (int, int)");
            System.out.println("3. Object with Array of Integer (int ... args");
            System.out.println("4. Array of References(Simple Object[])");
            System.out.println("5. Java Collection Class(Simple Object ... args)");
            System.out.println("6. Exit");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
                    System.in));
            String input = bufferRead.readLine();

            switch(input.charAt(0)){
                case('1'):
                    SimpleObjectWithPrimitives obj = new SimpleObjectWithPrimitives(1,1);
                    objList.add(obj);
                    break;

                case('2'):
                    createReferenceObject();
                    break;

                case('3'):
                    createSimpleArray();
                    break;

                case('4'):
                    createReferenceArray();
                    break;

                case('5'):
                    createCollectionArray();
                    break;

                default:
                    System.out.println("invalid input");
                    screenMessage();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serialize() throws IOException,
            Exception {
        System.out.println("Serialize and transfer to receiver?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
                System.in));
        String input = bufferRead.readLine();
        if (input.equals("1")) {
            for (Object obj : objList) {
                Document doc = Serializer.serialize(obj);
                File file = createFile(doc);
                transferFile(server, port, file);
            }
        } else if (input.equals("2")) {

        } else {
            System.out.println("Invalid input");
        }
    }

    public File createFile(Document doc) throws IOException {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        File file = new File("send_data.xml");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        out.output(doc, writer);
        writer.close();
        return file;
    }

    private void transferFile(String server, int port, File file) {
        System.out.println("Transferring file...");
        try {
            Socket s = new Socket(server, port);
            OutputStream output = s.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            s.close();
            System.out.println("Transfer Complete");
        } catch (IOException e) {
            System.out.println("connection refused");
        }
    }













    private void createCollectionArray() throws IOException {

        System.out.println("Enter length of array");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
                System.in));
        String input = bufferRead.readLine();
        int length = Integer.parseInt(input);
        SimpleObjectWithPrimitives[] simpleObjArray = new SimpleObjectWithPrimitives[length];
        for (int i = 0; i < length; i++) {
            SimpleObjectWithPrimitives obj = createSimpleObject();
            simpleObjArray[i] = obj;
        }
        CollectionClassOfReferences obj = new CollectionClassOfReferences(simpleObjArray);
        objList.add(obj);
    }

    private void createSimpleArray() throws IOException {
        System.out.println("SimpleObjectWithPrimitives separated by a ','");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
                System.in));
        String input = bufferRead.readLine();
        String[] fields = input.split(",");
        int[] numbers = new int[fields.length];
        for (int i = 0; i < numbers.length; i++) {
            try {
                numbers[i] = Integer.parseInt(fields[i]);
            } catch (Exception e) {
                System.out.println("Enter only Numbers");
                createSimpleArray();
            }
        }
        Object obj = new ObjectPrimitiveArray(numbers);
        this.objList.add(obj);
    }

    private void createReferenceArray() throws IOException {
        System.out.println("Enter length of array");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
                System.in));
        String input = bufferRead.readLine();
        int length = Integer.parseInt(input);
        SimpleObjectWithPrimitives[] simpleObjArray = new SimpleObjectWithPrimitives[length];
        for (int i = 0; i < length; i++) {
            SimpleObjectWithPrimitives obj = createSimpleObject();
            simpleObjArray[i] = obj;
        }
        ArrayObjectReference obj = new ArrayObjectReference(simpleObjArray);
        objList.add(obj);
    }

    private void createReferenceObject() throws IOException {
        SimpleObjectWithPrimitives simpleObj = createSimpleObject();
        Object obj = new ConnectedObjects(simpleObj);
        objList.add(obj);
    }

    private SimpleObjectWithPrimitives createSimpleObject() throws IOException {
        boolean flag = false;
        while (!flag) {
            System.out.println("Enter values such as (1,2)");
            BufferedReader bufferRead = new BufferedReader(
                    new InputStreamReader(System.in));
            String input = bufferRead.readLine();
            String[] fields = input.split(",");
            if (fields.length != 2) {
                System.out.println("You must enter exactly 2 parameters");
            } else {
                boolean bool = false;
                try {
                    int i = Integer.parseInt(fields[0]);
                    int j = Integer.parseInt(fields[1]);
                    SimpleObjectWithPrimitives obj = new SimpleObjectWithPrimitives(i, j);
                    return obj;
                } catch (Exception e) {
                    System.out.println("You must enter a number");
                }
            }
        }
        return null;
    }
}