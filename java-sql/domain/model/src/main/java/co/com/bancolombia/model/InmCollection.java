package co.com.bancolombia.model;

import co.com.bancolombia.model.exceptions.BusinessDetailException;

import java.util.*;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.DATA_VALIDATION_ERROR;

public class InmCollection {

    public static <T> List<T> add(List<T> immutableList, T element) {
        List<T> tmpList = new ArrayList<>(immutableList);
        tmpList.add(element);
        return Collections.unmodifiableList(tmpList);
    }

    public static <T> List<T> delete(List<T> immutableList, T element) {
        List<T> tmpList = new ArrayList<>(immutableList);
        if (!tmpList.remove(element))
            throw new BusinessDetailException(DATA_VALIDATION_ERROR, "Element doesn't exist in list");

        return Collections.unmodifiableList(tmpList);
    }

    /*
    public static <T> Set<T> add(Set<T> immutableSet, T element) {
        Set<T> tmpSet = new HashSet<>(immutableSet);
        if (!tmpSet.add(element))
            throw new IllegalArgumentException("Error, already exists");

        return Collections.unmodifiableSet(tmpSet);
    }

    public static <T> Set<T> delete(Set<T> immutableSet, T element) {
        Set<T> tmpSet = new HashSet<>(immutableSet);
        if (!tmpSet.remove(element))
            throw new IllegalArgumentException("Error, not exists");

        return Collections.unmodifiableSet(tmpSet);
    }
     */
}
