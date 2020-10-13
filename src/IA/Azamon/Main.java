package IA.Azamon;

import java.util.*;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
    	System.out.println("###########################################");
    	System.out.println("                                           ");
    	System.out.println( 
    			"  /_\\   ____ __ _  _ __ ___    ___   _ __  \n" + 
    			" //_\\\\ |_  // _` || '_ ` _ \\  / _ \\ | '_ \\ \n" + 
    			"/  _  \\ / /| (_| || | | | | || (_) || | | |\n" + 
    			"\\_/ \\_//___|\\__,_||_| |_| |_| \\___/ |_| |_|");
    	System.out.println("                                           ");
    	System.out.println("###########################################");
        System.out.println("### 1.	Create an Experiment		###");
        System.out.println("### 2.	Run an Experiment		###");
        System.out.println("### 3.	Exit				###");
        System.out.println("###########################################");
        switch(scan.nextInt()){
            case 1:
                Create.main(args);
                break;
            case 2:
                Experiments.main(args);
                break;
            case 3:
                scan.close();
                return;
            default:
                main(args);
        }
    }
}
