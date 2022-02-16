/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.utils;

import cognizant.com.codechallenge.dto.CompilerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static cognizant.com.codechallenge.utils.UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter;

/**
 * replaceAll(", $", "")
 *
 * @author JIDEX
 */
public class Utils {

    public static String processSource(String methodName, String source, Object inputParam) throws JsonProcessingException {
     StringBuilder codeStore = new StringBuilder("public class CodeRunner{\n");
     codeStore.append(source).append("\n");
     codeStore.append("public static void main(String args[]){\n");
     codeStore.append(methodName).append("(");
     return getMapper().writeValueAsString(codeStore);
    }

    public static Type getCompilerConversionTyper() {
        Type types = new TypeToken<CompilerResponse>() {
        }.getType();
        return types;
    }


    public static Gson getGson() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, getUnixEpochDateTypeAdapter())
                .create();

        return gson;
    }

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }

    public static LocalDate getDeductionDate(LocalDate d) {
        return d.minusMonths(2);
    }

    public static String getLocalDateFormatter(LocalDate d) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd");
        return formatter.format(d);
    }


}
