package hu.auditorium.model.service;

import java.util.List;

interface DataParser<T> {

    List<T> parse(List<String> content1, List<String> content2);

}
