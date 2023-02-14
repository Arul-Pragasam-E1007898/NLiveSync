import java.io.FileReader;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.bean.CsvToBeanBuilder;
import entities.NLiveContact;

public class Reader {
    private final LinkedBlockingQueue q;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Reader(LinkedBlockingQueue q){
        this.q = q;
    }

    public void read(){
        try {
            List<NLiveContact> contacts = new CsvToBeanBuilder(new FileReader(Constants.FILE)).withType(NLiveContact.class).build().parse();
            int counter = 1;
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (NLiveContact contact : contacts) {
                arrayNode.add(contact.toJson());
                if(counter%90==0) {
                    flush(arrayNode);
                    arrayNode = objectMapper.createArrayNode();
                }
                counter++;
            }
            flush(arrayNode);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void flush(ArrayNode arrayNode) {
        try {
            if(arrayNode.size()>0) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.set("contacts", arrayNode);
                q.put(objectMapper.writeValueAsString(objectNode));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
