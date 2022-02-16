package cognizant.com.codechallenge;

import cognizant.com.codechallenge.model.Languages;
import java.util.Date;
import java.util.List;

public class TestData {
    public static List<Languages> getLanguages(){
       Languages lang = new Languages();
       lang.setDateCreated(new Date());
       lang.setName("Odofin Timothy");
       lang.setPassedName("passed");
       lang.setVersionIndex(1);
       lang.setId(new Long(85));
       return List.of(lang);
    }
}
