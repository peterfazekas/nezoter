package hu.auditorium.model.service;

import java.util.List;

public class DataApi<T> {

    private final DataReader dataReader;
    private final DataParser<T> dataParser;

    public DataApi(DataReader dataReader, DataParser<T> dataParser) {
        this.dataReader = dataReader;
        this.dataParser = dataParser;
    }

    public List<T> getData(String availabilityInput, String categoryInput) {
        List<String> availabilityList = dataReader.read(availabilityInput);
        List<String> categoryList = dataReader.read(categoryInput);
        return dataParser.parse(availabilityList, categoryList);
    }
}
