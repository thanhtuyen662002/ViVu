package com.vothanhtuyen.vivu_backend.sevices.translation;

public interface TranslationService {
    String getTranslation(String tableName, String columnName, Long rowId, String language);
    void updateTranslation(String tableName, String columnName, Long rowId, String language, String value);
    void insertTranslation(String tableName, String columnName, Long rowId, String language, String value);
    Long getLocationIdByName(String name);
}
