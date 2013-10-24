import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/24/13
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */


public class ClassExaminationTest {

    @Test
    public void testObj(){
        Object obj = new Object();
        Class classObj = obj.getClass();

        ClassExamination testObj = new ClassExamination(classObj, new ClassDetails()) ;
        assertTrue("Pass", true);
    }


}
