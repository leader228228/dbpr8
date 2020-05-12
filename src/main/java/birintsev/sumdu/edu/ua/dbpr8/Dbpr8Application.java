package birintsev.sumdu.edu.ua.dbpr8;

import birintsev.sumdu.edu.ua.dbpr8.controller.ConsoleCommandsController;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@AllArgsConstructor
@SpringBootApplication
public class Dbpr8Application implements CommandLineRunner {

    private final ConsoleCommandsController controller;

    public static void main(String[] args) {
        SpringApplication.run(Dbpr8Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter directory:");
        String dir = scanner.nextLine();
        controller.migrate(dir);
    }

}
