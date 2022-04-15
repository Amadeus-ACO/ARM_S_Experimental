package client.mainWindow.tabs;

import java.util.ArrayList;

class TabsZoneModel {

    public class TabPaneModel {
        public ArrayList<String> tabsModelList;

        public String createTabModel(String pageType) {
            return switch (pageType) {

                // Главная страница
                case MAIN_PAGE -> getMainPageModel();

                // Список лабораторных работ
                case LAB_LIST_PAGE -> getLabListPageModel();

                // Алгоритм с инстурментами АОВТ
                case AOVT_PAGE -> getAOVTModel();

                default -> null;
            };
        }
    }
    private ArrayList<TabPaneModel> TabPaneModelList;
    public ArrayList<TabPaneModel> getTabPaneModelList() {
        return TabPaneModelList;
    }

    public TabsZoneModel() {
        // Обновление конфигурации пользователя
        // Config.getUserData()
    }

    /**
     * Главная страница
     */
    public static final String MAIN_PAGE = "Main";
    private String getMainPageModel() {
        return "";
    }

    /**
     * Список лабораторных работ
     */
    public static final String LAB_LIST_PAGE = "Lab_List_Page";
    private String getLabListPageModel() {
        return "";
    }

    /**
     * Алгоритм с инструментами АОВТ
     */
    public static final String AOVT_PAGE = "AOVT_Page";
    private String getAOVTModel() {
        return "";
    }


}
