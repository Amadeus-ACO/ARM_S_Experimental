package entity.work.test.base;

import com.fasterxml.jackson.annotation.JsonView;
import entity.EntityView;
import entity.Section;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Question extends entity.Entity {

    @RequiredArgsConstructor
    public enum TYPE {
        // Вопросы входного тестирования
        NULL_ONE_ANSWER("OneAnswer"),               // На вопрос только один правильный ответ
        NULL_MULTIPLE_ANSWER("MultipleAnswer"),     // На вопрос несколько правильных вариантов ответа

        // Вопросы финального тестирования
        FINAL_LOVT("Final_LOVT"),  // Для ответа на вопрос используются инструменты LOVT
        FINAL_AOVT("Final_AOVT"),  // Для ответа на вопрос используются инструменты AOVT
        FINAL_TEXT("Final_TEXT");  // Для ответа на вопрос используется текстовое поле
        private final String value;
    }

    @Getter
    @JsonView(EntityView.METHODIST_QUESTION.class)
    private TYPE type;

    @Getter
    @ManyToOne
    @JsonView(EntityView.METHODIST_QUESTION.class)
    private Section section;

    @Getter
    @JsonView(EntityView.METHODIST_QUESTION.class)
    private String text;

    @Getter
    @OneToMany
    @JsonView(EntityView.METHODIST_QUESTION.class)
    private List<Answer> answerList;
}
