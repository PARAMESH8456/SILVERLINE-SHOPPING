package SilverlineShopping.Cart;


import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InputOutput {
    private static final String file = "cart.txt";

    public static void writeJson(ArrayList<Cart.cartItem> items){
        Gson gson = new Gson();
        Writer w = null;
        ArrayList<Cart.cartItem> set = readJson();
        set.addAll(items);
        try {
            w = new FileWriter(file);
            gson.toJson(set,w);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert w != null;
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void rewriteJson(ArrayList<Cart.cartItem> items){
        Gson gson = new Gson();
        Writer w = null;
        try {
            w = new FileWriter(file);
            gson.toJson(items,w);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert w != null;
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearJson(){
        File f = new File(file);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Cart.cartItem> readJson(){
        ArrayList <Cart.cartItem> itemList = new ArrayList<>();
        Gson gson = new Gson();
        Reader r = null;
        try {
            r = new FileReader(file);
            Cart.cartItem[] items = gson.fromJson(r,Cart.cartItem[].class);
            if(items == null){
                System.out.println("No items in cart");
            }else{
                itemList.addAll(Arrays.asList(items));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                assert r != null;
                r.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return itemList;
    }
}
