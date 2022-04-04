import com.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.AESUtils;
import com.util.Base64Util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT ,classes = Application.class)
public class testJunit {

    @Test
    public void test1() throws UnsupportedEncodingException {
        byte[] qwertyuis1 = (new Base64()).decode("qwertyui");
        byte[] qwertyuis2 = Base64.decodeBase64("qwertyui");
        byte[] qwertyuis3 = Base64Util.decode("qwertyui");

//        byte[] Str2 = "qwer".getBytes("UTF-8");
//        byte[] Str1 = "qwer".getBytes("UTF-8");
//        System.out.println(Base64.encodeBase64(Str1));
//        System.out.println(new String (Str2));

        System.out.println(qwertyuis1);
        System.out.println(new String((new Base64()).encode(qwertyuis1)));
        System.out.println(qwertyuis2);
        System.out.println(new String(Base64.encodeBase64(qwertyuis1)));
        System.out.println(qwertyuis3);
        System.out.println(Base64Util.encode(qwertyuis3));

        System.out.println(Base64Util.encode((qwertyuis1)));
    }


    @Test
    public void test2() throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidKeyException {
//        HashMap hashMap = new HashMap();
//        hashMap.put("1",123);
//        String z = Base64Util.encode(AESUtils.encryptAES(new ObjectMapper().writeValueAsString(hashMap),"1231231231231231"));
//        System.out.println(z);
//        String param = new String(AESUtils.decryptAES(z, "1231231231231231"), "utf-8");
//        System.out.println(param);
        URL url = ResourceUtils.getURL("classpath:static/123.txt");
        System.out.println(url);
        File url1 = ResourceUtils.getFile("classpath:static/123.txt");
        System.out.println(url1);

        File targetFile = new File("/home/zihexin/servers/pics/merchantimg/", "123.1");
        if (!targetFile.isDirectory()) {
            File f = new File("/home/zihexin/servers/pics/merchantimg/");
            f.mkdirs();
        } else {
            targetFile.mkdirs();
        }
        Files.copy(url1.toPath(), targetFile.toPath());
    }
}

