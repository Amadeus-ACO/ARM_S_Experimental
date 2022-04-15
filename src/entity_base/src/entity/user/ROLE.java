package entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ROLE {
    STUDENT("Студент"),
    TEACHER("Преподаватель"),
    ADMIN("Администратор"),
    METHODIST("Методист");
    private final String value;
}
