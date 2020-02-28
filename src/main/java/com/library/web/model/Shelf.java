package com.library.web.model;

import com.library.web.repository.ShelfRepository;
import com.library.web.viewmodel.CreateShelfForm;
import com.library.web.viewmodel.UpdateShelfForm;

import javax.persistence.*;

@Entity
@Table(name = "shelf")
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelf_id", nullable = false)
    private long shelfId;
    private String code;

    public long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Shelf [shelfId=" + shelfId + ", code=" + code + "]";
    }

    public static Shelf from(CreateShelfForm createShelfForm) {
        Shelf shelf = new Shelf();
        shelf.setCode(createShelfForm.getCode());
        return shelf;
    }

    public static Shelf updateFrom(ShelfRepository shelfRepository, UpdateShelfForm updateShelfForm) {
        return shelfRepository.findByShelfId(updateShelfForm.getId())
                .map(a -> {
                    Shelf shelf = new Shelf();
                    shelf.setShelfId(updateShelfForm.getId());
                    shelf.setCode(updateShelfForm.getCode());
                    return shelf;
                })
                .orElseThrow(() -> new RuntimeException("Null Object"));
    }
}
