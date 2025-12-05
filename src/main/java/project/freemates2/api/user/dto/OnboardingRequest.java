package project.freemates2.api.user.dto;

public record OnboardingRequest(

    String nickname,
    Integer birthYear,
    Integer universityId,
    String gender

) {

}
