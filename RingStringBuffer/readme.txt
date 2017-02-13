import javax.annotation.concurrent.ThreadSafe;

/**
 * A ring string buffer with a initial size.
 * When there is enough space in the buffer, append the input string.
 * When there is not enough space in the buffer, double the size.
 *
 * The class should be thread safe.
 *
 * Example:
 * Assume initial size = 5.
 *
 * RingStringBuffer buffer = new RingStringBuffer(5);
 * buffer.put("abc"); // abc
 * buffer.get(1); -> return "a".
 * buffer.put("def"); // bcdef
 * buffer.put("aaa"); // bcdefaaa. When the size is not enough, double the size. size=10
 * buffer.size(); -> return 10;
 * buffer.get(6); -> return "bcdefa"
 * buffer.size(); -> return 10;
 * buffer.put("aaa")// aaaaa
 * buffer.get(10); -> throw exception
 * buffer.get(5) -> return "aaaaa"
 *
 */
@ThreadSafe
public class RingStringBuffer {

  /**
   * Constructor.
   * @param size Initial size of the StringBuffer.
   */
  public RingStringBuffer(int size){}


  /**
   * Put input string to the buffer.
   * @param input input string.
   */
  public void put(String input){}


  /**
   * Return the string with length from buffer.
   *
   * @param length the length of result string.
   * @return the result string.
   */
  public String get(int length){}


  /**
   * @return return the size of the buffer.
   */
  public int size(){}

}