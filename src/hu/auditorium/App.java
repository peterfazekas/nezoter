package hu.auditorium;

import hu.auditorium.controller.AuditoriumFacade;
import hu.auditorium.controller.service.AuditoriumService;
import hu.auditorium.controller.service.CategoryService;
import hu.auditorium.controller.service.ChairService;
import hu.auditorium.model.domain.Chair;
import hu.auditorium.model.service.*;
import hu.auditorium.view.AppRunner;

import java.util.List;
import java.util.Scanner;

public class App {

    private final AppRunner appRunner;

    private App() {
        DataApi<Chair> data = new DataApi<>(new FileDataReader(), new ChairParser(new ListTransformer()));
        DataWriter dataWriter = new FileDataWriter("szabad.txt");
        List<Chair> chairs = data.getData("foglaltsag.txt", "kategoria.txt");
        AuditoriumService auditoriumService = new AuditoriumService(chairs);
        CategoryService categoryService = new CategoryService(chairs);
        ChairService chairService = new ChairService(chairs);
        AuditoriumFacade auditorium = new AuditoriumFacade(dataWriter, auditoriumService, categoryService, chairService);
        Console console = new Console(new Scanner(System.in));
        appRunner = new AppRunner(auditorium, new PositionParser(console));
    }

    public static void main(String[] args) {
        new App().appRunner.run();
    }

}
