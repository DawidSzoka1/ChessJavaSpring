package com.chessd.chess.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class MovesConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        try{
            return objectMapper.writeValueAsString(stringList);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException("Error converting list of moves to json " + e.getMessage());
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        try{
            return objectMapper.readValue(s, List.class);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException("Error converting string to list of moves " + e.getMessage());
        }
    }
}
