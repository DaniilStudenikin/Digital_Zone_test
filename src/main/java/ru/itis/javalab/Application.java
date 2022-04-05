package ru.itis.javalab;


import ru.itis.javalab.services.*;
import ru.itis.javalab.sqlite.Database;
import ru.itis.javalab.utils.DatabaseAssistant;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Database.createDB();
        WellService wellService = WellServiceImpl.getInstance();
        EquipmentService equipmentService = EquipmentServiceImpl.getInstance();

        Scanner scanner = new Scanner(System.in);
        commandsPrint();
        while (scanner.hasNext()) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1:
                        System.out.println("Введите количество оборудования которое хотите добавить: ");
                        int equipmentCount = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Введите название скважины куда хотите добавить оборудование: ");
                        String wellName1 = scanner.nextLine();
                        equipmentService.createEquipments(equipmentCount,wellName1);
                        System.out.println("Оборудование добавлено!");
                        break;
                    case 2:
                        System.out.println("Введите названия скважины по которой хотите получить информацию: ");
                        String wellName2 = scanner.nextLine();
                        wellName2 = wellName2.replace(" ",",").replace(",,",",");
                        String[] wells = wellName2.split(",");
                        wellService.findAllInfoAboutEquipments(wells);
                        System.out.println("Готово!");
                        break;
                    case 3:
                        DatabaseAssistant.exportDataFromDBToXML();
                        System.out.println("XML готов! Проверяйте!");
                        break;
                    case 4:
                        System.out.println("Хорошего вам времени суток!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Что-то пошло не так! Попробуйте еще раз!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Неверна введена команда");
                commandsPrint();
                scanner.nextLine();
                continue;
            }
            commandsPrint();
        }
    }
    private static void commandsPrint(){
        System.out.println("Введите номер команды которую хотите выполнить:");
        System.out.println("1. Добавить оборудования на скважину.");
        System.out.println("2. Получить информацию об оборудовании на скважине.");
        System.out.println("3. Выгрузить данные из базы данных в XML");
        System.out.println("4. Закрыть приложение");
    }
}
