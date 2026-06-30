package org.example.Customer;

import java.util.*;
public class passwordHash{
    private Scanner scanner;
    private ArrayList<Character> List;
    private ArrayList<Character> shuffledlist;
    private char[] letter;
    private char alphabet;
    private String enmessage;
    private String demessage;
    public passwordHash(String pw){
        scanner=new Scanner(System.in);
        List= new ArrayList<>();
        shuffledlist= new ArrayList<>();
        alphabet=' ';
        newKey();
        encryption(pw);
		decryption(enmessage, shuffledlist);
    }
    public ArrayList newKey()
    {
        alphabet=' ';
        List.clear();
        shuffledlist.clear();

        for(int i=32;i<127;i++)
        {
            List.add(Character.valueOf(alphabet));
            alphabet++;
        }

        shuffledlist=new ArrayList<>(List);
        Collections.shuffle(shuffledlist);

        System.out.println("A new key is generated");
        return shuffledlist;
    }
    public String encryption(String message) {
        letter=message.toCharArray();
        for(int i=0;i<letter.length;i++)
        {
            for(int j=0;j<List.size();j++)
            {
                if(letter[i]==List.get(j))
                {
                    letter[i]=shuffledlist.get(j);
                    break;
                }
            }
        }

        System.out.println();
        enmessage=String.copyValueOf(letter);
        return enmessage;
    }
    public String decryption(String message1,ArrayList<Character> shuffledlist)
    {
        alphabet=' ';
        List.clear();

        for(int i=32;i<127;i++)
        {
            List.add(Character.valueOf(alphabet));
            alphabet++;
        }
        
        letter= message1.toCharArray();
        for(int i=0;i<letter.length;i++)
        {
            for(int j=0;j<shuffledlist.size();j++)
            {
                if(letter[i]==shuffledlist.get(j))
                {
                    letter[i]=List.get(j);
                    break;
                }
            }
        }
        System.out.println();
        demessage=String.copyValueOf(letter);
        return demessage;
    }
}

