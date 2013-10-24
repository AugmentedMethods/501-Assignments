import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sean
 * Date: 10/24/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldExaminationTest {
    @Test
    public void testFieldExamination() throws Exception {
        Object obj = new Object();
        Class classObj = obj.getClass();
        ClassDetails classDetails = new ClassDetails();

        FieldExamination testObj = new FieldExamination(classObj, classDetails, obj, true) ;
        assertTrue("Pass", true);
    }
}
