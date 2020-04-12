package database;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDBContainRelative {
    List<IDBEntityFactory> getRelativeFactories();
    Map<String, List<? extends DBEntity>> getRelativeValues();
}
