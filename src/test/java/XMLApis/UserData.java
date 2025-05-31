package XMLApis;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "objects")
public class UserData {

/*
<objects type="array">
    <object>
        <id type="integer">7889551</id>
        <name>Aadrika Bhattathiri</name>
        <email>aadrika_bhattathiri@nitzsche.test</email>
        <gender>female</gender>
        <status>active</status>
    </object>
    <object>
        <id type="integer">7889549</id>
        <name>Sen. Dhyanesh Menon</name>
        <email>menon_dhyanesh_sen@cummerata-welch.test</email>
        <gender>female</gender>
        <status>active</status>
    </object>
</objects>
*/


    @JacksonXmlProperty(isAttribute = true, localName = "type")
    private String type;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "object")
    private List<ObjectData> objects;


    @Data
    private static class ObjectData {

        @JacksonXmlProperty(localName = "id")
        private Integer id;
        @JacksonXmlProperty(localName = "name")
        private String name;
        @JacksonXmlProperty(localName = "email")
        private String email;
        @JacksonXmlProperty(localName = "gender")
        private String gender;
        @JacksonXmlProperty(localName = "status")
        private String status;

        @Data
        public static class id {

            @JacksonXmlText
            private int value;
            @JacksonXmlProperty(isAttribute = true, localName = "type")
            private String type;
        }

    }

}
