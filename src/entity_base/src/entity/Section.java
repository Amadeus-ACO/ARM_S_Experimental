package entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@javax.persistence.Entity
@JsonIgnoreProperties("children")
public class Section extends Entity {

    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private Section parentSection;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "parentSection")
    private List<Section> children = new ArrayList<>();

    @Transient
    private UUID parentId;

    @JsonSetter("parent_id")
    public void setParentId(UUID id) {
        this.parentId = id;
    }

    @JsonGetter("parent_id")
    public UUID getParentSectionID() {
        return parentSection != null ? parentSection.getId() : null;
    }
}


//@JoinColumn(name = "parent_id")
//        @JoinTable(
//            name = "lecture_section_relationship",
//            joinColumns = @JoinColumn(name = "id")
//        //inverseJoinColumns = @JoinColumn(name = "parent_id")
//  )
//    @JsonGetter("child_section_ids")
//    public Set<UUID> getChildSectionsIDs() {
//        return children
//                .stream()
//                .map(entity.Entity::getId)
//                .collect(Collectors.toSet());
//    }

//    public boolean hasParent(){
//        return parentSection != null;
//    }

//@OneToMany(mappedBy = "parentSection")
//private Set<LectureSection> childSectionList;

//    @JsonGetter("child_section_list")
//    public Set<UUID> getChildSectionList() {
//        return childSectionList
//                .stream()
//                .map(section -> section.getId())
//                .collect(Collectors.toSet());
//    }

//    @JsonGetter("parent_id")
//    public UUID getParentSection() {
//        return parentSection != null ? parentSection.getId() : null;
//    }

//@OneToMany


//@ManyToOne
//    @JoinTable(
//        name = "lecture_section_relationship",
//        joinColumns = @JoinColumn(name = "id")
//        //inverseJoinColumns = @JoinColumn(name = "parent_id")
//    )
//    @Transient
//    @JsonIgnoreProperties(value = {"parentLectureSection"})
//    private LectureSection parentLectureSection;

//    @JoinTable(
//        name = "lecture_section_relationship",
//        joinColumns = @JoinColumn(name = "parent_id"),
//        inverseJoinColumns = @JoinColumn(name = "child_id")
//    )
//    private UUID id;
//
//    @JoinTable(
//        name = "lecture_section_relationship",
//        joinColumns = @JoinColumn(name = "parent_id"),
//        inverseJoinColumns = @JoinColumn(name = "parent_id")
//    )
//    private UUID parentId;

//    @OneToMany
//    @JoinTable(
//        name = "lecture_section_relationship",
//        joinColumns = @JoinColumn(name = "parent_id"),
//        inverseJoinColumns = @JoinColumn(name = "child_id")
//    )
//    private List<LectureSection> lectureSectionList;