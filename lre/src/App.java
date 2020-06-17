import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        FileIO fileIO = new FileIO("input.txt","output.txt");
        LeftRecursionElimination lre = new LeftRecursionElimination(fileIO.read());
        System.out.println("Чтение данных с файла...");
        fileIO.write(lre.leftRecursionEliminate());
        System.out.println("Запись данных в файл...");
        System.out.println("Успешно!");
    }
}
