import java.util.Scanner;

public class Lab_3 {

    static String Choice() {
        Scanner in = new Scanner(System.in);
        String s;
        s = in.nextLine();
        return s;
    }

    static void GenerateMenu() {
        Scanner in = new Scanner(System.in);
        String message;
        System.out.print("Введите сообщение: ");
        message = in.nextLine();
        RSA rsa = new RSA();
        do {
            rsa.performPQ();
            rsa.calculateR();
            rsa.hashFunc(message);
            rsa.funcEuler(rsa.getP(), rsa.getQ());
            rsa.generateE();
            rsa.calculateD();
        } while (rsa.getD() < 0);
        rsa.calculateSignature();
        System.out.println("Открытый ключ = (" + rsa.getE() + ", " +rsa.getR() +")");
        System.out.println("Закрытый ключ = (" + rsa.getD() + ", " +rsa.getR() +")");
        System.out.println("Отправленные данные = (" + rsa.getM() + ", " +rsa.getSignature() +")");
    }

    static void SetMenu() {
        Scanner in = new Scanner(System.in);
        String message;
        RSA rsa = new RSA();
        System.out.print("Введите сообщение: ");
        message = in.nextLine();
        System.out.print("Введите p: ");
        rsa.setP(in.nextInt());
        System.out.print("Введите q: ");
        rsa.setQ(in.nextInt());
        rsa.calculateR();
        rsa.hashFunc(message);
        rsa.funcEuler(rsa.getP(), rsa.getQ());
        System.out.print("Введите е: ");
        rsa.setE(in.nextInt());
        rsa.calculateD();
        rsa.calculateSignature();
        System.out.println("Открытый ключ = (" + rsa.getE() + ", " +rsa.getR() +")");
        System.out.println("Закрытый ключ = (" + rsa.getD() + ", " +rsa.getR() +")");
        System.out.println("Отправленные данные = (" + rsa.getM() + ", " +rsa.getSignature() +")");
    }

    static void DecodingMenu() {
        Scanner in = new Scanner(System.in);
        RSA rsa = new RSA();
        String message;
        System.out.println("Введите полученную пару ");
        System.out.print("Введите сообщение: ");
        message = in.nextLine();
        System.out.print("Введите подпись: ");
        rsa.setSignature(in.nextInt());
        System.out.println("Полученные данные = (" + message + ", " +rsa.getSignature() +")");
        System.out.println("Введите ключ");
        System.out.print("Введите e: ");
        rsa.setE(in.nextInt());
        System.out.print("Введите r: ");
        rsa.setR(in.nextInt());
        rsa.hashFunc(message);
        if (rsa.checkSignature()){
            System.out.println("Все хорошо!");
        } else {
            System.out.println("Данные украдены");
        }
    }

    static void MainMenu() {
        String Answer = "0";
        String Ask = "3";
        while (!Answer.equals(Ask)) {
            System.out.println("1. Сформировать подпись");
            System.out.println("2. Проверить подпись");
            System.out.println("3. Выход");
            switch (Answer = Choice()) {
                case "1": {
                    System.out.println("1. Сгенерировать данные");
                    System.out.println("2. Ввести данные");
                    switch (Answer = Choice()) {
                        case "1": {
                            GenerateMenu();
                            break;
                        }
                        case "2": {
                            SetMenu();
                            break;
                        }
                    }
                    break;
                }
                case "2": {
                    DecodingMenu();
                    break;
                }
            }
        }
    }

    public static void main (String [] args){
        MainMenu();
    }
}