package hu.auditorium.view;

import hu.auditorium.controller.AuditoriumFacade;
import hu.auditorium.model.service.PositionParser;

public class AppRunner {

    private final AuditoriumFacade auditorium;
    private final PositionParser positionParser;

    public AppRunner(AuditoriumFacade auditorium, PositionParser positionParser) {
        this.auditorium = auditorium;
        this.positionParser = positionParser;
    }

    public void run() {
        System.out.println(printTask(2) + MessageKey.QUESTION);
        System.out.println(auditorium.getCertainChairStatus(positionParser.createPosition(MessageKey.ROW_TEXT, MessageKey.COLUMN_TEXT)));
        System.out.println(printTask(3) + auditorium.getAuditoriumStatistic());
        System.out.println(printTask(4) + auditorium.getMostPopularCategory());
        System.out.println(printTask(5) + auditorium.getCurrentIncome());
        System.out.println(printTask(6) + auditorium.getUniqueAvailableChairCount());
        System.out.println(printTask(7) + auditorium.printAuditoriumMap());
    }

    private String printTask(int number) {
        return String.format(MessageKey.TASK_PATTERN, number);
    }
}
