package EDA.MeowMed.JSON;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapFilter {
    public Map<String, Object> filterMap(Map<String, Object> mapOfObject, String filters) {
        Map<String, Object> filtered = new LinkedHashMap<>();
        List<String> filterList = Arrays.asList(filters.split(","));

        for (String filter : filterList) {
            if (filter.contains(".")) {
                String subObjectName = filter.substring(0, filter.indexOf("."));
                if (!mapOfObject.containsKey(subObjectName)) {
                    throw new IllegalArgumentException("Does not contain " + subObjectName + "!");
                }
                if (!filtered.containsKey(subObjectName)) {
                    Map<String, Object> subObject;
                    try {
                        subObject = (Map<String, Object>) mapOfObject.get(subObjectName);
                    } catch (ClassCastException e) {
                        throw new IllegalArgumentException("Does not contain " + subObjectName + " as an object with children!");
                    }
                    if (filterList.contains(subObjectName)) {
                        filtered.put(subObjectName, mapOfObject.get(subObjectName));
                    } else {
                        String subFields = filterList.stream()
                                .filter(s -> s.contains(subObjectName + "."))
                                .collect(Collectors.joining(","));
                        filtered.put(subObjectName, filterMap(subObject, subFields));
                    }

                }
            } else if (!mapOfObject.containsKey(filter)) {
                throw new IllegalArgumentException("Does not contain " + filter + "!");
            } else if (!filtered.containsKey(filter)) {
                filtered.put(filter, mapOfObject.get(filter));
            }
        }
        return filtered;
    }
}
