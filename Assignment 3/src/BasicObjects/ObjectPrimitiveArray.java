package BasicObjects;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 11/11/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectPrimitiveArray {
    private int[] simpleIntArray;

    public ObjectPrimitiveArray(int... args)
    {
        simpleIntArray = new int[args.length];
        for(int i = 0; i < args.length ; i++)
        {
            simpleIntArray[i] = args[i];
        }
    }
}
