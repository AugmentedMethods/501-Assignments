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

    public CollectionClassOfReferences (SimpleObjectWithPrimitives ... args)
    {
        listOfObj = new ArrayList<Object>();
        for(SimpleObjectWithPrimitives s : args)
            listOfObj.add(s);

    }
}
