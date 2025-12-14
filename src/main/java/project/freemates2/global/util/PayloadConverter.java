package project.freemates2.global.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class PayloadConverter {
  private final ObjectMapper json;
  private final XmlMapper xml;

  public PayloadConverter() {
    // JSON 매퍼 설정
    this.json = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // XML 매퍼 설정
    this.xml = new XmlMapper();
    this.xml.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 4. 따로 설정


  }

  public <T> T parse(String body, Class<T> type) {
    if (body == null || body.isBlank()) {
      throw new IllegalArgumentException("응답 본문이 비어 있습니다.");
    }
    try {
      return isLikelyXml(body) ? xml.readValue(body, type) : json.readValue(body, type);
    } catch (Exception e) {
      throw new IllegalStateException("응답 파싱 실패: " + e.getMessage(), e);
    }
  }

  private boolean isLikelyXml(String s) {
    String t = s.stripLeading();
    return !t.isEmpty() && t.charAt(0) == '<';
  }

}

