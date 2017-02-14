import com.peabee.RingStringBuffer;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by pengbo on 17-2-13.
 */
public class RingStringBufferTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void ringStringBufferTest(){
        RingStringBuffer buffer = new RingStringBuffer(5);
        buffer.put("abc"); // abc
        Assert.assertEquals("abc",buffer.toString());

        Assert.assertEquals("a",buffer.get(1)); //-> return "a".

        buffer.put("def"); // bcdef
        buffer.put("aaa"); // bcdefaaa. When the size is not enough, double the size. size=10
        Assert.assertEquals(10,buffer.size()); //-> return 10;


        Assert.assertEquals("bcdefa",buffer.get(6)); //-> return "bcdefa"

        Assert.assertEquals(10,buffer.size()); // -> return 10;
        buffer.put("aaa");// aaaaa

        Assert.assertEquals("aaaaa",buffer.toString());

        thrown.expect(IllegalArgumentException.class);
        buffer.get(10);// -> throw exception

        Assert.assertEquals("aaaaa",buffer.get(5)); //-> return "aaaaa"//
    }
}
