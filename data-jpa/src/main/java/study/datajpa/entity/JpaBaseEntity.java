package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

//순수 JPA
@Getter
@MappedSuperclass
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    //영속성 컨텍스트에 1차 캐싱 될 때 실행
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    //flush가 일어나면 실행
    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
