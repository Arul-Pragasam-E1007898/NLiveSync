package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.bean.CsvBindByName;

public class NLiveContact {
    @CsvBindByName(column = "email", required = true)
    private String email;
    @CsvBindByName(column = "first_name", required = false)
    private String firstName;
    @CsvBindByName(column = "last_name", required = false)
    private String lastName;
    @CsvBindByName(column = "ncash", required = true)
    private String nCash;

    public ObjectNode toJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("emails", email);
        objectNode.set("data", getData(objectMapper));
        return objectNode;
    }

    private ObjectNode getData(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("first_name", firstName);
        objectNode.put("last_name", lastName);
        objectNode.set("custom_field", getCustomField(objectMapper));
        return objectNode;
    }

    private ObjectNode getCustomField(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("ncash", nCash);
        return objectNode;
    }
}
