import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Console;

class User{
    String username;
    String password;
    String fullName;
    Long accountNumber;
    Long balance;

    User(String username, String password){
        this.username = username;
        this.password = password;
        this.balance = 0L;
    }
}

public class BankingSystem {
    Scanner sc = new Scanner(System.in);
    Console cnsl = System.console();
    User currentUser = null;

    List<User> users = new ArrayList<>();

    public static void main(String[] args){
        BankingSystem bs = new BankingSystem();
        while(true){
            int opt = bs.takeInput("home");
            
            if(opt == 1){
                opt = bs.signIn();

                if(opt == 1){
                    System.out.println("\nSign-In Successful");
                    while(opt != 5){

                        opt = bs.takeInput("MainMenu");
                        switch(opt){
                            case 1: bs.enterDetails();
                                    break;
                            case 2: bs.transaction();
                                    break;
                            case 3: if(bs.currentUser.fullName != null){
                                        bs.displayDetails();
                                    }
                                    else{
                                        System.out.println("User Details Yet to Be Filled");
                                    }
                                    break;
                            case 4: bs.displayBalance();
                                    break;
                        }
                    }
                }

                else if(opt == 0){
                    opt = bs.takeInput("Password");

                    if(opt == 1){
                        int res = bs.resetPassword();
                        if(res == 1){
                            System.out.println("Password Changed Successfully");
                        }
                        else{
                            System.out.println("User Doesn't Exist");
                            continue;
                        }
                    }

                    else if(opt == -1){
                        continue;
                    }
                }

                else if(opt == -1){
                    System.out.println("User Doesn't Exist");
                }

            }

            else if(opt == 2){
                bs.register();
            }

            else if(opt == 3){
                break;
            }
            else{
                System.out.println("Not a Valid Option");
            }
        }
    }

    public int takeInput(String state){
        int opt;
        if(state.equals("MainMenu")){
            
            System.out.println("--------------MENU-------------");
            System.out.println("Select Option: \n1. Enter Details \n2. Perform Transaction \n3. Display Details \n4. Display Balance \n5. Logout");
            opt = sc.nextInt();     sc.nextLine();
            return opt;     
        }
        
        else if(state.equals("Transaction")){
            System.out.println("--------------MENU-------------");
            System.out.println("Enter Operation: \n1. Withdraw\n2. Deposit");
            opt = sc.nextInt();   sc.nextLine();
            return opt;
        }
        
        else if(state.equals("Password")){
            System.out.println("Wrong Password");
            System.out.println("--------------MENU-------------");
            System.out.println("1. Reset Password\n2. Retry");
            opt = sc.nextInt();   sc.nextLine();
            return opt;      
        }
        else if(state.equals("home")){
            System.out.println("--------------MENU-------------");
            System.out.println("Enter Option: \n1. Sign In\n2. Register\n3. Exit");
            opt = sc.nextInt();     sc.nextLine();
            return opt;
        }

        return -1;
    }

    public void enterDetails(){
        System.out.println("Enter FullName: ");
        String name = sc.nextLine();
        System.out.println("Enter Account Number");
        Long accNo = sc.nextLong();     sc.nextLine();
        currentUser.fullName = name;
        currentUser.accountNumber = accNo;

        System.out.println("Details Updated Successfully");
        
    }

    public void transaction(){
        System.out.println("Select Operation: \n1. Withdraw\n2. Deposit");
        int operation = sc.nextInt();   sc.nextLine();
        switch(operation){
            case 1: withdrawMoney();
                    break;
            case 2: depositMoney();
                    break;
        }
    }

    public void displayDetails(){
        System.out.println("FullName: " + currentUser.fullName + "\nAccount Number: " + currentUser.accountNumber + "\nUsername: " + currentUser.username);
    }

    public void displayBalance(){
        System.out.println("Account Number: " + currentUser.accountNumber + "\nCurrent Balance: " + currentUser.balance);
    }

    public void withdrawMoney(){
        System.out.println("Enter Amount to Withdraw: ");
        Long amt = sc.nextLong();     sc.nextLine();
        if(amt < 0){
            System.out.println("Invalid Amount");
            return;
        }
        if(amt > currentUser.balance){
            System.out.println("Insufficient Funds: ");
        }
        else{
            currentUser.balance -= amt;
            System.out.println("Amount Rs." + amt + " Withdrawn Successfully");
        }
    }

    public void depositMoney(){
        System.out.println("Enter Amount to Deposit: ");
        Long amt = sc.nextLong();     sc.nextLine();
        if(amt < 0){
            System.out.println("Invalid Amount");
            return;
        }    
        currentUser.balance += amt;
        System.out.println("Amount Rs." + amt + " Deposited Successfully");
    }

    public void register(){
        System.out.println("Enter Username: ");
        String uname = sc.nextLine();
        System.out.print("Enter Password: ");
        String passwd = String.valueOf(cnsl.readPassword());
        for(int i = 0; i < passwd.length(); i++)    System.out.print("*");
        System.out.println();
        users.add(new User(uname, passwd));
        System.out.println("User Created Successfully");
    }

    public int signIn(){
        System.out.println("Enter Username: ");
        String uname = sc.nextLine();
        System.out.print("Enter Password: ");
        String passwd = String.valueOf(cnsl.readPassword());
        for(int i = 0; i < passwd.length(); i++)    System.out.print("*");
        System.out.println();
        for(User user: users){
            if(user.username.equals(uname) && user.password.equals(passwd)){
                currentUser = user;
                return 1;
            }
            else if(user.username.equals(uname)){
                return 0;
            }
        }
        return -1;
    }

    public int resetPassword(){
        System.out.println("Enter Username: ");
        String uname = sc.nextLine();
        for(User user: users){
            if(user.username.equals(uname)){
                System.out.print("Enter New Password: ");
                String passwd = String.valueOf(cnsl.readPassword());
                for(int i = 0; i < passwd.length(); i++)    System.out.print("*");
                System.out.println();
                user.password = passwd;
                return 1;
            }
        }
        return 0;
    }
}
