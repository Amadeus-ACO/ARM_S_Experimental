package entity;

public class EntityView {
    // Вариант при начальной инициализации
    public static class INIT {}

    // Вариант с ID и основными свойствами
    public static class ID extends INIT {}

    public static class TEACHER_GROUP_LIST extends ID {}

    public static class TEACHER_STUDENT_LIST_IN_GROUP extends ID {
    }

    // Короткий вариант сериализации
    public static class SHORT extends ID {}

    public static class STUDENT extends ID {}

    //public static class TASK extends SHORT {}

    //public static class USER extends SHORT {}

    public static class TREE {}


}
