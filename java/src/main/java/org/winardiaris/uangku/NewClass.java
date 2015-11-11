/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.winardiaris.uangku;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author ars
 */
public class NewClass {
    public static void main(String[]args) throws Exception{
        String crunchifyValue1 = "This is a simple Example from Crunchify";
        String ecodedValue1 = URLEncoder.encode(crunchifyValue1, "UTF-8");
        String decodedValue1 = URLDecoder.decode(ecodedValue1, "UTF-8");
        System.out.println("crunchifyValue1 after encoding => " + ecodedValue1);
        System.out.println("crunchifyValue1 after decoding (Original Value): => " + decodedValue1);
 
        String crunchifyValue2 = "Hello There, We started accepting Guest-Posts on Crunchify...";
        String encodedValue2 = URLEncoder.encode(crunchifyValue2, "UTF-8");
        String decodedValue2 = URLDecoder.decode(encodedValue2, "UTF-8");
        System.out.println("\ncrunchifyValue2 after encoding => " + encodedValue2);
        System.out.println("crunchifyValue2 after decoding (Original Value) => " + decodedValue2);
        
    }
}
