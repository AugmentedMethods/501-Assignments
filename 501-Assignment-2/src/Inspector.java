import java.lang.invoke.MethodHandle;
import java.lang.reflect.*;
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

    public void inspect(Object obj, boolean recursive)
    {
        ExaminerClass examineObj= new ExaminerClass(obj, recursive);
    }

}
