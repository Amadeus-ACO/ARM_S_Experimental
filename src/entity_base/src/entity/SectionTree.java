package entity;

import java.util.List;

public class SectionTree extends Section {

    private List<Section> childSections;

    public SectionTree(Section section) {
        this.setId(section.getId());
        this.setName(section.getName());
        this.setParentSection(section.getParentSection());

        this.childSections = section.getChildren();
    }
}
