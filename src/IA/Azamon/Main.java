package IA.Azamon;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Select:");
        System.out.println("1.-Create an Experiment");
        System.out.println("2.-Run an Experiment");
        System.out.println("3.-Exit");
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
