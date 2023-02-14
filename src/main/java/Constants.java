public interface Constants {
    String END = "$$END";
    String FILE = System.getProperty("FILE", "/Users/apragasam/Documents/Work/Tmp/create_nldata_5L.csv");
    String FS_BASE = System.getProperty("FSBASE","arulpragasam-team.myfreshworks.com");
    String ENDPOINT = "https://"+FS_BASE+"/crm/sales/api/contacts/bulk_upsert";
    String TOKEN = System.getProperty("TOKEN","bjgKf4W4R_WzC6L3Pkfvgw");
    String TOKEN_VALUE = "Token token=" + TOKEN;
    Integer MAX_ATTEMPTS = 5;
}
