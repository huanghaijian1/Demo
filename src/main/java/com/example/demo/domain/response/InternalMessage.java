package com.example.demo.domain.response;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@Slf4j
public class InternalMessage {
    private static HashMap<String, InternalMessage> messageHashMap = new HashMap<>();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream inputStream = (classLoader.getResource("internal-message.csv").openStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                String[] content = line.split(",");
                if (content.length < 2)
                    continue;
                if (!messageHashMap.containsKey(content[0])) {
                    messageHashMap.put(content[0], new InternalMessage(content[0], content[1]));
                }
            }
        } catch (IOException e) {
            log.error("Error loading internal-message.csv file");
            System.exit(-1);
        }
    }

    private String code;
    private String msg;

    protected InternalMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessage(String code, Object... msgObjects) {
        if (messageHashMap.containsKey(code)) {
            if (msgObjects == null || msgObjects.length == 0)
                return messageHashMap.get(code).msg;
            String fmt = messageHashMap.get(code).msg;
            return String.format(fmt, msgObjects);
        }else{
            if(msgObjects != null){
                return msgObjects[0].toString();
            }
        }
        return null;
    }
}
