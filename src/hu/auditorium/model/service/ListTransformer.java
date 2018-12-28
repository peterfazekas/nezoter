package hu.auditorium.model.service;

import java.util.List;

public class ListTransformer {

    private static final String DELIMITER = "";

    public String transform(List<String> dataList) {
        return String.join(DELIMITER, dataList);
    }

    public int getColumn(List<String> dataList) {
        return getRow(dataList) > 0 ? dataList.get(0).length() : 0;
    }

    public int getRow(List<String> dataList) {
        return dataList.size();
    }



}

