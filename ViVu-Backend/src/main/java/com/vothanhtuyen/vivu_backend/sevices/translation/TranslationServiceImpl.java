package com.vothanhtuyen.vivu_backend.sevices.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.entities.Translations;
import com.vothanhtuyen.vivu_backend.repositories.TranslationRepository;

@Service
public class TranslationServiceImpl implements TranslationService {
    @Autowired
    private TranslationRepository translationRepository;

    @Override
    public String getTranslation(String tableName, String columnName, Long rowId, String language) {
        try {
            Translations translation = translationRepository.findByTableNameAndColumnNameAndRowIdAndLanguage(tableName, columnName, rowId, language).get();
            return translation.getValue();
        } catch (Exception e) {
        }
        return null;
    }
    
    @Override
    public void updateTranslation(String tableName, String columnName, Long rowId, String language, String value) {
        throw new UnsupportedOperationException("Unimplemented method 'updateTranslation'");
    }

    @Override
    public void insertTranslation(String tableName, String columnName, Long rowId, String language, String value) {
        try {
            Translations translation = new Translations();
            translation.setTableName(tableName);
            translation.setColumnName(columnName);
            translation.setRowId(rowId);
            translation.setLanguage(language);
            translation.setValue(value);
            translationRepository.save(translation);
        } catch (Exception e) {
        }
    }
}
