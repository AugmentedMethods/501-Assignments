package BasicObjects;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 11/11/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollectionClassOfReferences {

    private ArrayList<Object> listOfObj;

    public CollectionClassOfReferences ()
    {
        listOfObj = new ArrayList<Object>();
        listOfObj.add(new SimpleObjectWithPrimitives(1,2));
        listOfObj.add(new SimpleObjectWithPrimitives(4,2));

    }
}
