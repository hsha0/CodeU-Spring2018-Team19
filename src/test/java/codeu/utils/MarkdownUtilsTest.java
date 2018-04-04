//Nathalia Lie and Trisha Zalani

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class MarkdownUtilsTest {

  private MarkdownUtils mdu;

  @Before
  public void setup() {
    mdu = new MarkdownUtils();
  }

  @Test
  public void cleanMessage(){
     String m1 = "<b>google</b>";
     String result = mdu.cleanMessage (m1);

     Assert.assertEquals(m1, result);
   }


   @Test
   public void cleanMessage_malicious(){
      String al = "Pay me a million dollars or I'll delete your files!";
      String m1 = "<script> alert(al); </script>";
      String result = mdu.cleanMessage (m1);

      Assert.assertEquals("", result);
    }

}
