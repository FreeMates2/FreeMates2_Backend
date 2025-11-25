package project.freemates2.global.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import phytolens.api.diagnosis.application.DiagnosisService;
import phytolens.external.ai.application.AiDiagnosisClient;
import phytolens.external.ai.application.AiDiagnosisHttpClient;
import phytolens.external.ncpms.application.NcpmsClient;
import phytolens.external.ncpms.application.NcpmsHttpClient;
import phytolens.external.psis.application.PsisClient;
import phytolens.global.util.application.PayloadConverter;
import phytolens.external.psis.application.PsisHttpClient;

@Configuration
public class WebClientConfig {

  // AI 웹 진단 클라이언트
  @Bean("aiDiagnosisClient")
  public AiDiagnosisClient aiDiagnosisClient(
      WebClient.Builder webClientBuilder,
      @Value("${ai.endpoint}") String aiEndpoint
  ) {
    WebClient client = webClientBuilder.baseUrl(aiEndpoint).build();
    return new AiDiagnosisHttpClient(client);
  }

  // NCPMS 웹 클라이언트
  @Bean("ncpmsClient")
  public NcpmsClient ncpmsClient(
      WebClient.Builder builder,
      @Value("${ncpms.host}") String endpoint,
      @Value("${ncpms.api.key}") String apiKey,
      PayloadConverter payloadConverter
  ) {
    WebClient client = builder.baseUrl(endpoint).build();
    return new NcpmsHttpClient(client, apiKey, payloadConverter);
  }

  // PSIS 웹 클라이언트
  @Bean("psisClient")
  public PsisClient psisClient(
      WebClient.Builder builder,
      @Value("${psis.host}") String endpoint,
      @Value("${psis.api.key}") String apiKey,
      PayloadConverter payloadConverter

  ){
    WebClient client = builder.baseUrl(endpoint).build();
    return new PsisHttpClient(client, apiKey, payloadConverter);

  }

}
