package org.example;

import java.io.*;
import java.util.*;

public class ByteStreams {
    public static void main(String[] args) throws IOException
    {
        Scanner s=new Scanner(System.in);
        String str;
        str=s.nextLine();
        int j;
        byte b[]=str.getBytes();
        System.out.println(b.length);
        FileOutputStream fos=new FileOutputStream("f:/s1.txt");
        for(j=0;j< b.length;j++)
        {
            fos.write(b[j]);
        }
        fos.close();

    }

}