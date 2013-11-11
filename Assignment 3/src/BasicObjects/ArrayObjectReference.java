package BasicObjects;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 11/11/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayObjectReference {

    private Object[] arrayReferences;

    public ArrayObjectReference ()
    {
        arrayReferences = new Object[]{new ObjectPrimitiveArray(2,4), new ObjectPrimitiveArray(1,5)};

    }

}
