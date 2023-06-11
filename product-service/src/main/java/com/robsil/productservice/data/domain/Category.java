package com.robsil.productservice.data.domain;

import com.robsil.userservice.data.domain.BaseEntity;
import lombok.experimental.SuperBuilder;
import lombok.*;
import jakarta.persistence.*;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

//    parent category id
    @ManyToOne
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    private Category parent;

    @Column
    private String title;

    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(super.getId(), category.getId()) && Objects.equals(parent, category.parent) && Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), parent, title);
    }
}
