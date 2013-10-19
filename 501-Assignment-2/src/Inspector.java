import java.util.ArrayList;
import java.lang.Class;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/18/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class Inspector {

    private ArrayList<String> log;
    private Object obj;
    private Class classObj;

    public void inspect(Object obj, boolean recursive)
    {
        log = new ArrayList<String>();
        this.obj = obj;
        runClassTest();
    }

    private void runClassTest()
    {
        classObj = obj.getClass();
        getClassName();
        getDirectSuperClass();
        getInterfaces();

    }

    private void getClassName()
    {
        log.add(classObj.getCanonicalName());
    }

    private void getDirectSuperClass()
    {
        log.add(classObj.getGenericSuperclass().toString());
    }

    private void getInterfaces()
    {
        if(classObj.getInterfaces().length > 0)
            for(Class<?> c : classObj.getInterfaces() )
                log.add(c.toString());
    }




















    private void runInterfaceTest(String objName)
    {}
}
