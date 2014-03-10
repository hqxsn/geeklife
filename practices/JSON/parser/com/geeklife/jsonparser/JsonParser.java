package com.geeklife.jsonparser;

import com.google.gson.Gson;
import org.boon.json.ObjectMapper;
import org.boon.json.ObjectMapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    public static String testGson(User user) {
        Gson gson = new Gson();

        String json = gson.toJson(user);

        return json;
    }

    public static String testBoon(User user) {

        ObjectMapper mapper =  ObjectMapperFactory.create();

        return mapper.writeValueAsString(user);
    }

    public static String testGson(List<User> users) {
        Gson gson = new Gson();

        String json = gson.toJson(users);

        return json;
    }

    public static String testBoon(List<User> users) {

        ObjectMapper mapper =  ObjectMapperFactory.create();



        return mapper.writeValueAsString(users);
    }

    public static FacebookMsgContainer  toMsgByBoon(String json) {

        ObjectMapper mapper =  ObjectMapperFactory.create();

        FacebookMsgContainer msgs = mapper.readValue(json, FacebookMsgContainer.class);
        return null;
    }

    public static FacebookMsgContainer toMsgByGson(String json) {
        Gson gson = new Gson();

        FacebookMsgContainer msgs = gson.fromJson(json, FacebookMsgContainer.class);
        return null;
    }

	public static void main(String[] args) {

        String json = "{ \"data\": [\n" +
                "      {\n" +
                "         \"id\": \"X999_Y999\",\n" +
                "         \"from\": {\n" +
                "            \"name\": \"Tom Brady\", \"id\": \"X12\"\n" +
                "         },\n" +
                "         \"message\": \"Looking forward to 2010!\",\n" +
                "         \"actions\": [\n" +
                "            {\n" +
                "               \"name\": \"Comment\",\n" +
                "               \"link\": \"http://www.facebook.com/X999/posts/Y999\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"name\": \"Like\",\n" +
                "               \"link\": \"http://www.facebook.com/X999/posts/Y999\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"type\": \"status\",\n" +
                "         \"created_time\": \"2010-08-02T21:27:44+0000\",\n" +
                "         \"updated_time\": \"2010-08-02T21:27:44+0000\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\": \"X998_Y998\",\n" +
                "         \"from\": {\n" +
                "            \"name\": \"Peyton Manning\", \"id\": \"X18\"\n" +
                "         },\n" +
                "         \"message\": \"Where's my contract?\",\n" +
                "         \"actions\": [\n" +
                "            {\n" +
                "               \"name\": \"Comment\",\n" +
                "               \"link\": \"http://www.facebook.com/X998/posts/Y998\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"name\": \"Like\",\n" +
                "               \"link\": \"http://www.facebook.com/X998/posts/Y998\"\n" +
                "            }\n" +
                "         ],\n" +
                "         \"type\": \"status\",\n" +
                "         \"created_time\": \"2010-08-02T21:27:44+0000\",\n" +
                "         \"updated_time\": \"2010-08-02T21:27:44+0000\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";



        User user = new User();
        user.setUserId(1000l);
        user.setUserName("Andy Song");
        user.setTelephone("1234334");


        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user);
        users.add(user);

        for (int j = 0; j < 10; j++) {

            System.out.println();

            System.out.println("round:" + j + " begin");

            long startInMilli = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++)
                testBoon(users);
            System.out.println("toJsonByBoon:" + (System.currentTimeMillis() - startInMilli) + " ms");

            System.gc();
            System.gc();

            startInMilli = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++)
                testGson(users);
            System.out.println("toJsonByGson:" + (System.currentTimeMillis() - startInMilli) + " ms");

            System.gc();
            System.gc();

            startInMilli = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++)
                toMsgByBoon(json);
            System.out.println("toMsgByBoon:" + (System.currentTimeMillis() - startInMilli) + " ms");

            System.gc();
            System.gc();

            startInMilli = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++)
                toMsgByGson(json);
            System.out.println("toMsgByGson:" + (System.currentTimeMillis() - startInMilli) + " ms");

            System.gc();
            System.out.println("round:" + j + " end");
        }

    }
}
