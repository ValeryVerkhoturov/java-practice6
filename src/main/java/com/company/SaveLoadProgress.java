package com.company;

import com.company.objects.Company;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.Optional;
import java.util.logging.Logger;

@UtilityClass
public class SaveLoadProgress {

    @SneakyThrows
    public synchronized void saveProgress(Company company, Logger log) {
        if (!new File(Resources.getProperty("savePath")).mkdirs())
            log.severe("Директория не создана");
        File file = new File(Resources.getProperty("savePath") + Resources.getProperty("saveFile"));
        while (!file.createNewFile())
            if (!file.delete()) {
                log.severe("Не удалось сохранить прогресс.");
                System.out.println("Не удалось сохранить прогресс.");
                return;
            }

        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        @Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(company);
        objectOutputStream.flush();
        log.info("Сохранение прошло успешно.");
        System.out.println("Сохранение прошло успешно.");
    }

    @SneakyThrows
    public synchronized Optional<Company> loadProgress(Logger log) {
        if (!new File(Resources.getProperty("savePath")).mkdirs())
            log.severe("Директория не создана");
        File file = new File(Resources.getProperty("savePath") + Resources.getProperty("saveFile"));
        if (!file.exists()){
            log.info("Сохранение не существует.");
            System.out.println("Сохранение не существует.");
            return Optional.empty();
        }

        @Cleanup FileInputStream fileInputStream  = new FileInputStream(file);
        @Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Company company = (Company) objectInputStream.readObject();
        log.info("Загрузка прошла успешно.");
        System.out.println("Загрузка прошла успешно.");
        return Optional.of(company);
    }
}
