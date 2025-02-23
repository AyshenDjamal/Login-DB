package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnection.connect();
        Menu();
    }

    public static void Menu(){
        System.out.println("----------MENU-----------");
        System.out.println("1: REGISTER");
        System.out.println("2: SIGN IN");
        System.out.println("3: SIGN OUT");
        System.out.println("0: MANAGE ACCOUNT");


        System.out.print("Secim Et: ");
        Scanner sc = new Scanner(System.in);
        int button = sc.nextInt();

        switch(button){
            case 1: signUp(); break;
            case 2: signIn(); break;
            case 3: signOut();
            default: manageAccount(); break;
        }
    }

    // Qeydiyyatdan Kecmek
    public static void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------Sign UP------------");
        System.out.print("ID: ");
        int i = sc.nextInt();
        sc.nextLine();


        System.out.print("Ad ve Soyad: ");
        String n = sc.nextLine();
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Parol: ");
        String p = sc.nextLine();

        DBConnection.insertData(i, n, e, p);
        Menu();

    }

    //Log in Metodu
    public static void signIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------Sign In---------------");
        System.out.print("Email: ");
        String em = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        if (DBConnection.sign(em, p)) {
            System.out.println("Sisteme xosh geldin!");
        } else {
            System.out.println("Email ve ya Parol yanlisdir!");
            System.out.print("Eger parolu unutmusunuzsa deyishmek isterdinizmi? Beli/Xeyr \nCavab: ");
            String answer = sc.nextLine();

            if (answer.equalsIgnoreCase("Beli")) {
                System.out.print("Yeni Parol-u Daxil Edin: ");
                String newp = sc.nextLine();
                System.out.print("Yeni Parolu Tesdiq Edin: ");
                String confirmp = sc.nextLine();
                if (newp.equals(confirmp)) {
                    DBConnection.updatePassword(em,newp);
                } else {
                    System.out.println("Duzgun Parolu Daxil Edin");
                }
            }
        }
        Menu();
    }

    //Exit ucun
    public static void signOut(){
        Scanner sc = new Scanner(System.in);
        System.out.println("---------Sign Out-----------");
        System.out.print("Cixis etmeyinize eminmisiniz? Beli/Xeyr \nCavab: ");
        String answer = sc.next();

        if(answer.equalsIgnoreCase("Beli")){
            System.out.println("Ugurla cixis edildi");
        }else{
            System.out.println("Hele de sistemdesen");
        }
        Menu();
    }

    //Hesabda Deyishiklik
    public static void manageAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------Manage Account----------");
        System.out.println("1: Update Account");
        System.out.println("2: Delete Account");
        System.out.print("Secim et: ");
        int opt = sc.nextInt();

        switch(opt){
            case 1: update(); break;
            case 2: delete(); break;
            default:
                System.out.println("Duzgun secim edin");
                manageAccount();
        }

    }

    public static void update(){
        Scanner sc = new Scanner(System.in);
        System.out.println("----------Update-----------");

        System.out.print("ID: ");
        int i = sc.nextInt();
        sc.nextLine();


        System.out.print("Ad ve Soyad: ");
        String n = sc.nextLine();
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Parol: ");
        String p = sc.nextLine();

        DBConnection.updateUser(i, n, e, p);
        Menu();

    }

    public static void delete(){
        Scanner sc = new Scanner(System.in);
        System.out.println("----------Delete-----------");

        System.out.print("ID-ni daxil edin: ");
        int i = sc.nextInt();

        System.out.print("User-i silmeyinize eminsiniz? Beli/Xeyr \nCavab: ");
        String opt = sc.next();

        if(opt.equalsIgnoreCase("Beli")){
            DBConnection.deleteUser(i);
        }else {
            System.out.println("Hesabiniz hele de movcuduur!");
        }
        Menu();
    }
}