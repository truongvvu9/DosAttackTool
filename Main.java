import java.io.*;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    static String ipAddress = "";
    static int byteSize = 65500;
    static String command = "ping -l " + byteSize + " " + ipAddress;

    public static void main(String[] args) throws IOException, InterruptedException {
        menu();
    }
    private static void menu() throws IOException, InterruptedException {
        boolean exit = false;
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("1.Enter ip address manually");
            System.out.println("2.Load ip address from ipaddress.txt");
            boolean hasInt = scanner.hasNextInt();
            if(hasInt){
                int choice = scanner.nextInt();
                scanner.nextLine();
                if(choice == 1){
                    getIpAddress();
                    break;
                }else if(choice == 2){
                    if(loadIPAddressFromText()){
                        break;
                    }
                }else{
                    System.out.println("Invalid choice. Please try again.");
                }
            }else{
                System.out.println("Invalid choice. Please try again.");
            }
        }
        while(true){
            if(exit){
                break;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose one: ");
            System.out.println("1. Lag by seconds");
            System.out.println("2. Lag by minutes");
            System.out.println("3. Lag by hours");
            System.out.println("4. Lag indefinitely");
            System.out.println("5. Exit");
            boolean hasInt = scanner.hasNextInt();
            if(hasInt){
                int input = scanner.nextInt();
                scanner.nextLine();
                switch(input){
                    case 1:
                        pingBySeconds(getInput("seconds"));
                        break;

                    case 2:
                        pingBySeconds(getInput("minutes"));
                        break;

                    case 3:
                        pingBySeconds(getInput("hours"));
                        break;

                    case 4:
                        pingIndefinitely();
                        break;
                    case 5:
                        exit = true;
                        break;

                        default:
                        System.out.println("You did not enter a valid number.");

                }
            }else{
                System.out.println("You did not enter a valid number.");
            }

        }
    }
    private static void getIpAddress() throws IOException {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter ip address: ");
            String input = scanner.nextLine();
            if(checkIfValidIpAddress(input)){
                ipAddress = input;
                System.out.println("Ip address has been entered successfully.");
                break;
            }else{
                System.out.println("Invalid ip address. Please try again.");
            }

        }
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to save this ip address to ipaddress.txt file? (y/n): ");
            String input = scanner.nextLine();
            if(input.equals("y")){
                File file = new File("ipaddress.txt");
                if(file.exists()){
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    bufferedWriter.write(ipAddress);
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    System.out.println("Successfully saved the ip address " + ipAddress + " to ipaddress.txt");
                    break;
                }else{
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ipaddress.txt"));
                    bufferedWriter.write(ipAddress);
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    System.out.println("Successfully saved the ip address " + ipAddress + " to ipaddress.txt");
                    break;
                }
            }else if(input.equals("n")){
                break;
            }else{
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static boolean loadIPAddressFromText() throws IOException {
        File file = new File("ipaddress.txt");
        if(file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.ready()){
                ipAddress = reader.readLine();
                System.out.println("Successfully loaded ip address " + ipAddress + " from ipaddress.txt");
                return true;
            }else{
                System.out.println("Something went wrong when trying to load the ip address from ipaddress.txt");
                return false;
            }
        }else{
            System.out.println("File does not exist!");
            return false;
        }
    }
    private static boolean checkIfValidIpAddress(String ip){
        String[] arrayStrings = ip.split("\\.");
        if(arrayStrings.length == 4){
            boolean result = true;
            for(int i=0; i<arrayStrings.length; i++){
                String numberString = String.valueOf(arrayStrings[i]);
                int numberInt = Integer.parseInt(numberString);
                if(numberInt < 0 ||  numberInt > 255){
                    result = false;
                    break;
                }
            }
            return result;
        }else{
            return false;
        }

    }
    private static long getInput(String timeFormat){
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the number of " + timeFormat + ": ");
            boolean hasLong = scanner.hasNextLong();
            if(hasLong){
                long input = scanner.nextLong();
                scanner.nextLine();
                if(timeFormat.equals("seconds")){
                    return input;
                }else if(timeFormat.equals("minutes")){
                    return input * 60;
                }else if(timeFormat.equals("hours")){
                    return input * 3600;
                }
            }else{
                System.out.println("You did not enter a valid number.");
            }
        }
    }
    private static void pingBySeconds(long timeDuration) throws IOException, InterruptedException {
        Timer timer = new Timer();
        MyTimer myTimer = new MyTimer(timeDuration);
        timer.schedule(myTimer, 0, 1000);
        while(true){
            Process process = Runtime.getRuntime().exec(command);
            if(myTimer.isDone){
                break;
            }
        }
    }
    private static void pingIndefinitely() throws IOException {
        while(true){
            System.out.println("Pinging...");
            Process process = Runtime.getRuntime().exec(command);
        }
    }

}
