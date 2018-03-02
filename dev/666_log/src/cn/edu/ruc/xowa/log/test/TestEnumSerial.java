package cn.edu.ruc.xowa.log.test;

import cn.edu.ruc.xowa.log.page.Url;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestEnumSerial
{
    public static void main(String[] args)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            Url url = new Url("/wiki/test");
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(url);

            byte[] employeeAsBytes = baos.toByteArray();

            ByteArrayInputStream bi = new ByteArrayInputStream(employeeAsBytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            Url url1 = (Url) oi.readObject();

            return;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
